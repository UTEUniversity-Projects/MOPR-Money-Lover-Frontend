package com.moneylover.data.model.api.response;

import java.time.Instant;

import lombok.Data;

@Data
public class ReminderResponse {
    private Long id;

    private Instant time;
}
