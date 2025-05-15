package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateEventRequest {
    private Long walletId;

    private String name;

    private String description;

    private String startDate;

    private String endDate;

    private Long iconId;
}
