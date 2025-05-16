package com.moneylover.data.model.api.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BillDetailStatisticsResponse {
    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private List<CategoryStatisticsResponse> incomeByCategories = new ArrayList<>();

    private List<CategoryStatisticsResponse> expenseByCategories = new ArrayList<>();
}