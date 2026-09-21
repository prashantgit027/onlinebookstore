package com.example.onlinebookstore.domain;

import lombok.Builder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;

    @Version
    private Integer version;

    public Book() {}

    @Builder
    public Book(String title, String author, BigDecimal price, Integer stock) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public java.math.BigDecimal getPrice() { return price; }
    public void setPrice(java.math.BigDecimal price) { this.price = price; }
    public Integer getStock() { return stock; }
    
    // Domain behaviour: handle stock changes within the aggregate
    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new com.example.onlinebookstore.exception.InvalidQuantityException("Quantity must be positive");
        }
        if (this.stock == null || this.stock < quantity) {
            throw new com.example.onlinebookstore.exception.InsufficientStockException("Insufficient stock for book: " + this.title);
        }
        this.stock = this.stock - quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new com.example.onlinebookstore.exception.InvalidQuantityException("Quantity must be positive");
        }
        if (this.stock == null) this.stock = 0;
        this.stock = this.stock + quantity;
    }

    // Setter kept for deserialization/mapping but use domain methods in business logic
    public void setStock(Integer stock) {
        if (stock != null && stock < 0) throw new com.example.onlinebookstore.exception.InvalidQuantityException("Stock cannot be negative");
        this.stock = stock;
    }
}
