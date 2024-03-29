package com.OnlineShopping.inventoryservice.controller;

import com.OnlineShopping.inventoryservice.dto.InventoryResponse;
import com.OnlineShopping.inventoryservice.dto.ProductRequest;
import com.OnlineShopping.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Path parameter
    // http://localhost:8082/api/inventory/iphone-14,iphone-13

    // Request parameter
    // http://localhost:8082/api/inventory?skuCode=iphone-14&skuCode=iphone-13

    // This endpoint can be called from the Order Service to check the availability of a product.

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String  addProductInInventory(@RequestBody ProductRequest productRequest){
        return inventoryService.addProductInInventory(productRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String addStock(@RequestBody ProductRequest productRequest){
        return inventoryService.addStock(productRequest);
    }


}
