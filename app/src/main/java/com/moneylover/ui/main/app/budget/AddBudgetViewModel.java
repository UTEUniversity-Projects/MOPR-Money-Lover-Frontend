package com.moneylover.ui.main.app.budget;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateBudgetRequest;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddBudgetViewModel extends BaseViewModel {

    public AddBudgetViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doCreateBudget(MainCallback<ResponseWrapper<?>> callback, CreateBudgetRequest request) {
        compositeDisposable.add(repository.getApiService().createBudget(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess();
                    } else {
                        callback.doFail();
                    }
                }, throwable -> {
                    callback.doError(throwable);
                }));
    }
}
