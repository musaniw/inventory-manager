package com.contineo.demo.inventoryManager.validators;

import com.contineo.demo.inventoryManager.model.InventoryRecord;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CategoryValidator implements Validator {

    public CategoryValidator() {
    }

    public List<String> validate(InventoryRecord record) {
        if(record.getSubCategory().getCategory().equals(record.getCategory())) {
            return Collections.emptyList();
        }
         else {
             return Collections.singletonList(String.format("Subcategory %s does not belong in category %s", record.getSubCategory(), record.getCategory()));
        }
    }
}
