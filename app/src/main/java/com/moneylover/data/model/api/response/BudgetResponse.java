package com.moneylover.data.model.api.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BudgetResponse {
    private Long id;

    private CategoryStatisticsResponse categoryStatistics;

    private WalletResponse wallet;

    private Integer periodType;

    private String startDate;

    private String endDate;

    private BigDecimal amount;

    private BigDecimal spentAmount;
}
