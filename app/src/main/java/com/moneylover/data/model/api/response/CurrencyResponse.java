package com.moneylover.data.model.api.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class CurrencyResponse implements Serializable {
    private Long id;

    private String name;

    private String code;

    private FileResponse icon;
}
