package com.example.onlinebookstore.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AddToCartRequest {
    @NotNull
    private Long bookId;

    @NotNull
    @Min(1)
    private Integer qty;
}
