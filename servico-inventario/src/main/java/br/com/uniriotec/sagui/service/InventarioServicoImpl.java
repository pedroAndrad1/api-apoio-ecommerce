package br.com.uniriotec.sagui.service;

import br.com.uniriotec.sagui.Repository.InventarioRepositorio;
import br.com.uniriotec.sagui.model.Inventario;
import br.com.uniriotec.sagui.model.dto.InventarioData;
import br.com.uniriotec.sagui.model.dto.InventarioForm;
import br.com.uniriotec.sagui.model.dto.InventarioRepresentationAssembler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventarioServicoImpl implements InventarioServico {
    private static final Long INVENTARIO_DEVE_SER_MAIOR = 0L;
    //teste
    @Autowired
    private InventarioRepositorio inventarioRepositorio;
    @Autowired
    private InventarioRepresentationAssembler inventarioRepresentationAssembler;
    @Override
    public boolean estaEmEstoque(String skuCode){
        return inventarioRepositorio.findBySkuCode( skuCode ).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public Long estaEmEstoque(List<String> skuCode) {
        return inventarioRepositorio.countBySkuCodeInAndQuantityGreaterThan(skuCode, INVENTARIO_DEVE_SER_MAIOR);
    }

    @Override
    public InventarioData atualizaInventario(InventarioForm inventarioForm) {
        Optional<Inventario> data = inventarioRepositorio.findBySkuCode( inventarioForm.getSkuCode() );
        if( data.isPresent() ){
            return  inventarioRepresentationAssembler.toModel(
            inventarioRepositorio.save(
                   Inventario.builder()
                           .id( data.get().getId() )
                           .skuCode(inventarioForm.getSkuCode() )
                           .quantity(inventarioForm.getQuantity() )
                           .build()
           ));
        }else{
            throw new BancoNaoModificadoException("Não foi possivel atualizar o produto no inventário");
        }
    }

    @Override
    public InventarioData inserirInventario(InventarioForm inventarioData) {
        inventarioRepositorio.findBySkuCode(inventarioData.getSkuCode()).ifPresent(
               notFound -> {throw new BancoNaoModificadoException("Este sku code já existe na base");}
        );
        Inventario toPersist = Inventario.builder()
                .skuCode( inventarioData.getSkuCode() )
                .quantity( inventarioData.getQuantity() )
                .build();
        return inventarioRepresentationAssembler.toModel( inventarioRepositorio.save(toPersist) );
    }

    @Override
    public CollectionModel<InventarioData> retornaTodos() {
        return inventarioRepresentationAssembler.toCollectionModel( inventarioRepositorio.findAll() );
    }

}
