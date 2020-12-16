package com.contineo.demo.inventoryManager.controller;

import com.contineo.demo.inventoryManager.model.InventoryRecord;
import com.contineo.demo.inventoryManager.model.InventoryResponse;
import com.contineo.demo.inventoryManager.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigInteger;
import java.util.Collections;

import static com.contineo.demo.inventoryManager.utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {InventoryController.class})
@ExtendWith(SpringExtension.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    public void testInventoryGetCall() throws Exception {
        InventoryRecord record = createInventoryRecord();
        when(inventoryService.getAll()).thenReturn(Collections.singletonList(record));

        MvcResult mvcResult = this.mockMvc
                .perform(get("/api/inventory/record"))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = mvcResult.getResponse().getContentAsString();

        assertTrue(responseString.contains(record.getName()));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testInventoryPostCall() throws Exception {
        InventoryRecord record = createInventoryRecord();
        when(inventoryService.save(any())).thenReturn(new InventoryResponse(record));

        this.mockMvc
                .perform(post("/api/inventory/record")
                        .content("{\n" +
                        "    \"name\" : \"" + INVENTORY_NAME + "\",\n" +
                        "    \"category\" : \"" + INVENTORY_CATEGORY + "\",\n" +
                        "    \"subCategory\" : \"" + INVENTORY_SUBCATEGORY + "\",\n" +
                        "    \"quantity\" : " + INVENTORY_QUANTITY + "\n" +
                        "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<InventoryRecord> bodyCaptor = forClass(InventoryRecord.class);

        verify(inventoryService).save(bodyCaptor.capture());
        assertEquals(bodyCaptor.getValue().getName(), INVENTORY_NAME);
        assertEquals(bodyCaptor.getValue().getCategory(), INVENTORY_CATEGORY);
        assertEquals(bodyCaptor.getValue().getSubCategory(), INVENTORY_SUBCATEGORY);
        assertEquals(bodyCaptor.getValue().getQuantity(), INVENTORY_QUANTITY);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testInventoryPatchCall() throws Exception {
        InventoryRecord record = createInventoryRecord();
        when(inventoryService.update(any())).thenReturn(new InventoryResponse(record));

        this.mockMvc
                .perform(patch("/api/inventory/record")
                        .content("{\n" +
                                "    \"id\" : 1,\n" +
                                "    \"quantity\" : 10\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<InventoryRecord> bodyCaptor = forClass(InventoryRecord.class);

        verify(inventoryService).update(bodyCaptor.capture());
        assertEquals(bodyCaptor.getValue().getId(), 1L);
        assertEquals(bodyCaptor.getValue().getQuantity(), BigInteger.TEN);
    }
}