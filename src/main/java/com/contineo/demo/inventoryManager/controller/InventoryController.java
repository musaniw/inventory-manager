package com.contineo.demo.inventoryManager.controller;

import com.contineo.demo.inventoryManager.model.Category;
import com.contineo.demo.inventoryManager.model.InventoryRecord;
import com.contineo.demo.inventoryManager.model.InventoryResponse;
import com.contineo.demo.inventoryManager.model.SubCategory;
import com.contineo.demo.inventoryManager.repository.InventoryRepository;
import com.contineo.demo.inventoryManager.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping(path = "/record", produces = "application/json")
    public List<InventoryRecord> getProducts() {
        try {
            return inventoryService.getAll();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping(path = "/record", consumes = "application/json", produces = "application/json")
    public InventoryResponse addProduct(@RequestBody InventoryRecord newRecord) {
        try {
            return inventoryService.save(newRecord);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PatchMapping(path = "/record", consumes = "application/json", produces = "application/json")
    public InventoryResponse updateProduct(@RequestBody InventoryRecord updateRecord) {
        try {
            return inventoryService.update(updateRecord);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
