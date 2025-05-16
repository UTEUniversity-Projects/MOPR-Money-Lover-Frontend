package com.moneylover.data.model.api.response;

import java.time.Instant;

import lombok.Data;

@Data
public class PeriodResponse {
    private Long id;

    private WalletResponse wallet;

    private Double totalAmount;

    private Double totalSpent;

    private Double balance;

    private Instant startDate;

    private Instant endDate;
}
