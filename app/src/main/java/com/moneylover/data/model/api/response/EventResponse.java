package com.moneylover.data.model.api.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class EventResponse implements Serializable {
    private Long id;

    private Long walletId;

    private String name;

    private String description;

    private String startDate;

    private String endDate;

    private Boolean isCompleted;

    private FileResponse icon;
}
