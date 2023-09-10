package br.com.uniriotec.sagui.controller;

import br.com.uniriotec.sagui.model.Pedido;
import br.com.uniriotec.sagui.model.dto.PedidoData;
import br.com.uniriotec.sagui.model.dto.PedidoRepresentationAssembler;
import br.com.uniriotec.sagui.repository.PedidoRepositorio;
import br.com.uniriotec.sagui.service.PedidoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin
public class PedidoControlador {

    private final PedidoRepositorio pedidoRepositorio;
    private final PedidoRepresentationAssembler pedidoRepresentationAssembler;
    private final PedidoServico pedidoServico;

    @Autowired
    public PedidoControlador(PedidoRepositorio pedidoRepositorio,
                             PedidoRepresentationAssembler pedidoRepresentationAssembler, PedidoServico pedidoServico) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.pedidoRepresentationAssembler = pedidoRepresentationAssembler;
        this.pedidoServico = pedidoServico;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<PedidoData> buscaPedido(Long id) {
        List<Pedido> pedidos = pedidoRepositorio.findAll();
        return pedidoRepresentationAssembler.toCollectionModel(pedidos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<PedidoData> geraPedido(@RequestBody PedidoData pedidoData){
        return pedidoServico.salvarPedido(pedidoData);
    }

}
