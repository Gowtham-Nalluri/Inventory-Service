package com.hcl.inventory.repository;

import com.hcl.inventory.dto.InventorySearchRequest;
import com.hcl.inventory.entity.Inventory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryRepositoryCustomImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private InventoryRepositoryCustomImpl repository;

    @Test
    void shouldReturnPagedInventoryResults() {

        Inventory inventory =
                Inventory.builder()
                        .id("1")
                        .name("Laptop")
                        .build();

        when(
                mongoTemplate.find(
                        any(Query.class),
                        eq(Inventory.class)))
                .thenReturn(
                        List.of(inventory));

        when(
                mongoTemplate.count(
                        any(Query.class),
                        eq(Inventory.class)))
                .thenReturn(1L);

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .category("Electronics")
                        .build();

        Page<Inventory> page =
                repository.searchInventory(
                        request);

        assertNotNull(page);

        assertEquals(
                1,
                page.getTotalElements());

        assertEquals(
                "Laptop",
                page.getContent()
                        .get(0)
                        .getName());

        verify(mongoTemplate)
                .find(
                        any(Query.class),
                        eq(Inventory.class));

        verify(mongoTemplate)
                .count(
                        any(Query.class),
                        eq(Inventory.class));
    }

    @Test
    void shouldReturnEmptyPageWhenNoRecordsFound() {

        when(
                mongoTemplate.find(
                        any(Query.class),
                        eq(Inventory.class)))
                .thenReturn(
                        List.of());

        when(
                mongoTemplate.count(
                        any(Query.class),
                        eq(Inventory.class)))
                .thenReturn(0L);

        Page<Inventory> page =
                repository.searchInventory(
                        InventorySearchRequest
                                .builder()
                                .build());

        assertTrue(
                page.getContent()
                        .isEmpty());

        assertEquals(
                0,
                page.getTotalElements());
    }
}