package com.moneylover.data.model.api.response;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class BillResponse {
    private Long id;

    private Double amount;

    private Instant date;

    private String note;

    private Boolean isIncludedReport;

    private WalletResponse wallet;

    private CategoryResponse category;

    private List<TagResponse> tags;

    private EventResponse event;

    private ReminderResponse reminder;

    private FileResponse picture;
}