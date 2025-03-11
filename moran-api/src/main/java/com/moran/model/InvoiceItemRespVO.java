package com.moran.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemRespVO {

    private Long categoryId;

    private String category;

    private String description;

    private BigDecimal quantity;

    private BigDecimal unitAmount;

    private BigDecimal amount;
}