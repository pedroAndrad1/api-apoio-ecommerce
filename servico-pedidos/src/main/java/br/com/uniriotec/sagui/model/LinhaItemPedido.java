package br.com.uniriotec.sagui.model;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="linha_item_pedido_tabela")
public class LinhaItemPedido {
//    @Id
//    @Column(name = "linha_item_pedido_id")
//    @Getter private String id;
    @Id
    @Column(name = "codigo_sku", nullable = false)
    @Getter private String codigoSku;
    @Column(name = "preco")
    @Getter private BigDecimal preco;
    @Column(name = "quantidade")
    @Getter private Integer quantidade;
    @ManyToOne
    @JoinColumn(name="pedido_id", nullable = false)
    @Getter @Setter private Pedido pedido;
}
