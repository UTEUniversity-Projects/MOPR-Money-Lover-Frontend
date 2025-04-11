package com.moneylover.ui.base.activity;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.others.ToastMessage;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import lombok.Getter;
import lombok.Setter;

public class BaseViewModel extends ViewModel {

    protected CompositeDisposable compositeDisposable;
    protected final ObservableBoolean mIsLoading = new ObservableBoolean();
    protected final MutableLiveData<String> progressBarMsg = new MutableLiveData<>();
    protected final MutableLiveData<ToastMessage> mErrorMessage = new MutableLiveData<>();

    @Setter
    protected String token;

    @Setter
    protected String deviceId;
    @Getter
    protected final Repository repository;
    @Getter
    protected final MVVMApplication application;

    public BaseViewModel(Repository repository, MVVMApplication application) {
        this.repository = repository;
        this.application = application;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void showLoading() {
        mIsLoading.set(true);
    }

    public void hideLoading() {
        mIsLoading.set(false);
    }

    public void showSuccessMessage(String message) {
        mErrorMessage.setValue(new ToastMessage(ToastMessage.TYPE_SUCCESS, message));
    }

    public void showNormalMessage(String message) {
        mErrorMessage.setValue(new ToastMessage(ToastMessage.TYPE_NORMAL, message));
    }

    public void showWarningMessage(String message) {
        mErrorMessage.setValue(new ToastMessage(ToastMessage.TYPE_WARNING, message));
    }

    public void showErrorMessage(String message) {
        mErrorMessage.setValue(new ToastMessage(ToastMessage.TYPE_ERROR, message));
    }

    public void showSuccessMessage(String message, int gravity) {
        mErrorMessage.setValue(new ToastMessage(ToastMessage.TYPE_SUCCESS, message, gravity));
    }

    public void showNormalMessage(String message, int gravity) {
        mErrorMessage.setValue(new ToastMessage(ToastMessage.TYPE_NORMAL, message, gravity));
    }

    public void showWarningMessage(String message, int gravity) {
        mErrorMessage.setValue(new ToastMessage(ToastMessage.TYPE_WARNING, message, gravity));
    }

    public void showErrorMessage(String message, int gravity) {
        mErrorMessage.setValue(new ToastMessage(ToastMessage.TYPE_ERROR, message, gravity));
    }

    public void changeProgressBarMsg(String message) {
        progressBarMsg.setValue(message);
    }

    public void showDevelopmentMessage() {
        showNormalMessage("Tính năng đang được phát triển");
    }
}
