package com.moneylover.ui.main.app;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.response.CategoryResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryListViewModel extends BaseFragmentViewModel {
    public CategoryListViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doGetCategoryList(MainCallback<List<CategoryResponse>> callback, Map<String, Object> params) {
        showLoading();
        compositeDisposable.add(repository.getApiService().getCategoryList(params)
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
}
