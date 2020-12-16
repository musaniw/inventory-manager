package com.contineo.demo.inventoryManager.service;

import com.contineo.demo.inventoryManager.model.*;
import com.contineo.demo.inventoryManager.repository.InventoryRepository;
import com.contineo.demo.inventoryManager.utils.TestUtils;
import com.contineo.demo.inventoryManager.validators.CategoryValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.contineo.demo.inventoryManager.utils.TestUtils.*;
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
    void testAllRecordsAreReturnedOnGet() {
        List<InventoryRecord> existingRecords = Arrays.asList(createInventoryRecord(), createInventoryRecord());
        when(repository.findAll()).thenReturn(existingRecords);

        List<InventoryRecord> records = inventoryService.getAll();

        verify(repository, times(1)).findAll();
        assertEquals(existingRecords, records);
    }

    @Test
    void testValidRecordProcessedSuccessfullyOnSave() {
        InventoryRecord newRecord = new InventoryRecord(INVENTORY_NAME, INVENTORY_CATEGORY, INVENTORY_SUBCATEGORY, INVENTORY_QUANTITY);
        InventoryRecord expectedRecord = createInventoryRecord();

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
    void testInValidRecordReturnsValidationFailuresOnSave() {
        InventoryRecord newRecord = createInventoryRecord();

        when(categoryValidator.validate(newRecord)).thenReturn(Collections.singletonList("Error"));
        InventoryResponse inventoryResponse = inventoryService.save(newRecord);

        verify(categoryValidator, times(1)).validate(newRecord);
        verify(repository, never()).save(newRecord);
        assertEquals(newRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.FAILURE, inventoryResponse.getStatus());
        assertFalse(inventoryResponse.getValidationFailures().isEmpty());
    }

    @Test
    void testQuantityIsUpdatedOnUpdate() {
        InventoryRecord updateRecord = new InventoryRecord(INVENTORY_ID, null, null, null, BigInteger.TWO);
        InventoryRecord existingRecord = TestUtils.createInventoryRecord();
        InventoryRecord updatedRecord = new InventoryRecord(1L, INVENTORY_NAME, INVENTORY_CATEGORY, INVENTORY_SUBCATEGORY, BigInteger.TWO);

        when(repository.findById(1L)).thenReturn(Optional.of(existingRecord));
        when(repository.save(updatedRecord)).thenReturn(updatedRecord);

        InventoryResponse inventoryResponse = inventoryService.update(updateRecord);

        verify(repository, times(1)).save(updatedRecord);
        assertEquals(updatedRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.SUCCESS, inventoryResponse.getStatus());
    }

    @Test
    void testNoOtherFieldExceptQuantityIsUpdated() {
        InventoryRecord updateRecord = new InventoryRecord(INVENTORY_ID,"Nike Air Max",null, null, BigInteger.TWO);
        InventoryRecord existingRecord = TestUtils.createInventoryRecord();
        InventoryRecord updatedRecord = new InventoryRecord(1L, INVENTORY_NAME, INVENTORY_CATEGORY, INVENTORY_SUBCATEGORY, BigInteger.TWO);

        when(repository.findById(1L)).thenReturn(Optional.of(existingRecord));
        when(repository.save(existingRecord)).thenReturn(updatedRecord);

        InventoryResponse inventoryResponse = inventoryService.update(updateRecord);

        verify(repository, times(1)).save(updatedRecord);
        assertEquals(updatedRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.SUCCESS, inventoryResponse.getStatus());
    }

    @Test
    void testValidationFailuresReturnedIfQuantityNotProvidedOnUpdate() {
        InventoryRecord updateRecord = new InventoryRecord(INVENTORY_ID,"Nike Air Max",null, null, null);
        InventoryRecord existingRecord = TestUtils.createInventoryRecord();

        when(repository.findById(1L)).thenReturn(Optional.of(existingRecord));

        InventoryResponse inventoryResponse = inventoryService.update(updateRecord);

        verify(repository, never()).save(any());
        assertEquals(updateRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.FAILURE, inventoryResponse.getStatus());
        assertFalse(inventoryResponse.getValidationFailures().isEmpty());
    }

    @Test
    void testValidationFailuresReturnedIfNoExistingRecordOnUpdate() {
        InventoryRecord updateRecord = new InventoryRecord(INVENTORY_ID,"Nike Air Max",null, null, null);

        when(repository.findById(1L)).thenReturn(Optional.empty());

        InventoryResponse inventoryResponse = inventoryService.update(updateRecord);

        verify(repository, never()).save(any());
        assertEquals(updateRecord, inventoryResponse.getRecord());
        assertEquals(InventoryStatus.FAILURE, inventoryResponse.getStatus());
        assertFalse(inventoryResponse.getValidationFailures().isEmpty());
    }
}