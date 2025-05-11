package com.moneylover.data.model.api.response;

import lombok.Data;

import java.time.Instant;

@Data
public class NotificationResponse {
    private Long id;

    private String content;

    private Boolean isRead;

    private Integer type;

    private Integer scope;

    private Instant createdAt;
}
