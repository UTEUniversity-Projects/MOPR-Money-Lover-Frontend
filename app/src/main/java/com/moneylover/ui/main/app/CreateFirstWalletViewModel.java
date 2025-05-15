package com.moneylover.ui.main.app;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateWalletRequest;
import com.moneylover.data.model.api.response.CurrencyResponse;
import com.moneylover.data.model.api.response.FileResponse;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateFirstWalletViewModel extends BaseViewModel {

    public CreateFirstWalletViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doGetCurrencyByCode(MainCallback<List<CurrencyResponse>> callback, String code) {
        showLoading();
        compositeDisposable.add(repository.getApiService().getCurrencyByCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess((List<CurrencyResponse>) response.getData().getContent());
                    } else {
                        callback.doFail();
                    }
                    hideLoading();
                }, throwable -> {
                    hideLoading();
                    callback.doError(throwable);
                }));
    }

    public void doGetWalletIconOption(MainCallback<List<FileResponse>> callback) {
        showLoading();
        Map<String, Object> params = Map.of(
                "scope", "category_icons",
                "page", 0,
                "size", 1000,
                "fileName", "wallet"
        );
        compositeDisposable.add(repository.getApiService().getFileList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess((List<FileResponse>) response.getData().getContent());
                    } else {
                        callback.doFail();
                    }
                    hideLoading();
                }, throwable -> {
                    hideLoading();
                    callback.doError(throwable);
                }));
    }

    public void doCreateWallet(MainCallback<ResponseWrapper<?>> callback, CreateWalletRequest request) {
        compositeDisposable.add(repository.getApiService().createWallet(request)
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
