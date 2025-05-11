package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBudgetRequest {
    private Long periodId;

    private Long categoryId;

    private Double amount;
}
