package com.moneylover.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Expenditure {
    public String name;
    public float value;
    public int iconRes;
}
