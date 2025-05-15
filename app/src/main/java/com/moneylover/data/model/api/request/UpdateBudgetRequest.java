package com.moneylover.data.model.api.request;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBudgetRequest {
    private Long id;

    private Long categoryId;

    private Long walletId;

    private Integer periodType;

    private Instant startDate;

    private Instant endDate;

    private BigDecimal amount;
}
