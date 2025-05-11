package com.moneylover.ui.main.app.account;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;

public class AccountViewModel extends BaseFragmentViewModel {

    public AccountViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void removeAccessToken() {
        repository.getSharedPreferences().setToken(null);
    }
}
