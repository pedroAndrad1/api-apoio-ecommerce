package br.com.uniriotec.sagui.controller;

import br.com.uniriotec.sagui.Repository.InventarioRepositorio;
import br.com.uniriotec.sagui.model.dto.InventarioData;
import br.com.uniriotec.sagui.model.dto.InventarioForm;
import br.com.uniriotec.sagui.service.InventarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventario")
public class InventarioControlador {
    @Autowired
    private InventarioServico inventarioServico;
    @Autowired
    private InventarioRepositorio ir;

    /**
     * Retorna a quantidade de produtos com quantidade disponível maior que 0
     * @param skuCode códigos de produto
     * @return quantidade de produtos
     */
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Long> emEstoque(@RequestParam List<String> skuCode){
        return ResponseEntity.status(HttpStatus.OK).body( inventarioServico.estaEmEstoque( skuCode ) );
    }

    /**
     * Retorna todos os itens de inventário
     * @return
     */
    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CollectionModel<InventarioData> todos(){
        return inventarioServico.retornaTodos();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public InventarioData salvar(@RequestBody InventarioForm inventarioForm){
        return inventarioServico.inserirInventario(inventarioForm);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public InventarioData alterar(@RequestBody InventarioForm inventarioForm){
        return inventarioServico.atualizaInventario( inventarioForm );
    }
}
