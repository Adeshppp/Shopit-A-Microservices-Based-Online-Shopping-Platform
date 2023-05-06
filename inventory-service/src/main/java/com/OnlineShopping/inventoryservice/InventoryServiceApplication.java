package com.OnlineShopping.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


//	hard code to feed data manually
//		@Bean
//		public CommandLineRunner loadData(InventoryRepository inventoryRepository){
//			return args -> {
//				Inventory inventory = new Inventory();
//				inventory.setSkuCode("iphone_13");
//				inventory.setQuantity(100);
//
//				Inventory inventory1 = new Inventory();
//				inventory1.setSkuCode("iphone_14");
//				inventory1.setQuantity(0);
//				inventoryRepository.save(inventory);
//				inventoryRepository.save(inventory1);
//			};
//
//	}

}
