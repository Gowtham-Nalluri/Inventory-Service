package com.hcl.inventory.validator;

import com.hcl.inventory.constants.InventoryConstants;
import com.hcl.inventory.dto.InventorySearchRequest;
import com.hcl.inventory.dto.Pagination;
import com.hcl.inventory.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventorySearchValidator {

    public void validate(
            InventorySearchRequest request) {

        List<String> errors =
                new ArrayList<>();

        validatePriceRange(request, errors);

        validateStockRange(request, errors);

        validateDates(request, errors);

        validatePagination(request, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validatePriceRange(
            InventorySearchRequest request,
            List<String> errors) {

        if (request.getMinPrice() != null
                && request.getMaxPrice() != null
                && request.getMinPrice()
                .compareTo(request.getMaxPrice()) > 0) {
            errors.add("minPrice cannot exceed maxPrice");
        }
    }

    private void validateStockRange(
            InventorySearchRequest request,
            List<String> errors) {

        if (request.getMinStock() != null
                && request.getMaxStock() != null
                && request.getMinStock()
                > request.getMaxStock()) {
            errors.add("minStock cannot exceed maxStock");
        }
    }

    private void validateDates(
            InventorySearchRequest request,
            List<String> errors) {

        if (request.getManufacturingDate() != null
                && request.getExpiryDate() != null
                && request.getManufacturingDate()
                .isAfter(request.getExpiryDate())) {
            errors.add("manufacturingDate cannot be after expiryDate");
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

        if (pagination.getPage() != null
                && pagination.getPage() < 0) {
            errors.add("page cannot be negative");
        }

        if (pagination.getLimit() != null
                && pagination.getLimit() <= 0) {
            errors.add("limit must be greater than zero");
        }

        if (pagination.getLimit() != null
                && pagination.getLimit()
                > InventoryConstants.MAX_PAGE_LIMIT) {
            errors.add("limit cannot exceed " + InventoryConstants.MAX_PAGE_LIMIT);
        }
    }
}