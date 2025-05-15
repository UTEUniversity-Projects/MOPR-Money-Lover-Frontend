package com.moneylover.ui.main.app.budget;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;

public class BudgetViewModel extends BaseFragmentViewModel {

    public BudgetViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
