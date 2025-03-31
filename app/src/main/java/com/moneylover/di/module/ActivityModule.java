package com.moneylover.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.moneylover.MVVMApplication;
import com.moneylover.ViewModelProviderFactory;
import com.moneylover.data.Repository;
import com.moneylover.di.scope.ActivityScope;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.auth.AuthViewModel;
import com.moneylover.utils.GetInfo;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private BaseActivity<?, ?> activity;

    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }

    @Named("access_token")
    @Provides
    @ActivityScope
    String provideToken(Repository repository){
        return repository.getToken();
    }

    @Named("device_id")
    @Provides
    @ActivityScope
    String provideDeviceId( Context applicationContext){
        return GetInfo.getAll(applicationContext);
    }

    @Provides
    @ActivityScope
    AuthViewModel provideAuthViewModel(Repository repository, Context application) {
        Supplier<AuthViewModel> supplier = () -> new AuthViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<AuthViewModel> factory = new ViewModelProviderFactory<>(AuthViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AuthViewModel.class);
    }

}
