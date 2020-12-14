package com.contineo.demo.inventoryManager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Objects;

@Entity(name="INVENTORY")
public class InventoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Category category;
    private SubCategory subCategory;
    private BigInteger quantity;

    public InventoryRecord() {
    }

    public InventoryRecord(Long id, String name, Category category, SubCategory subCategory, BigInteger quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.quantity = quantity;
    }

    public InventoryRecord(String name, Category category, SubCategory subCategory, BigInteger quantity) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryRecord record = (InventoryRecord) o;
        return Objects.equals(id, record.id) &&
                Objects.equals(name, record.name) &&
                category == record.category &&
                subCategory == record.subCategory &&
                Objects.equals(quantity, record.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, subCategory, quantity);
    }
}
