package com.moneylover.ui.main.app.account;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AccountViewModel extends BaseFragmentViewModel {

    public AccountViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void removeAccessToken() {
        repository.getSharedPreferences().setToken(null);
    }

    public void doLogOut(MainCallback<ResponseWrapper<Void>> callback) {
        showLoading();
        compositeDisposable.add(repository.getApiService().logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess();
                    } else {
                        callback.doFail();
                    }
                    hideLoading();
                }, throwable -> {
                    hideLoading();
                    callback.doError(throwable);
                }));
    }
}
