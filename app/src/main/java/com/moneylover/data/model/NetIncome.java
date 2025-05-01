package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NetIncome {
    private String date;
    private int expenditure;
    private int income;
    private int iconResId;
    private int subIconResId;
    private String name;
    private int value;

    public NetIncome(String date, int expenditure, int income) {
        this.date = date;
        this.expenditure = expenditure;
        this.income = income;
    }

    public NetIncome(int iconResId, String name, int subIconResId, int value) {
        this.iconResId = iconResId;
        this.name = name;
        this.subIconResId = subIconResId;
        this.value = value;
    }
}
