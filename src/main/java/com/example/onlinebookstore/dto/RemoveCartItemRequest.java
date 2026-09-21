package com.example.onlinebookstore.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RemoveCartItemRequest {
    @NotNull
    private Long cartItemId;
}
