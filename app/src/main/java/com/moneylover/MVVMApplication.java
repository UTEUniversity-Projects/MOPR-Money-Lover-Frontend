package com.moneylover;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.moneylover.di.component.AppComponent;
import com.moneylover.di.component.DaggerAppComponent;
import com.moneylover.others.MyTimberDebugTree;
import com.moneylover.utils.DialogUtils;

import es.dmoral.toasty.Toasty;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Getter;
import lombok.Setter;
import timber.log.Timber;

public class MVVMApplication extends Application implements LifecycleObserver {
    @Setter
    private AppCompatActivity currentActivity;

    @Getter
    private AppComponent appComponent;
    private Boolean inBackground;

    @Override
    public void onCreate() {
        super.onCreate();


        if (BuildConfig.DEBUG) {
            Timber.plant(new MyTimberDebugTree());
        }

        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);

        // Init Toasty
        Toasty.Config.getInstance().allowQueue(false).apply();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//        insertMock();
//        startOrderSchedule();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onAppBackgrounded() {
        //App in background
        Timber.d("APP IN BACKGROUND");
        inBackground = true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onAppForegrounded() {
        // App in foreground
        Timber.d("APP IN FOREGROUND");
        inBackground = false;
    }

    public PublishSubject<Integer> showDialogNoInternetAccess() {
        final PublishSubject<Integer> subject = PublishSubject.create();
        currentActivity.runOnUiThread(() -> DialogUtils.dialogConfirm(currentActivity, currentActivity.getResources().getString(R.string.network_error), currentActivity.getResources().getString(R.string.network_error_button_retry), (dialogInterface, i) -> subject.onNext(1), currentActivity.getResources().getString(R.string.network_error_button_exit), (dialogInterface, i) -> System.exit(0)));
        return subject;
    }

}

