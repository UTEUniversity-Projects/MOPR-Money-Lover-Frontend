package com.moneylover.data.model.api.request;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBudgetRequest {
    private Long categoryId;

    private Long walletId;

    private Integer periodType;

    private String startDate;

    private String endDate;

    private BigDecimal amount;
}
