package com.moneylover.ui.main.app;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateTagRequest;
import com.moneylover.data.model.api.request.UpdateTagRequest;
import com.moneylover.data.model.api.response.TagResponse;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TagViewModel extends BaseViewModel {

    public TagViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doGetTagList(MainCallback<List<TagResponse>> callback) {
        Map<String, Object> params = Map.of(
                "page", 0,
                "size", 1000
        );
        showLoading();
        compositeDisposable.add(repository.getApiService().getTagList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                      callback.doSuccess(response.getData().getContent());
                    } else {
                        callback.doFail();
                    }
                }, throwable -> {
                    callback.doError(throwable);
                    hideLoading();
                }));
    }

    public void doAddTag(MainCallback<ResponseWrapper<?>> callback, CreateTagRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().createTag(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response);
                    } else {
                        callback.doFail();
                    }
                    hideLoading();
                }, throwable -> {
                    callback.doError(throwable);
                    hideLoading();
                }));
    }

    public void doEditTag(MainCallback<ResponseWrapper<?>> callback, UpdateTagRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().updateTag(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response);
                    } else {
                        callback.doFail();
                    }
                    hideLoading();
                }, throwable -> {
                    callback.doError(throwable);
                    hideLoading();
                }));
    }

    public void doDeleteTag(MainCallback<ResponseWrapper<?>> callback, Long id) {
        showLoading();
        compositeDisposable.add(repository.getApiService().deleteTag(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response);
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
