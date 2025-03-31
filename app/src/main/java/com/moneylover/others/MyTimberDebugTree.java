package com.moneylover.others;

import timber.log.Timber;

public class MyTimberDebugTree extends Timber.DebugTree {
    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return String.format("[L:%d] [M:%s] [C:%s] **",
                element.getLineNumber(),
                element.getMethodName(),
                element.getClassName());
    }
}
