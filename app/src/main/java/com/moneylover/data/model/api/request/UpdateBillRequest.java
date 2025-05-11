package com.moneylover.data.model.api.request;

import java.time.Instant;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBillRequest {
    private Long id;

    private Double amount;

    private Instant date;

    private String note;

    private Boolean isIncludedReport;

    private Long categoryId;

    private List<Long> tagIds;

    private Long eventId;

    private Long reminderId;

    private Long pictureId;
}
