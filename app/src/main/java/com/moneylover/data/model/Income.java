package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Income {
    public String name;
    public float value;
    public int iconRes;
    public String date;

    public Income(String name, float value, int iconRes) {
        this.name = name;
        this.value = value;
        this.iconRes = iconRes;
    }
}
