package com.moneylover.data.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportGroupType implements Serializable {
    private int icon;
    private String name;
    private String wallet;
    private String type;
}
