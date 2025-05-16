package com.moneylover.ui.main.app;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateBillRequest;
import com.moneylover.data.model.api.request.CreateReminderRequest;
import com.moneylover.data.model.api.response.ReminderResponse;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddTransactionViewModel extends BaseViewModel {

    public AddTransactionViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doCreateReminder(MainCallback<ResponseWrapper<?>> callback, CreateReminderRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().createReminder(request)
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

    public void doGetLatestReminder(MainCallback<List<ReminderResponse>> callback) {
        Map<String, Object> params = Map.of("page", 0, "size", 1);
        showLoading();
        compositeDisposable.add(repository.getApiService().getReminderList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response.getData().getContent());
                    } else {
                        callback.doFail();
                    }
                    hideLoading();
                }, throwable -> {
                    hideLoading();
                    callback.doError(throwable);
                }));
    }

    public void doCreateBill(MainCallback<ResponseWrapper<?>> callback, CreateBillRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().createBill(request)
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
