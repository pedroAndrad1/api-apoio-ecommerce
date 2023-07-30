package br.com.uniriotec.sagui.model.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class LinhaItemPedidoData extends RepresentationModel<LinhaItemPedidoData> {

    @Getter private Long id;
    @Getter private String codigoSku;
    @Getter private BigDecimal preco;
    @Getter private Integer quantidade;

    @Getter private PedidoData pedidoData;
}
