package com.contineo.demo.inventoryManager.service;

import com.contineo.demo.inventoryManager.model.*;
import com.contineo.demo.inventoryManager.repository.InventoryRepository;
import com.contineo.demo.inventoryManager.validators.CategoryValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    InventoryRepository repository;

    @Mock
    CategoryValidator categoryValidator;

    InventoryService inventoryService;

    @BeforeEach
    public void init() {
        inventoryService = new InventoryService(repository, Collections.singletonList(categoryValidator));
    }

    @Test
    void testValidProductIsSavedSuccessfully() {
        InventoryRecord newRecord = new InventoryRecord("Nike Vapor Zoom", Category.CLOTHES, SubCategory.SHOES, BigInteger.ONE);
        InventoryRecord expectedRecord = new InventoryRecord(1L, "Nike Vapor Zoom", Category.CLOTHES, SubCategory.SHOES, BigInteger.ONE);

        when(categoryValidator.validate(newRecord)).thenReturn(Collections.emptyList());
        when(repository.save(newRecord)).thenReturn(expectedRecord);
        InventoryResponse inventoryResponse = inventoryService.save(newRecord);

        verify(categoryValidator, times(1)).validate(newRecord);
        verify(repository, times(1)).save(newRecord);
        assertEquals(expectedRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.SUCCESS, inventoryResponse.getStatus());
        assertTrue(inventoryResponse.getValidationFailures().isEmpty());
    }

    @Test
    void testInValidProductReturnsValidationFailures() {
        InventoryRecord newRecord = new InventoryRecord("Nike Vapor Zoom", Category.CLOTHES, SubCategory.SHOES, BigInteger.ONE);

        when(categoryValidator.validate(newRecord)).thenReturn(Collections.singletonList("Error"));
        InventoryResponse inventoryResponse = inventoryService.save(newRecord);

        verify(categoryValidator, times(1)).validate(newRecord);
        verify(repository, never()).save(newRecord);
        assertEquals(newRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.FAILURE, inventoryResponse.getStatus());
        assertFalse(inventoryResponse.getValidationFailures().isEmpty());
    }

    @Test
    void testQuantityIsUpdatedOnPatch() {
        InventoryRecord updateRecord = new InventoryRecord(1L, null, null, null, BigInteger.TWO);
        InventoryRecord existingRecord = new InventoryRecord(1L, "Nike Vapor Zoom", Category.CLOTHES, SubCategory.SHOES, BigInteger.ONE);
        InventoryRecord updatedRecord = new InventoryRecord(1L, "Nike Vapor Zoom", Category.CLOTHES, SubCategory.SHOES, BigInteger.TWO);

        when(repository.findById(1L)).thenReturn(Optional.of(existingRecord));
        when(repository.save(updatedRecord)).thenReturn(updatedRecord);

        InventoryResponse inventoryResponse = inventoryService.update(updateRecord);

        verify(repository, times(1)).save(updatedRecord);
        assertEquals(updatedRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.SUCCESS, inventoryResponse.getStatus());
    }

    @Test
    void testNoOtherFieldExceptQuantityIsUpdatedOnPatch() {
        InventoryRecord updateRecord = new InventoryRecord(1L,"Nike Air Max",null, null, BigInteger.TWO);
        InventoryRecord existingRecord = new InventoryRecord(1L,"Nike Vapor Zoom", Category.CLOTHES, SubCategory.SHOES, BigInteger.ONE);
        InventoryRecord updatedRecord = new InventoryRecord(1L, "Nike Vapor Zoom", Category.CLOTHES, SubCategory.SHOES, BigInteger.TWO);

        when(repository.findById(1L)).thenReturn(Optional.of(existingRecord));
        when(repository.save(existingRecord)).thenReturn(updatedRecord);

        InventoryResponse inventoryResponse = inventoryService.update(updateRecord);

        verify(repository, times(1)).save(updatedRecord);
        assertEquals(updatedRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.SUCCESS, inventoryResponse.getStatus());
    }
}