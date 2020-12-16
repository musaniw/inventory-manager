package com.contineo.demo.inventoryManager.service;

import com.contineo.demo.inventoryManager.model.InventoryRecord;
import com.contineo.demo.inventoryManager.model.InventoryResponse;
import com.contineo.demo.inventoryManager.repository.InventoryRepository;
import com.contineo.demo.inventoryManager.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.contineo.demo.inventoryManager.model.InventoryStatus.FAILURE;
import static com.contineo.demo.inventoryManager.model.InventoryStatus.SUCCESS;

@Service
public class InventoryService {

    private InventoryRepository inventoryRepository;
    private List<Validator> validators;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, List<Validator> validators) {
        this.inventoryRepository = inventoryRepository;
        this.validators = validators;
    }

    public List<InventoryRecord> getAll() {
        return inventoryRepository.findAll();
    }

    public InventoryResponse save(InventoryRecord newRecord) {
        List<String> validationFailures = validators.stream()
                .flatMap(validator -> validator
                        .validate(newRecord)
                        .stream())
                .collect(Collectors.toList());
        if (validationFailures.isEmpty()) {
            return new InventoryResponse(inventoryRepository.save(newRecord), SUCCESS, validationFailures);
        }
        return new InventoryResponse(newRecord, FAILURE, validationFailures);

    }

    public InventoryResponse update(InventoryRecord updateRecord) {
        Optional<InventoryRecord> existingRecord = inventoryRepository.findById(updateRecord.getId());
        if (existingRecord.isPresent()) {
            if(updateRecord.getQuantity() != null) {
                existingRecord.get().setQuantity(updateRecord.getQuantity());
                return new InventoryResponse(inventoryRepository.save(existingRecord.get()));
            } else {
                return new InventoryResponse(updateRecord, FAILURE, Collections.singletonList("Only quantity can be updated for id " + updateRecord.getId()));
            }
        }
        return new InventoryResponse(updateRecord, FAILURE, Collections.singletonList("Could not find existing record for id " + updateRecord.getId()));
    }
}
