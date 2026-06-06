package com.hcl.inventory.validator;

import com.hcl.inventory.dto.InventorySearchRequest;
import com.hcl.inventory.dto.Pagination;
import com.hcl.inventory.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InventorySearchValidatorTest {

    private final InventorySearchValidator validator =
            new InventorySearchValidator();

    @Test
    void shouldThrowExceptionWhenMinPriceGreaterThanMaxPrice() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .minPrice(
                                new BigDecimal("50000"))
                        .maxPrice(
                                new BigDecimal("10000"))
                        .build();

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validator.validate(request));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "minPrice cannot exceed maxPrice"));
    }

    @Test
    void shouldThrowExceptionWhenMinStockGreaterThanMaxStock() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .minStock(100)
                        .maxStock(10)
                        .build();

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validator.validate(request));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "minStock cannot exceed maxStock"));
    }

    @Test
    void shouldThrowExceptionWhenManufacturingDateAfterExpiryDate() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .manufacturingDate(
                                LocalDate.now())
                        .expiryDate(
                                LocalDate.now()
                                        .minusDays(1))
                        .build();

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validator.validate(request));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "manufacturingDate cannot be after expiryDate"));
    }

    @Test
    void shouldThrowExceptionForNegativePage() {

        Pagination pagination =
                Pagination.builder()
                        .page(-1)
                        .limit(10)
                        .build();

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .pagination(pagination)
                        .build();

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validator.validate(request));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "page cannot be negative"));
    }

    @Test
    void shouldPassValidation() {

        Pagination pagination =
                Pagination.builder()
                        .page(0)
                        .limit(10)
                        .build();

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .category("Electronics")
                        .pagination(pagination)
                        .build();

        assertDoesNotThrow(
                () -> validator.validate(request));
    }
}