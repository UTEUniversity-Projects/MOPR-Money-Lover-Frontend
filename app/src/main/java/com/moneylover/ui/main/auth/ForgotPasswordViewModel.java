package com.moneylover.ui.main.auth;

import com.moneylover.MVVMApplication;
import com.moneylover.constants.Constants;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.RequestForgotPasswordRequest;
import com.moneylover.data.model.api.response.RequestForgotPasswordResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgotPasswordViewModel extends BaseFragmentViewModel {

    public ForgotPasswordViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doRequestForgotPassword(MainCallback<ResponseWrapper<RequestForgotPasswordResponse>> callback, RequestForgotPasswordRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().requestForgotPassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isResult()) {
                        callback.doSuccess(response);
                    } else {
                        callback.doFail();
                    }
                }, callback::doError));
    }

    public void setForgotPasswordToken(String token) {
        repository.getSharedPreferences().setString(Constants.FORGOT_PASSWORD_TOKEN, token);
    }
}
