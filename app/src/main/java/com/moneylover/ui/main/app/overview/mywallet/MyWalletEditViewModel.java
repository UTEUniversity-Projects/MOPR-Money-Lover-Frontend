package com.moneylover.ui.main.app.overview.mywallet;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.UpdateWalletRequest;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyWalletEditViewModel extends BaseViewModel {

    public MyWalletEditViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doDeleteWallet(MainCallback<ResponseWrapper<?>> callback, Long id) {
        showLoading();
        compositeDisposable.add(repository.getApiService().deleteWallet(id)
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
                    callback.doError(throwable);
                    hideLoading();
                }));
    }

    public void doUpdateWallet(MainCallback<ResponseWrapper<?>> callback, UpdateWalletRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().updateWallet(request)
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
                    callback.doError(throwable);
                    hideLoading();
                }));
    }

}
