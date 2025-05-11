package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateEventCompletedRequest {
    private Long id;

    private Boolean isCompleted;
}
