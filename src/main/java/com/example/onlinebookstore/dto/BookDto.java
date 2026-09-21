package com.example.onlinebookstore.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class BookDto {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer stock;
}
