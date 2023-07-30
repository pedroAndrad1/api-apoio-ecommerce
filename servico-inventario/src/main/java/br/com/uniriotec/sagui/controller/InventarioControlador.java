package br.com.uniriotec.sagui.controller;

import br.com.uniriotec.sagui.Repository.InventarioRepositorio;
import br.com.uniriotec.sagui.model.Inventario;
import br.com.uniriotec.sagui.service.InventarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventario")
@CrossOrigin(origins = {"http://localhost:8080","http://localhost:3000"})
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
    @GetMapping("/admin")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Inventario>> todos(){
        return ResponseEntity.status(HttpStatus.OK).body( ir.findAll() );
    }

}
