package com.moneylover.data.model.api.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;

    private String name;

    private String description;

    private Boolean isExpense;

    private FileResponse icon;
}
