package com.contineo.demo.inventoryManager.validators;

import com.contineo.demo.inventoryManager.model.InventoryRecord;

import java.util.List;

public interface Validator {

    List<String> validate(InventoryRecord record);

}
