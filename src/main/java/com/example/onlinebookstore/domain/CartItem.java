package com.example.onlinebookstore.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book book;

    private Integer quantity;

    public CartItem() {}
    public CartItem(Book book, Integer quantity) { this.book = book; this.quantity = quantity; }

    // Domain behaviour: change quantity with validation
    public void setQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new com.example.onlinebookstore.exception.InvalidQuantityException("Cart item quantity must be positive");
        }
        this.quantity = quantity;
    }

    public void increaseQuantity(int delta) {
        if (delta <= 0) throw new com.example.onlinebookstore.exception.InvalidQuantityException("Delta must be positive");
        if (this.quantity == null) this.quantity = 0;
        this.quantity = this.quantity + delta;
    }

    public void decreaseQuantity(int delta) {
        if (delta <= 0) throw new com.example.onlinebookstore.exception.InvalidQuantityException("Delta must be positive");
        if (this.quantity == null || this.quantity - delta <= 0) {
            throw new com.example.onlinebookstore.exception.InvalidQuantityException("Resulting quantity must be positive");
        }
        this.quantity = this.quantity - delta;
    }
}
