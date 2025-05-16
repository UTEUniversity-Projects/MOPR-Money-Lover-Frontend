package com.moneylover.data.model.api.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CategoryDetailStatisticsResponse {
    private CategoryResponse category;

    private BigDecimal totalAmount;

    private BigDecimal dailyAverage;

    private Instant startDate;

    private Instant endDate;

    private Integer periodType;

    private List<PeriodBreakdownResponse> periodBreakdown = new ArrayList<>();
}