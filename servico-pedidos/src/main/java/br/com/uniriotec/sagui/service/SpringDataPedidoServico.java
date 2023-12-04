package br.com.uniriotec.sagui.service;

import br.com.uniriotec.sagui.model.LinhaItemPedido;
import br.com.uniriotec.sagui.model.Pedido;
import br.com.uniriotec.sagui.model.dto.LinhaItemPedidoRepresentationAssembler;
import br.com.uniriotec.sagui.model.dto.PedidoData;
import br.com.uniriotec.sagui.model.dto.PedidoRepresentationAssembler;
import br.com.uniriotec.sagui.repository.PedidoRepositorio;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class SpringDataPedidoServico implements PedidoServico {

    private final PedidoRepositorio pedidoRepositorio;
    private final PedidoRepresentationAssembler pedidoRepresentationAssembler;
    private final LinhaItemPedidoRepresentationAssembler itemPedidoRepresentationAssembler;
    private final WebClient.Builder webClientBuilder;

    private static final String SALVAR_PEDIDO_CB_NOME = "pedidos";

    @Autowired
    public SpringDataPedidoServico(PedidoRepositorio pedidoRepositorio, PedidoRepresentationAssembler pedidoRepresentationAssembler,
                         LinhaItemPedidoRepresentationAssembler itemPedidoRepresentationAssembler, WebClient.Builder webClientBuilder) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.pedidoRepresentationAssembler = pedidoRepresentationAssembler;
        this.itemPedidoRepresentationAssembler = itemPedidoRepresentationAssembler;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    @CircuitBreaker(name = SALVAR_PEDIDO_CB_NOME, fallbackMethod = "pedidosFallBack")
    @TimeLimiter( name = SALVAR_PEDIDO_CB_NOME )
    @Retry( name = SALVAR_PEDIDO_CB_NOME )
    public CompletableFuture<PedidoData> salvarPedido(PedidoData pedidoData){
        System.out.println(pedidoData);
        //Transforma pedidoData em classe anotada para persistencia pedido
        Pedido pedido = pedidoRepresentationAssembler.mapToPersistence(pedidoData);
        pedido.setLinhaItemPedidoList( itemPedidoRepresentationAssembler.mapToPersistenseCollection( pedidoData.getLinhaItemPedidoDataList(), pedido ) );
        //Cria lista de sku's para chamada a inventario
        List<String> codigosSku = pedido.getLinhaItemPedidoList().stream()
                .map(LinhaItemPedido::getCodigoSku)
                .toList();
        //Chama api de inventario que retorna um long que representa a quantidade de produtos em estoque
        Long resultado = webClientBuilder.build().get()
                .uri( "http://servico-inventario/api/inventario",
                        uriBuilder -> uriBuilder.queryParam( "skuCode", codigosSku ).build() )
                .retrieve()
                .bodyToMono( Long.class )
                .block();
        //Salva o pedido caso o Long retornado da chamada à inventário seja igual ao tamanho da lista de produtos
        try{
            if( resultado == codigosSku.size() ){
                pedido = pedidoRepositorio.save(pedido);
                //todo chamada assincrona a inventário pra recurerar qual item do pedido está fora de estoque
            }else{
                throw new IllegalArgumentException("Foram pedidos " + codigosSku.size() + " produtos e " + ( codigosSku.size() - resultado ) + " deles não estão em estoque");
            }
        }catch(NullPointerException e){
            throw new IllegalArgumentException("A lista de SKUs não retornou quantidade nulla, favor contatar o suporte");
        }
        final PedidoData pedidoDataCF = pedidoRepresentationAssembler.toModel(pedido);
        return CompletableFuture.supplyAsync( () -> pedidoDataCF) ;
    }
//    @Override
//    @CircuitBreaker(name = SALVAR_PEDIDO_CB_NOME, fallbackMethod = "pedidosFallBack")
//    @TimeLimiter( name = SALVAR_PEDIDO_CB_NOME )
//    @Retry( name = SALVAR_PEDIDO_CB_NOME )
//    public CompletableFuture<Pedido> salvarPedido2(Pedido pedido){
//
//    }
    @Override
    public CompletableFuture<PedidoData> pedidosFallBack(PedidoData pedidoData, RuntimeException runtimeException){
        System.out.println("Excessão:");
        System.out.println(runtimeException.getCause());
        pedidoData.setPedidoNumero("Erro, não foi possível registrar o pedido, tente novamente.");
        final PedidoData pedidoDataCF = pedidoData;
        return  CompletableFuture.supplyAsync(( ) -> pedidoDataCF);
    }
}
