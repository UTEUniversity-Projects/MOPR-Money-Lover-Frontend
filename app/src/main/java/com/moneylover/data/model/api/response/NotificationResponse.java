package com.moneylover.data.model.api.response;

import java.time.Instant;

import lombok.Data;

@Data
public class NotificationResponse {
    private Long id;

    private WalletResponse wallet;

    private String content;

    private Boolean isRead;

    private Integer type;

    private Integer scope;

    private Instant createdDate;
}
