package com.moneylover.data.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenditureTransaction {
    private String day;
    private String dateOfWeek;
    private int total;
    private List<Expenditure> expenditures;
}
