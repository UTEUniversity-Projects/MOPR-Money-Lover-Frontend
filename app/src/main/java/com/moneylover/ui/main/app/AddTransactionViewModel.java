package com.moneylover.ui.main.app;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.ui.base.activity.BaseViewModel;

public class AddTransactionViewModel extends BaseViewModel {

    public AddTransactionViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
