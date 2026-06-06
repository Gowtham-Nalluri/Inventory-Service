package com.hcl.inventory.validator;

import com.hcl.inventory.dto.InventorySearchRequest;
import com.hcl.inventory.dto.Pagination;
import com.hcl.inventory.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InventorySearchValidatorTest {

    private InventorySearchValidator validator;

    @BeforeEach
    void setUp() {

        validator = new InventorySearchValidator();
    }

    @Test
    void shouldPassValidationWhenRequestIsValid() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .category("Electronics")
                        .minPrice(
                                new BigDecimal("10000"))
                        .maxPrice(
                                new BigDecimal("50000"))
                        .minStock(10)
                        .maxStock(100)
                        .manufacturingDate(
                                LocalDate.of(2024, 1, 1))
                        .expiryDate(
                                LocalDate.of(2030, 1, 1))
                        .pagination(
                                Pagination.builder()
                                        .page(0)
                                        .limit(10)
                                        .build())
                        .build();

        assertDoesNotThrow(
                () -> validator.validate(request));
    }

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

        assertEquals(
                1,
                exception.getErrors().size());

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
                                LocalDate.of(2025, 1, 1))
                        .expiryDate(
                                LocalDate.of(2024, 1, 1))
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
    void shouldThrowExceptionWhenPageIsNegative() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .pagination(
                                Pagination.builder()
                                        .page(-1)
                                        .limit(10)
                                        .build())
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
    void shouldThrowExceptionWhenLimitIsZero() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .pagination(
                                Pagination.builder()
                                        .page(0)
                                        .limit(0)
                                        .build())
                        .build();

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validator.validate(request));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "limit must be greater than zero"));
    }

    @Test
    void shouldThrowExceptionWhenLimitExceedsMaximumAllowed() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .pagination(
                                Pagination.builder()
                                        .page(0)
                                        .limit(501)
                                        .build())
                        .build();

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validator.validate(request));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "limit cannot exceed 500"));
    }

    @Test
    void shouldCollectMultipleValidationErrors() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .minPrice(
                                new BigDecimal("50000"))
                        .maxPrice(
                                new BigDecimal("10000"))
                        .minStock(100)
                        .maxStock(10)
                        .pagination(
                                Pagination.builder()
                                        .page(-1)
                                        .limit(0)
                                        .build())
                        .build();

        ValidationException exception =
                assertThrows(
                        ValidationException.class,
                        () -> validator.validate(request));

        assertEquals(
                4,
                exception.getErrors().size());

        assertTrue(
                exception.getErrors()
                        .contains(
                                "minPrice cannot exceed maxPrice"));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "minStock cannot exceed maxStock"));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "page cannot be negative"));

        assertTrue(
                exception.getErrors()
                        .contains(
                                "limit must be greater than zero"));
    }

    @Test
    void shouldAllowNullPagination() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .category("Electronics")
                        .build();

        assertDoesNotThrow(
                () -> validator.validate(request));
    }

    @Test
    void shouldAllowEmptyRequest() {

        InventorySearchRequest request =
                InventorySearchRequest.builder()
                        .build();

        assertDoesNotThrow(
                () -> validator.validate(request));
    }
}