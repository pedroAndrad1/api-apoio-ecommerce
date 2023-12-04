package br.com.uniriotec.sagui.model.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class LinhaItemPedidoData extends RepresentationModel<LinhaItemPedidoData> {

    @Getter private String codigoSku;
    @Getter private BigDecimal preco;
    @Getter private Integer quantidade;
}
