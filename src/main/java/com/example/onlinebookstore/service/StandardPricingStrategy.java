package com.example.onlinebookstore.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Default {@link PricingStrategy}: unit price multiplied by quantity, no
 * discounts applied.
 */
@Component
public class StandardPricingStrategy implements PricingStrategy {
    @Override
    public BigDecimal lineTotal(BigDecimal unitPrice, int quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
