package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateBudgetRequest {
    private Long id;

    private Long periodId;

    private Long categoryId;

    private Double amount;
}
