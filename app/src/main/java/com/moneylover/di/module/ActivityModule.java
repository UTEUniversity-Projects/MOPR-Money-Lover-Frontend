package com.moneylover.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.moneylover.MVVMApplication;
import com.moneylover.MainViewModel;
import com.moneylover.ViewModelProviderFactory;
import com.moneylover.data.Repository;
import com.moneylover.di.scope.ActivityScope;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.app.AppViewModel;
import com.moneylover.ui.main.app.transactionHistory.AddNoteViewModel;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailViewModel;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryEditViewModel;
import com.moneylover.ui.main.app.transactionHistory.ViewReportViewModel;
import com.moneylover.ui.main.app.transactionHistory.WalletEditViewModel;
import com.moneylover.ui.main.app.transactionHistory.WalletViewModel;
import com.moneylover.ui.main.auth.AuthViewModel;
import com.moneylover.ui.main.onboarding.OnboardingViewModel;
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
    MainViewModel proMainViewModel(Repository repository, Context application) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Provides
    @ActivityScope
    AuthViewModel provideAuthViewModel(Repository repository, Context application) {
        Supplier<AuthViewModel> supplier = () -> new AuthViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<AuthViewModel> factory = new ViewModelProviderFactory<>(AuthViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AuthViewModel.class);
    }

    @Provides
    @ActivityScope
    OnboardingViewModel provideOnboardingViewModel(Repository repository, Context application) {
        Supplier<OnboardingViewModel> supplier = () -> new OnboardingViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<OnboardingViewModel> factory = new ViewModelProviderFactory<>(OnboardingViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(OnboardingViewModel.class);
    }

    @Provides
    @ActivityScope
    AppViewModel provideAppViewModel(Repository repository, Context application) {
        Supplier<AppViewModel> supplier = () -> new AppViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<AppViewModel> factory = new ViewModelProviderFactory<>(AppViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AppViewModel.class);
    }

    @Provides
    @ActivityScope
    WalletViewModel provideWalletViewModel(Repository repository, Context application) {
        Supplier<WalletViewModel> supplier = () -> new WalletViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<WalletViewModel> factory = new ViewModelProviderFactory<>(WalletViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(WalletViewModel.class);
    }

    @Provides
    @ActivityScope
    TransactionHistoryDetailViewModel provideTransactionHistoryDetailViewModel(Repository repository, Context application) {
        Supplier<TransactionHistoryDetailViewModel> supplier = () -> new TransactionHistoryDetailViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<TransactionHistoryDetailViewModel> factory = new ViewModelProviderFactory<>(TransactionHistoryDetailViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(TransactionHistoryDetailViewModel.class);
    }

    @Provides
    @ActivityScope
    TransactionHistoryEditViewModel provideTransactionHistoryEditViewModel(Repository repository, Context application) {
        Supplier<TransactionHistoryEditViewModel> supplier = () -> new TransactionHistoryEditViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<TransactionHistoryEditViewModel> factory = new ViewModelProviderFactory<>(TransactionHistoryEditViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(TransactionHistoryEditViewModel.class);
    }

    @Provides
    @ActivityScope
    AddNoteViewModel provideAddNoteViewModel(Repository repository, Context application) {
        Supplier<AddNoteViewModel> supplier = () -> new AddNoteViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<AddNoteViewModel> factory = new ViewModelProviderFactory<>(AddNoteViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AddNoteViewModel.class);
    }

    @Provides
    @ActivityScope
    WalletEditViewModel provideWalletEditViewModel(Repository repository, Context application) {
        Supplier<WalletEditViewModel> supplier = () -> new WalletEditViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<WalletEditViewModel> factory = new ViewModelProviderFactory<>(WalletEditViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(WalletEditViewModel.class);
    }

    @Provides
    @ActivityScope
    ViewReportViewModel provideViewReportViewModel(Repository repository, Context application) {
        Supplier<ViewReportViewModel> supplier = () -> new ViewReportViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<ViewReportViewModel> factory = new ViewModelProviderFactory<>(ViewReportViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ViewReportViewModel.class);
    }

}
