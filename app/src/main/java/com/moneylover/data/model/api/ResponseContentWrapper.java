package com.moneylover.data.model.api;

import lombok.Data;

@Data
public class ResponseContentWrapper<T> {
    private boolean result;
    private DataWrapper<T> data;
    private String message;
}
