package com.moneylover.ui.main.app.transactionHistory;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;

public class TransactionHistoryListViewModel extends BaseFragmentViewModel {

    public TransactionHistoryListViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
