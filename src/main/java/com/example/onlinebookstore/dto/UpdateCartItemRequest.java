package com.example.onlinebookstore.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCartItemRequest {
    @NotNull
    private Long cartItemId;

    @NotNull
    @Min(1)
    private Integer qty;
}
