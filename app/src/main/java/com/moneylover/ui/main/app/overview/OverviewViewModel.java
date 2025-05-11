package com.moneylover.ui.main.app.overview;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;

public class OverviewViewModel extends BaseFragmentViewModel {

    public OverviewViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doGetFirstWallet(MainCallback<List<ResponseWrapper<WalletResponse>>> callback) {}
}
