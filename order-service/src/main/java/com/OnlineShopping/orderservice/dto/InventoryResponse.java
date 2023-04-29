package com.OnlineShopping.orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// added from inventory's dto package
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;
}
