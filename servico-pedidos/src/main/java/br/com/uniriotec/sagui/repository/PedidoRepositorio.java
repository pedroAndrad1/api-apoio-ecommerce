package br.com.uniriotec.sagui.repository;

import br.com.uniriotec.sagui.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
}
