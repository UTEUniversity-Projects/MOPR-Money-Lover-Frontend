package com.moneylover.data.model.api.request;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEventRequest {
    private Long walletId;

    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private Long iconId;
}
