package com.moneylover.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {

    public static String formatNumberWithComma(int number) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(number);
    }

    public static String formatNumberWithComma(double number) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(number);
    }

    public static String formatNumberWithComma(BigDecimal number) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(number);
    }

}
