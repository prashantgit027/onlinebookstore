package com.example.onlinebookstore.service;

import java.math.BigDecimal;

/**
 * Strategy pattern: pluggable pricing calculation for order line items.
 * Allows future strategies (e.g. bulk discounts, promotions) to be added
 * without changing CartService/OrderFactory.
 */
public interface PricingStrategy {
    BigDecimal lineTotal(BigDecimal unitPrice, int quantity);
}
