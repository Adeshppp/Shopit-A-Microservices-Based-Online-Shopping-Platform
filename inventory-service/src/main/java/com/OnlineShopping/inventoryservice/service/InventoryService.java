package com.OnlineShopping.inventoryservice.service;

import com.OnlineShopping.inventoryservice.dto.InventoryResponse;
import com.OnlineShopping.inventoryservice.dto.ProductRequest;
import com.OnlineShopping.inventoryservice.model.Inventory;
import com.OnlineShopping.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;

    @SneakyThrows //demo purpose
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
//        log.info("Wait started");
//        Thread.sleep(10000);
//        log.info("Wait ended");
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
    public String addProductInInventory(ProductRequest productRequest) {
        Inventory inventory = inventoryRepository.findBySkuCode(productRequest.getSkuCode());

        if(inventory != null) {
            log.error("Product with sku code {} already present", productRequest.getSkuCode());
            return ("Product with sku code "+productRequest.getSkuCode()+" already present with count "+ isInStock(Collections.singletonList(productRequest.getSkuCode())).get(0).getAvailableStock());

        }
        else{
            Inventory newInventory = new Inventory();
            newInventory.setSkuCode(productRequest.getSkuCode());
            newInventory.setQuantity(productRequest.getQuantity());
            inventoryRepository.save(newInventory);
            log.info("Product with sku code {} is successfully added to inventory.",productRequest.getSkuCode());

        }
        return ("Product with sku code "+productRequest.getSkuCode()+" is successfully added to inventory.");
    }

    public String addStock(ProductRequest productRequest) {
        Inventory inventory = inventoryRepository.findBySkuCode(productRequest.getSkuCode());
        if(inventory != null){
            inventory.setQuantity(inventory.getQuantity()+productRequest.getQuantity());
            inventoryRepository.save(inventory);
            log.info("Updated the stock to {} for product {}", productRequest.getQuantity(),productRequest.getSkuCode());
        }
        return ("Sucessfully added "+productRequest.getQuantity()+" pieces of product "+productRequest.getSkuCode()+" and updated count is "+isInStock(Collections.singletonList(productRequest.getSkuCode())).get(0).getAvailableStock());
    }
}

// todo: can perform this operation more specific by checking the order quantity as well with the available stock.
