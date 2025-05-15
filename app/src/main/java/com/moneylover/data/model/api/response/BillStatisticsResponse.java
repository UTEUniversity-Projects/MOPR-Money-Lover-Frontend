package com.moneylover.data.model.api.response;

import com.moneylover.data.model.api.DataWrapper;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class BillStatisticsResponse {
    private DataWrapper<BillResponse> pagination;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;
}