package com.OnlineShopping.inventoryservice.repository;

import com.OnlineShopping.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);

    Inventory findBySkuCode(String skuCode);
}
