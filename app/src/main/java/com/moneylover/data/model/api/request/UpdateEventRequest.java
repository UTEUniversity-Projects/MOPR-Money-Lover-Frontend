package com.moneylover.data.model.api.request;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateEventRequest {
    private Long id;

    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private Long iconId;
}
