package com.hcl.inventory.repository;

import com.hcl.inventory.constants.InventoryConstants;
import com.hcl.inventory.dto.InventorySearchRequest;
import com.hcl.inventory.dto.Pagination;
import com.hcl.inventory.dto.SortingRequest;
import com.hcl.inventory.entity.Inventory;
import com.hcl.inventory.enums.SortDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryCustomImpl
        implements InventoryRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Inventory> searchInventory(
            InventorySearchRequest request) {

        Query query = new Query();

        List<Criteria> criteriaList =
                buildCriteria(request);

        if (!criteriaList.isEmpty()) {

            query.addCriteria(
                    new Criteria()
                            .andOperator(
                                    criteriaList.toArray(
                                            new Criteria[0])));
        }

        Pageable pageable =
                buildPageable(request);

        query.with(pageable);

        List<Inventory> inventories =
                mongoTemplate.find(
                        query,
                        Inventory.class);

        long totalRecords =
                mongoTemplate.count(
                        Query.of(query)
                                .limit(-1)
                                .skip(-1),
                        Inventory.class);

        return new PageImpl<>(
                inventories,
                pageable,
                totalRecords);
    }

    private Pageable buildPageable(
            InventorySearchRequest request) {

        Pagination pagination =
                request.getPagination();

        int page =
                pagination != null
                        && pagination.getPage() != null
                        ? pagination.getPage()
                        : InventoryConstants.DEFAULT_PAGE;

        int limit =
                pagination != null
                        && pagination.getLimit() != null
                        ? pagination.getLimit()
                        : InventoryConstants.DEFAULT_LIMIT;

        return PageRequest.of(
                page,
                limit,
                buildSort(request));
    }

    private Sort buildSort(
            InventorySearchRequest request) {

        SortingRequest sorting =
                request.getSorting();

        if (sorting == null
                || sorting.getField() == null) {
            return Sort.unsorted();
        }

        Sort.Direction direction =
                sorting.getDirection()
                        == SortDirection.DESC
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        return Sort.by(
                direction,
                sorting.getField()
                        .name()
                        .toLowerCase());
    }

    private List<Criteria> buildCriteria(
            InventorySearchRequest request) {

        List<Criteria> criteriaList =
                new ArrayList<>();

        addStringCriteria(
                criteriaList,
                InventoryConstants.NAME,
                request.getName());

        addStringCriteria(
                criteriaList,
                InventoryConstants.CATEGORY,
                request.getCategory());

        addStringCriteria(
                criteriaList,
                InventoryConstants.SELLER,
                request.getSeller());

        addStringCriteria(
                criteriaList,
                InventoryConstants.LOCATION,
                request.getLocation());

        if (request.getMinPrice() != null) {

            criteriaList.add(
                    Criteria.where(
                                    InventoryConstants.PRICE)
                            .gte(
                                    request.getMinPrice()));
        }

        if (request.getMaxPrice() != null) {

            criteriaList.add(
                    Criteria.where(
                                    InventoryConstants.PRICE)
                            .lte(
                                    request.getMaxPrice()));
        }

        return criteriaList;
    }

    private void addStringCriteria(
            List<Criteria> criteriaList,
            String field,
            String value) {

        if (value != null
                && !value.isBlank()) {

            criteriaList.add(
                    Criteria.where(field)
                            .regex(value, "i"));
        }
    }
}