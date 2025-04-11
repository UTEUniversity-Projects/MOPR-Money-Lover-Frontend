package com.moneylover.ui.main.auth;

import com.moneylover.MVVMApplication;
import com.moneylover.constants.Constants;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.RegisterRequest;
import com.moneylover.data.model.api.response.RegisterResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterOtpVerificationViewModel extends BaseFragmentViewModel {
    public RegisterOtpVerificationViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public String getRegisterToken() {
        return repository.getSharedPreferences().getStringVal(Constants.REGISTER_TOKEN);
    }

    public void removeRegisterToken() {
        repository.getSharedPreferences().removeKey(Constants.REGISTER_TOKEN);
    }

    public void doRegister(MainCallback<ResponseWrapper<RegisterResponse>> callback, RegisterRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().register(request)
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

}
