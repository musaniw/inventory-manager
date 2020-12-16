package com.contineo.demo.inventoryManager.utils;

import com.contineo.demo.inventoryManager.model.Category;
import com.contineo.demo.inventoryManager.model.InventoryRecord;
import com.contineo.demo.inventoryManager.model.SubCategory;

import java.math.BigInteger;

public class TestUtils {
    public static final Long INVENTORY_ID = 1L;
    public static final String INVENTORY_NAME = "Nike Vapor Zoom";
    public static final Category INVENTORY_CATEGORY = Category.CLOTHES;
    public static final SubCategory INVENTORY_SUBCATEGORY = SubCategory.SHOES;
    public static final BigInteger INVENTORY_QUANTITY = BigInteger.ONE;

    public static InventoryRecord createInventoryRecord() {
        return new InventoryRecord(INVENTORY_ID, INVENTORY_NAME, INVENTORY_CATEGORY, INVENTORY_SUBCATEGORY, INVENTORY_QUANTITY);
    }
}
