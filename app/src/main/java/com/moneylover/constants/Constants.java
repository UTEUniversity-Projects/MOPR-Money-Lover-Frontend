package com.moneylover.constants;

public class Constants {
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$&*]).*$";
    public static final String ACTION_EXPIRED_TOKEN ="ACTION_EXPIRED_TOKEN";
    public static final String VALUE_BEARER_TOKEN_DEFAULT="NULL";
    public static final String VALUE_USER_NAME="NULL";
    public static final String PREF_NAME = "mvvm.prefs";
    public static final String LOGIN_TYPE = "LOGIN";
    public static final String REGISTER_TYPE = "REGISTER";
    public static final String REGISTER_TOKEN = "REGISTER_TOKEN";
    public static final String FORGOT_PASSWORD_TOKEN = "FORGOT_PASSWORD_TOKEN";
    public static final Integer MENU_OPTION_TYPE_DEFAULT = 0;
    public static final Integer MENU_OPTION_TYPE_DATE_TIME = 1;

    public static final Integer PERIOD_TYPE_WEEK = 1;
    public static final Integer PERIOD_TYPE_MONTH = 2;
    public static final Integer PERIOD_TYPE_QUARTER = 3;
    public static final Integer PERIOD_TYPE_YEAR = 4;
    public static final Integer PERIOD_TYPE_CUSTOM = 5;

    public static final String PERIOD_NAME_WEEK = "Tuần này";
    public static final String PERIOD_NAME_MONTH = "Tháng này";
    public static final String PERIOD_NAME_QUARTER = "Quý này";
    public static final String PERIOD_NAME_YEAR = "Năm này";
    public static final String PERIOD_NAME_CUSTOM = "Đợt này";
}
