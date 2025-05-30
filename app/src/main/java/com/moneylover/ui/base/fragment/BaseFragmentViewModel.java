package com.moneylover.ui.base.fragment;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moneylover.MVVMApplication;
import com.moneylover.data.Repository;
import com.moneylover.data.model.others.ToastMessage;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import lombok.Setter;

public class BaseFragmentViewModel extends ViewModel {

    protected final Repository repository;
    protected final MVVMApplication application;
    protected CompositeDisposable compositeDisposable;
    protected MutableLiveData<ToastMessage> mErrorMessage = new MutableLiveData<>();
    protected final ObservableBoolean mIsLoading = new ObservableBoolean();

    @Setter
    protected String token;

    public BaseFragmentViewModel(Repository repository, MVVMApplication application) {
        this.repository = repository;
        this.application = application;
        this.compositeDisposable = new CompositeDisposable();
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

    public void showLoading() {
        mIsLoading.set(true);
    }

    public void hideLoading() {
        mIsLoading.set(false);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
