package com.moneylover.ui.main.app.transactionHistory;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.response.BillDetailStatisticsResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TransactionHistoryViewModel extends BaseFragmentViewModel {

    public TransactionHistoryViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
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
