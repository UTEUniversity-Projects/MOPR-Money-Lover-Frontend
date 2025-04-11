package com.moneylover.ui.main;

import com.moneylover.ui.base.BaseCallback;

public interface MainCallback<T> extends BaseCallback {
    default void doSuccess(T object) {

    }
}