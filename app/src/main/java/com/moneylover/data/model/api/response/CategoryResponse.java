package com.moneylover.data.model.api.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryResponse implements Serializable {
    private Long id;

    private String name;

    private String description;

    private Boolean isExpense;

    private FileResponse icon;
}
