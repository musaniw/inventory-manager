package com.contineo.demo.inventoryManager.model;

import java.util.ArrayList;
import java.util.List;

public class InventoryResponse {
    InventoryRecord record;
    InventoryStatus status;
    List<String> validationFailures;

    public InventoryResponse(InventoryRecord record) {
        this.record = record;
        this.status = InventoryStatus.SUCCESS;
        this.validationFailures = new ArrayList<>();
        ;
    }

    public InventoryResponse(InventoryRecord record, InventoryStatus status, List<String> validationFailures) {
        this.record = record;
        this.status = status;
        this.validationFailures = validationFailures;
        ;
    }

    public InventoryRecord getRecord() {
        return record;
    }

    public void setRecord(InventoryRecord record) {
        this.record = record;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    public List<String> getValidationFailures() {
        return validationFailures;
    }

    public void setValidationFailures(List<String> validationFailures) {
        this.validationFailures = validationFailures;
    }
}
