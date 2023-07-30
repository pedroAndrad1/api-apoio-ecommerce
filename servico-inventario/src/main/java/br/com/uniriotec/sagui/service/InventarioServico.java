package br.com.uniriotec.sagui.service;

import java.util.List;

public interface InventarioServico {
    boolean estaEmEstoque(String skuCode);
    Long estaEmEstoque(List<String> skuCode);
}
