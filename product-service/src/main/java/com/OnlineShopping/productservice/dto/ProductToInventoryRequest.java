package com.OnlineShopping.productservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductToInventoryRequest {
    private String skuCode;
    private Integer quantity = 0;
}
