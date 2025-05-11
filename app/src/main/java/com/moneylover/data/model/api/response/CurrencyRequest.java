package com.moneylover.data.model.api.response;

import lombok.Data;

@Data
public class CurrencyRequest {
    private Long id;

    private String name;

    private String code;

    private FileResponse icon;
}
