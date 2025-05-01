package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Expenditure {
    private  String name;
    private float value;
    private int iconResId;
    private String date;
    private int subIconResId;

    public Expenditure(String name, float value, int iconResId) {
        this.name = name;
        this.value = value;
        this.iconResId = iconResId;
    }

    public Expenditure(int iconResId, String name, int subIconResId, float value) {
        this.iconResId = iconResId;
        this.name = name;
        this.subIconResId = subIconResId;
        this.value = value;
    }

    public Expenditure(String name, float value, int iconResId, String date) {
        this.name = name;
        this.value = value;
        this.iconResId = iconResId;
        this.date = date;
    }
}
