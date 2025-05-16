package com.moneylover.data.model.api.response;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Data;

@Data
public class PeriodBreakdownResponse {
    private Instant startDate;

    private Instant endDate;

    private String label;

    private BigDecimal amount;
}