package br.com.uniriotec.sagui.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InventarioForm {
    private Long id;
    private String skuCode;
    private Integer quantity;
}
