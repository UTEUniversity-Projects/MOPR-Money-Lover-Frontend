package com.moneylover.ui.main.auth;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.recaptcha.Recaptcha;
import com.google.android.recaptcha.RecaptchaAction;
import com.google.android.recaptcha.RecaptchaTasksClient;
import com.moneylover.BuildConfig;
import com.moneylover.MVVMApplication;
import com.moneylover.constants.Constants;
import com.moneylover.data.Repository;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.RequestRegisterRequest;
import com.moneylover.data.model.api.response.RequestRegisterResponse;
import com.moneylover.ui.base.fragment.BaseFragmentViewModel;
import com.moneylover.ui.main.MainCallback;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class RegisterViewModel extends BaseFragmentViewModel {

    @Nullable
    private RecaptchaTasksClient recaptchaTasksClient = null;

    @Inject
    public RegisterViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void initializeRecaptchaClient() {
        Recaptcha.fetchTaskClient(application, BuildConfig.SITE_KEY).addOnSuccessListener(task -> {
            recaptchaTasksClient = task;
            executeRecaptchaTask();
        }).addOnFailureListener(e -> {
            Timber.tag("Recaptcha").e("Failed to initialize Recaptcha client: %s", e.getMessage());
        });
    }

    private final MutableLiveData<String> recaptchaToken = new MutableLiveData<>();

    public LiveData<String> getRecaptchaToken() {
        return recaptchaToken;
    }

    public void executeRecaptchaTask() {
        if (recaptchaTasksClient != null) {
            recaptchaTasksClient.executeTask(RecaptchaAction.LOGIN)
                    .addOnSuccessListener(task -> {
                        String token = task.toString();
                            recaptchaToken.postValue(token);

//                        writeToInternalStorage(application, "recaptcha_token.txt", token);
                    })
                    .addOnFailureListener(e -> {
                        Timber.tag("Recaptcha").e("Recaptcha failed: %s", e.getMessage());
                        recaptchaToken.postValue(null);
                    });
        }
    }


    public void writeToInternalStorage(Context context, String filename, String data) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(data.getBytes());
            Timber.d("Dữ liệu đã được ghi vào file: %s", filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRegisterToken(String token) {
        repository.getSharedPreferences().setString(Constants.REGISTER_TOKEN, token);
    }

    public void doRequestRegister(MainCallback<ResponseWrapper<RequestRegisterResponse>> callback, RequestRegisterRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().requestRegister(request)
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

}