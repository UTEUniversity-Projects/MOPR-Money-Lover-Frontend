package com.moneylover.ui.main.auth;

import com.moneylover.MVVMApplication;
import com.moneylover.constants.Constants;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.LoginRequest;
import com.moneylover.data.model.api.response.LoginResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends BaseFragmentViewModel {

    public LoginViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void doLogin(MainCallback<ResponseWrapper<LoginResponse>> callback, LoginRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().login(request)
                .subscribeOn(Schedulers.io())
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


    public void setAccessToken(String token) {
        repository.getSharedPreferences().setString(Constants.ACCESS_TOKEN, token);
    }

}
