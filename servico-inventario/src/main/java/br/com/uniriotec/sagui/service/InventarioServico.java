package br.com.uniriotec.sagui.service;

import br.com.uniriotec.sagui.model.dto.InventarioData;
import br.com.uniriotec.sagui.model.dto.InventarioForm;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

public interface InventarioServico {
    boolean estaEmEstoque(String skuCode);
    Long estaEmEstoque(List<String> skuCode);

    InventarioData atualizaInventario(InventarioForm inventarioForm);

    InventarioData inserirInventario(InventarioForm inventarioForm);

    CollectionModel<InventarioData> retornaTodos();
}
