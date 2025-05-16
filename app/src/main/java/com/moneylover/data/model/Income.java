package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Income {
    public String name;
    public int value;
    public int iconResId;
    public String date;
    private int subIconResId;

    public Income(String name, int value, int iconResId) {
        this.name = name;
        this.value = value;
        this.iconResId = iconResId;
    }

    public Income(int iconResId, String name, int subIconResId, int value) {
        this.iconResId = iconResId;
        this.name = name;
        this.subIconResId = subIconResId;
        this.value = value;
    }

    public Income(String name, int value, int iconResId, String date) {
        this.name = name;
        this.value = value;
        this.iconResId = iconResId;
        this.date = date;
    }


}
