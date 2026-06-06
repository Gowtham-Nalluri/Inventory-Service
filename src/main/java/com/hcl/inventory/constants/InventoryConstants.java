package com.hcl.inventory.constants;

public final class InventoryConstants {

    private InventoryConstants() {
    }

    public static final int DEFAULT_PAGE = 0;

    public static final int DEFAULT_LIMIT = 10;

    public static final int MAX_PAGE_LIMIT = 500;

    public static final String NAME = "name";

    public static final String CATEGORY = "category";

    public static final String SUB_CATEGORY = "subCategory";

    public static final String SELLER = "seller";

    public static final String LOCATION = "location";

    public static final String MODEL = "model";

    public static final String PRICE = "price";

    public static final String STOCK = "stock";

    public static final String VALIDATION_ERROR_CODE = "INV-400";

    public static final String INTERNAL_ERROR_CODE = "INV-500";

    public static final String MIN_PRICE_NEGATIVE =
            "minPrice cannot be negative";

    public static final String MAX_PRICE_NEGATIVE =
            "maxPrice cannot be negative";

    public static final String PRICE_RANGE_INVALID =
            "minPrice cannot exceed maxPrice";

    public static final String MIN_STOCK_NEGATIVE =
            "minStock cannot be negative";

    public static final String MAX_STOCK_NEGATIVE =
            "maxStock cannot be negative";

    public static final String STOCK_RANGE_INVALID =
            "minStock cannot exceed maxStock";

    public static final String FUTURE_MANUFACTURING_DATE =
            "manufacturingDate cannot be in future";

    public static final String INVALID_DATE_RANGE =
            "manufacturingDate cannot be after expiryDate";

    public static final String NEGATIVE_PAGE =
            "page cannot be negative";

    public static final String INVALID_LIMIT =
            "limit must be greater than zero";

    public static final String MAX_LIMIT_EXCEEDED =
            "limit cannot exceed ";
}