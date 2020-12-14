package com.contineo.demo.inventoryManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryManagerApplication {
    private static final Logger log = LoggerFactory.getLogger(InventoryManagerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagerApplication.class, args);
    }
}
