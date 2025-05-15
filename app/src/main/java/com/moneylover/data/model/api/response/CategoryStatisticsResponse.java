package com.moneylover.data.model.api.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CategoryStatisticsResponse {
    private CategoryResponse category;

    private BigDecimal totalAmount;

    private BigDecimal percentage;
}