package com.moneylover.ui;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    public MainViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doGetWalletList(MainCallback<List<WalletResponse>> callback) {
        Map<String, Object> params = Map.of(
                "page", 0,
                "size", 1000
        );
        compositeDisposable.add(repository.getApiService().getWalletList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess((List<WalletResponse>) response.getData().getContent());
                    } else {
                        callback.doFail();
                    }
                }, throwable -> {
                    hideLoading();
                    callback.doError(throwable);
                }));
    }

}
