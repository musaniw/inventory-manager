package com.contineo.demo.inventoryManager.model;

public enum SubCategory {
    SHOES(Category.CLOTHES),
    CAKES(Category.FOOD);

    Category category;

    SubCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }


}
