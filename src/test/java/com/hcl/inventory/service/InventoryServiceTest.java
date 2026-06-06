package com.hcl.inventory.service;

import com.hcl.inventory.dto.InventoryResponse;
import com.hcl.inventory.dto.InventorySearchRequest;
import com.hcl.inventory.entity.Inventory;
import com.hcl.inventory.repository.InventoryRepository;
import com.hcl.inventory.validator.InventorySearchValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository repository;

    @Mock
    private InventorySearchValidator validator;

    @InjectMocks
    private InventoryService service;

    @Test
    void shouldSearchInventorySuccessfully() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .category("Electronics")
                        .build();

        Inventory inventory =
                Inventory.builder()
                        .id("1")
                        .name("Laptop")
                        .category("Electronics")
                        .subCategory("Laptop")
                        .price(new BigDecimal("50000"))
                        .stock(10)
                        .seller("Amazon")
                        .location("Chennai")
                        .model("LAP-1")
                        .build();

        Page<Inventory> inventoryPage =
                new PageImpl<>(
                        List.of(inventory));

        when(repository.searchInventory(request))
                .thenReturn(inventoryPage);

        Page<InventoryResponse> response =
                service.searchInventory(request);

        assertNotNull(response);

        assertEquals(
                1,
                response.getTotalElements());

        InventoryResponse inventoryResponse =
                response.getContent().get(0);

        assertEquals(
                "Laptop",
                inventoryResponse.name());

        verify(validator)
                .validate(request);

        verify(repository)
                .searchInventory(request);
    }
}