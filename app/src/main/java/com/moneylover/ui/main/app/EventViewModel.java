package com.moneylover.ui.main.app;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.response.EventResponse;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class EventViewModel extends BaseViewModel {

    public EventViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doGetEventList(MainCallback<List<EventResponse>> callback) {
        Map<String, Object> params = Map.of(
          "page", 0,
          "size", 1000
        );

        showLoading();

        compositeDisposable.add(repository.getApiService().getEventList(params)
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
                    callback.doError(throwable);
                    hideLoading();
                }));
    }
}
