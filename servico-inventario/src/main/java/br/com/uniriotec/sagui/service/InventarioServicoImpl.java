package br.com.uniriotec.sagui.service;

import br.com.uniriotec.sagui.Repository.InventarioRepositorio;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class InventarioServicoImpl implements InventarioServico {
    private final Long INVENTARIO_DEVE_SER_MAIOR = 0L;
    //teste
    @Autowired
    private InventarioRepositorio inventarioRepositorio;
    @Override
    public boolean estaEmEstoque(String skuCode){
        return inventarioRepositorio.findBySkuCode( skuCode ).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public Long estaEmEstoque(List<String> skuCode) {
        log.info("start");
        Thread.sleep(30000);
        log.info("ended");
        return inventarioRepositorio.countBySkuCodeInAndQuantityGreaterThan(skuCode, INVENTARIO_DEVE_SER_MAIOR);
    }

}
