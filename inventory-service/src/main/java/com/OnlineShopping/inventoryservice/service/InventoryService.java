package com.OnlineShopping.inventoryservice.service;

import com.OnlineShopping.inventoryservice.dto.InventoryResponse;
import com.OnlineShopping.inventoryservice.dto.ProductRequest;
import com.OnlineShopping.inventoryservice.model.Inventory;
import com.OnlineShopping.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .availableStock(inventory.getQuantity())
                            .build()
                )
                .toList();
    }

    @Transactional
    public void addProductInInventory(ProductRequest productRequest) {
        Inventory inventory = inventoryRepository.findBySkuCode(productRequest.getSkuCode());

        if(inventory != null) log.error("Product with sku code {} already present", productRequest.getSkuCode());
        else{
            Inventory newInventory = new Inventory();
            newInventory.setSkuCode(productRequest.getSkuCode());
            newInventory.setQuantity(productRequest.getQuantity());
            inventoryRepository.save(newInventory);
            log.info("Product with sku code {} is successfully added to inventory.", productRequest.getSkuCode());
        }
    }

    public void addStock(ProductRequest productRequest) {
        Inventory inventory = inventoryRepository.findBySkuCode(productRequest.getSkuCode());
        if(inventory != null){
            inventory.setQuantity(productRequest.getQuantity());
            inventoryRepository.save(inventory);
            log.info("Updated the stock to {} for product {}", productRequest.getQuantity(),productRequest.getSkuCode());
        }
    }
}

// todo: can perform this operation more specific by checking the order quantity as well with the available stock.
