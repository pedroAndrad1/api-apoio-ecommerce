package br.com.uniriotec.sagui.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InventarioData {
    private Long id;
    private String skuCode;
    private Integer quantity;
}
