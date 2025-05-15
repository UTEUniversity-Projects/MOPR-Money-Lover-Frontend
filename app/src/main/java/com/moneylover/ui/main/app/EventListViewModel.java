package com.moneylover.ui.main.app;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;

public class EventListViewModel extends BaseFragmentViewModel {
    public EventListViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
