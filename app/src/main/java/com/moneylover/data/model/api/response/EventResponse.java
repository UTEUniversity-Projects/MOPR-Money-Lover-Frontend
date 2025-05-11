package com.moneylover.data.model.api.response;

import java.time.Instant;

import lombok.Data;

@Data
public class EventResponse {
    private Long id;

    private Long walletId;

    private String name;

    private String description;

    private Instant startDate;

    private Instant endDate;

    private Boolean isCompleted;

    private FileResponse icon;
}
