package com.moneylover.data.model.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryRequest {
    private Long id;

    private String name;

    private String description;

    private Boolean isExpense;

    private Long iconId;
}
