package com.moneylover.data.model.api.request;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateReminderRequest {
    private Long id;

    private Instant time;
}
