package com.moneylover.ui.main.app;

import androidx.annotation.Nullable;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.response.FileResponse;
import com.moneylover.ui.base.activity.BaseViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WalletIconOptionViewModel extends BaseViewModel {
    private int currentPage = 0;
    private final int pageSize = 100;
    private boolean isLastPage = false;
    private boolean isLoadingMore = false;

    public WalletIconOptionViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doGetWalletList(MainCallback<List<FileResponse>> callback, @Nullable Runnable onStart) {
        if (isLoadingMore || isLastPage) return;
        isLoadingMore = true;

        if (onStart != null) onStart.run();
        Map<String, Object> params = Map.of(
                "scope", "category_icons",
                "page", currentPage,
                "size", pageSize
        );
        compositeDisposable.add(repository.getApiService()
                .getFileList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        List<FileResponse> newItems = (List<FileResponse>) response.getData().getContent();
                        callback.doSuccess(newItems);
                        currentPage++;
                        if (newItems.size() < pageSize) {
                            isLastPage = true;
                        }
                    } else {
                        callback.doFail();
                    }
                    isLoadingMore = false;
                }, throwable -> {
                    isLoadingMore = false;
                    callback.doError(throwable);
                }));
    }

    public void resetPaging() {
        currentPage = 0;
        isLastPage = false;
    }

    public boolean isLoading() {
        return isLoadingMore;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

}

