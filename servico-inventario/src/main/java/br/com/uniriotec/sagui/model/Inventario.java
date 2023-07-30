package br.com.uniriotec.sagui.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario_tabela")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String skuCode;
    private Integer quantity;
}
