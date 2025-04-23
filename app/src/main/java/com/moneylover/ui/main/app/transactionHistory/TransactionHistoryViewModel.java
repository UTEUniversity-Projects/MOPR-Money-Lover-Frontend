package com.moneylover.ui.main.app.transactionHistory;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;

public class TransactionHistoryViewModel extends BaseFragmentViewModel {

    public TransactionHistoryViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
