package com.moneylover.data.model.api.request;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private String name;

    private String description;

    private Boolean isExpense;

    private Long iconId;
}
