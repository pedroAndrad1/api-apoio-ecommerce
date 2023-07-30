package br.com.uniriotec.sagui.Repository;

import br.com.uniriotec.sagui.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioRepositorio extends JpaRepository<Inventario,Long> {
    List<Inventario> findBySkuCodeIn( List<String> skuCode );
    Optional<Inventario> findBySkuCode( String skuCode );
    Long countBySkuCodeInAndQuantityGreaterThan( List<String> skuCodes, Long quantity );
}
