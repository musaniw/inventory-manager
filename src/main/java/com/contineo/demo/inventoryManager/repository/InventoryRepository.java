package com.contineo.demo.inventoryManager.repository;

import com.contineo.demo.inventoryManager.model.Category;
import com.contineo.demo.inventoryManager.model.InventoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryRecord, Long> {

    public InventoryRecord findByName(String name);

    public List<InventoryRecord> findByCategory(Category category);

}
