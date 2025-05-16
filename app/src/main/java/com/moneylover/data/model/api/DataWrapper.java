package com.moneylover.data.model.api;

import java.util.List;

import lombok.Data;

@Data
public class DataWrapper<T> {
    private List<T> content;
    private int totalElements;
    private int totalPages;
}
