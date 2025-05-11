package com.moneylover.ui.base;

public interface BaseCallback {
    void doError(Throwable error);

    void doSuccess();

    void doFail();
}
