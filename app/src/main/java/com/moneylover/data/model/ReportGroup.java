package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportGroup {
    private  String name;
    private float value;
    private int iconResId;
    private String date;
    private int subIconResId;

    public ReportGroup(String date, float value) {
        this.date = date;
        this.value = value;
    }

    public ReportGroup(String name, float value, int iconResId) {
        this.name = name;
        this.value = value;
        this.iconResId = iconResId;
    }

    public ReportGroup(int iconResId, String name, int subIconResId, float value) {
        this.iconResId = iconResId;
        this.name = name;
        this.subIconResId = subIconResId;
        this.value = value;
    }

    public ReportGroup(String name, float value, int iconResId, String date) {
        this.name = name;
        this.value = value;
        this.iconResId = iconResId;
        this.date = date;
    }
}