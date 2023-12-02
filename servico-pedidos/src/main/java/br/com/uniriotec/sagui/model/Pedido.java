package br.com.uniriotec.sagui.model;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="pedido_tabela")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    @Getter private Long id;
    @Column(name="pedido_numero", unique = true, nullable = false)
    @Getter private String pedidoNumero;
    @Getter
    @CreationTimestamp
    private LocalDateTime createdAt;
    //pedido não faz senido sem as linhas, FetchType.EAGER
    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "linha_item_pedido")
    @Getter @Setter private List<LinhaItemPedido> linhaItemPedidoList;
}
