package com.hcl.inventory.validator;

import com.hcl.inventory.constants.InventoryConstants;
import com.hcl.inventory.dto.InventorySearchRequest;
import com.hcl.inventory.dto.Pagination;
import com.hcl.inventory.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InventorySearchValidator {

    public void validate(
            InventorySearchRequest request) {

        List<String> errors =
                new ArrayList<>();

        validatePrice(request, errors);

        validateStock(request, errors);

        validateDates(request, errors);

        validatePagination(request, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(
                    errors);
        }
    }

    private void validatePrice(
            InventorySearchRequest request,
            List<String> errors) {

        BigDecimal minPrice =
                request.getMinPrice();

        BigDecimal maxPrice =
                request.getMaxPrice();

        if (minPrice != null
                && minPrice.compareTo(
                BigDecimal.ZERO) < 0) {

            errors.add(
                    "minPrice cannot be negative");
        }

        if (maxPrice != null
                && maxPrice.compareTo(
                BigDecimal.ZERO) < 0) {

            errors.add(
                    "maxPrice cannot be negative");
        }

        if (minPrice != null
                && maxPrice != null
                && minPrice.compareTo(
                maxPrice) > 0) {

            errors.add(
                    "minPrice cannot exceed maxPrice");
        }
    }

    private void validateStock(
            InventorySearchRequest request,
            List<String> errors) {

        Integer minStock =
                request.getMinStock();

        Integer maxStock =
                request.getMaxStock();

        if (minStock != null
                && minStock < 0) {

            errors.add(
                    "minStock cannot be negative");
        }

        if (maxStock != null
                && maxStock < 0) {

            errors.add(
                    "maxStock cannot be negative");
        }

        if (minStock != null
                && maxStock != null
                && minStock > maxStock) {

            errors.add(
                    "minStock cannot exceed maxStock");
        }
    }

    private void validateDates(
            InventorySearchRequest request,
            List<String> errors) {

        LocalDate manufacturingDate =
                request.getManufacturingDate();

        LocalDate expiryDate =
                request.getExpiryDate();

        if (manufacturingDate != null
                && manufacturingDate.isAfter(
                LocalDate.now())) {

            errors.add(
                    "manufacturingDate cannot be in future");
        }

        if (manufacturingDate != null
                && expiryDate != null
                && manufacturingDate.isAfter(
                expiryDate)) {

            errors.add(
                    "manufacturingDate cannot be after expiryDate");
        }
    }

    private void validatePagination(
            InventorySearchRequest request,
            List<String> errors) {

        Pagination pagination =
                request.getPagination();

        if (pagination == null) {
            return;
        }

        Integer page =
                pagination.getPage();

        Integer limit =
                pagination.getLimit();

        if (page != null
                && page < 0) {

            errors.add(
                    "page cannot be negative");
        }

        if (limit != null
                && limit <= 0) {

            errors.add(
                    "limit must be greater than zero");
        }

        if (limit != null
                && limit >
                InventoryConstants.MAX_PAGE_LIMIT) {

            errors.add(
                    "limit cannot exceed "
                            + InventoryConstants.MAX_PAGE_LIMIT);
        }
    }
}