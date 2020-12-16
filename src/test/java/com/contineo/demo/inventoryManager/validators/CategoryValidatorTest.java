package com.contineo.demo.inventoryManager.validators;

import com.contineo.demo.inventoryManager.model.Category;
import com.contineo.demo.inventoryManager.model.InventoryRecord;
import com.contineo.demo.inventoryManager.model.SubCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.List;

import static com.contineo.demo.inventoryManager.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class CategoryValidatorTest {

    @Test
    public void testCorrectSubcategoryIsValid() {
        InventoryRecord record = createInventoryRecord();

        CategoryValidator validator = new CategoryValidator();

        List<String> failures = validator.validate(record);

        assertTrue(failures.isEmpty());
    }

    @Test
    public void testIncorrectSubcategoryIsInvalid() {
        InventoryRecord record = new InventoryRecord(INVENTORY_NAME, INVENTORY_CATEGORY, SubCategory.CAKES, INVENTORY_QUANTITY);

        CategoryValidator validator = new CategoryValidator();

        List<String> failures = validator.validate(record);

        assertFalse(failures.isEmpty());
    }

}