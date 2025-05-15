package com.moneylover.data.model.api.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class TagResponse implements Serializable {
    private Long id;

    private String name;
}
