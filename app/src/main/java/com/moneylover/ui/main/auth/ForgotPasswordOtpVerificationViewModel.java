package com.moneylover.ui.main.auth;

import com.moneylover.MVVMApplication;
import com.moneylover.constants.Constants;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.ForgotPasswordOtpVerificationRequest;
import com.moneylover.data.model.api.response.ForgotPasswordOtpVerificationResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgotPasswordOtpVerificationViewModel extends BaseFragmentViewModel {
    public ForgotPasswordOtpVerificationViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doForgotPasswordOtpVerification(MainCallback<ResponseWrapper<ForgotPasswordOtpVerificationResponse>> callback, ForgotPasswordOtpVerificationRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().resetPassword(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response.isResult()) {
                                callback.doSuccess(response);
                            } else {
                                callback.doFail();
                            }
                        }, callback::doError
                ));
    }

    public String getForgotPasswordToken() {
        return repository.getSharedPreferences().getStringVal(Constants.FORGOT_PASSWORD_TOKEN);
    }

    public void removeForgotPasswordToken() {
        repository.getSharedPreferences().removeKey(Constants.FORGOT_PASSWORD_TOKEN);
    }
}

