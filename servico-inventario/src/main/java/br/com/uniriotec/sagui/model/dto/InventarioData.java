package br.com.uniriotec.sagui.model.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InventarioData extends RepresentationModel<InventarioData> {
    private Long id;
    private String skuCode;
    private Integer quantity;
}
