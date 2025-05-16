package com.moneylover.ui.main.app.overview;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.response.BillDetailStatisticsResponse;
import com.moneylover.data.model.api.response.BillResponse;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OverviewViewModel extends BaseFragmentViewModel {

    public OverviewViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doGetFirstWallet(MainCallback<List<WalletResponse>> callback) {
        Map<String, Object> params = Map.of(
                "page", 0,
                "size", 1000,
                "chargeToTotal", true
        );
        compositeDisposable.add(repository.getApiService().getWalletList(params)
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
                }));
    }

    public void doGetTop3LatestTransaction(MainCallback<List<BillResponse>> callback) {
        Map<String, Object> params = Map.of(
                "page", 0,
                "size", 3
        );
        compositeDisposable.add(repository.getApiService().getBillList(params)
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
                }));
    }

    public void doGetStatisticsDetail(MainCallback<BillDetailStatisticsResponse> callback) {

        Map<String, Object> params = Map.of(
                "page", 0,
                "size", 1000
        );
        compositeDisposable.add(repository.getApiService().getDetailBillStatistics(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response.getData());
                    } else {
                        callback.doFail();
                    }
                }, throwable -> {
                    callback.doError(throwable);
                }));
    }

}
