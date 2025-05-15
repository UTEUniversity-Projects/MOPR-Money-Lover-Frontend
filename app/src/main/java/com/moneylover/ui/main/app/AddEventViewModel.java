package com.moneylover.ui.main.app;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateEventRequest;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddEventViewModel extends BaseViewModel {

    public AddEventViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doCreateEvent(MainCallback<ResponseWrapper<?>> callback, CreateEventRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().createEvent(request)
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
