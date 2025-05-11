package com.moneylover.data.model.api.response;

import lombok.Data;

@Data
public class BudgetResponse {
    private Long id;

    private PeriodResponse period;

    private CategoryResponse category;

    private Double amount;

    private Double balance;
}
