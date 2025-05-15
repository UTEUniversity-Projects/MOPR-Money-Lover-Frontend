package com.moneylover.ui.main.app.budget;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;

public class BudgetListViewModel extends BaseFragmentViewModel {
    public BudgetListViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
