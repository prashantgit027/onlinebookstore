package com.example.onlinebookstore.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookTitle;
    private BigDecimal price;
    private Integer quantity;

    public OrderItem() {}
    public OrderItem(String bookTitle, BigDecimal price, Integer quantity) { this.bookTitle = bookTitle; this.price = price; this.quantity = quantity; }
}
