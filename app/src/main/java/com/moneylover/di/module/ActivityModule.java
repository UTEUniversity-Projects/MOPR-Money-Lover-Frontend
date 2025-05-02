package com.moneylover.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.moneylover.MVVMApplication;
import com.moneylover.ui.MainViewModel;
import com.moneylover.ViewModelProviderFactory;
import com.moneylover.data.Repository;
import com.moneylover.di.scope.ActivityScope;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.app.AppViewModel;
import com.moneylover.ui.main.app.transactionHistory.AddNoteViewModel;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailViewModel;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryEditViewModel;
import com.moneylover.ui.main.app.transactionHistory.WalletEditViewModel;
import com.moneylover.ui.main.app.transactionHistory.WalletViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.ViewReportViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureTransactionByGroupViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureTransactionViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupTransactionViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupTypeOptionViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeTransactionByGroupViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeTransactionViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.NetIncomeTransactionViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.NetIncomeViewModel;
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
    String provideToken(Repository repository) {
        return repository.getToken();
    }

    @Named("device_id")
    @Provides
    @ActivityScope
    String provideDeviceId(Context applicationContext) {
        return GetInfo.getAll(applicationContext);
    }

    @Provides
    @ActivityScope
    MainViewModel proMainViewModel(Repository repository, Context application) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    @Provides
    @ActivityScope
    AuthViewModel provideAuthViewModel(Repository repository, Context application) {
        Supplier<AuthViewModel> supplier = () -> new AuthViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AuthViewModel> factory = new ViewModelProviderFactory<>(AuthViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AuthViewModel.class);
    }

    @Provides
    @ActivityScope
    OnboardingViewModel provideOnboardingViewModel(Repository repository, Context application) {
        Supplier<OnboardingViewModel> supplier = () -> new OnboardingViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<OnboardingViewModel> factory = new ViewModelProviderFactory<>(OnboardingViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(OnboardingViewModel.class);
    }

    @Provides
    @ActivityScope
    AppViewModel provideAppViewModel(Repository repository, Context application) {
        Supplier<AppViewModel> supplier = () -> new AppViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AppViewModel> factory = new ViewModelProviderFactory<>(AppViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AppViewModel.class);
    }

    @Provides
    @ActivityScope
    WalletViewModel provideWalletViewModel(Repository repository, Context application) {
        Supplier<WalletViewModel> supplier = () -> new WalletViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<WalletViewModel> factory = new ViewModelProviderFactory<>(WalletViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(WalletViewModel.class);
    }

    @Provides
    @ActivityScope
    TransactionHistoryDetailViewModel provideTransactionHistoryDetailViewModel(Repository repository, Context application) {
        Supplier<TransactionHistoryDetailViewModel> supplier = () -> new TransactionHistoryDetailViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<TransactionHistoryDetailViewModel> factory = new ViewModelProviderFactory<>(TransactionHistoryDetailViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(TransactionHistoryDetailViewModel.class);
    }

    @Provides
    @ActivityScope
    TransactionHistoryEditViewModel provideTransactionHistoryEditViewModel(Repository repository, Context application) {
        Supplier<TransactionHistoryEditViewModel> supplier = () -> new TransactionHistoryEditViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<TransactionHistoryEditViewModel> factory = new ViewModelProviderFactory<>(TransactionHistoryEditViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(TransactionHistoryEditViewModel.class);
    }

    @Provides
    @ActivityScope
    AddNoteViewModel provideAddNoteViewModel(Repository repository, Context application) {
        Supplier<AddNoteViewModel> supplier = () -> new AddNoteViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AddNoteViewModel> factory = new ViewModelProviderFactory<>(AddNoteViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AddNoteViewModel.class);
    }

    @Provides
    @ActivityScope
    WalletEditViewModel provideWalletEditViewModel(Repository repository, Context application) {
        Supplier<WalletEditViewModel> supplier = () -> new WalletEditViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<WalletEditViewModel> factory = new ViewModelProviderFactory<>(WalletEditViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(WalletEditViewModel.class);
    }

    @Provides
    @ActivityScope
    ViewReportViewModel provideViewReportViewModel(Repository repository, Context application) {
        Supplier<ViewReportViewModel> supplier = () -> new ViewReportViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ViewReportViewModel> factory = new ViewModelProviderFactory<>(ViewReportViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ViewReportViewModel.class);
    }

    @Provides
    @ActivityScope
    NetIncomeViewModel provideNetIncomeViewModel(Repository repository, Context application) {
        Supplier<NetIncomeViewModel> supplier = () -> new NetIncomeViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<NetIncomeViewModel> factory = new ViewModelProviderFactory<>(NetIncomeViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(NetIncomeViewModel.class);
    }

    @Provides
    @ActivityScope
    ExpenditureViewModel provideExpenditureViewModel(Repository repository, Context application) {
        Supplier<ExpenditureViewModel> supplier = () -> new ExpenditureViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ExpenditureViewModel> factory = new ViewModelProviderFactory<>(ExpenditureViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ExpenditureViewModel.class);
    }

    @Provides
    @ActivityScope
    IncomeViewModel provideIncomeViewModel(Repository repository, Context application) {
        Supplier<IncomeViewModel> supplier = () -> new IncomeViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<IncomeViewModel> factory = new ViewModelProviderFactory<>(IncomeViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(IncomeViewModel.class);
    }

    @Provides
    @ActivityScope
    ViewReportByGroupTypeOptionViewModel provideViewReportByGroupTypeOptionViewModel(Repository repository, Context application) {
        Supplier<ViewReportByGroupTypeOptionViewModel> supplier = () -> new ViewReportByGroupTypeOptionViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ViewReportByGroupTypeOptionViewModel> factory = new ViewModelProviderFactory<>(ViewReportByGroupTypeOptionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ViewReportByGroupTypeOptionViewModel.class);
    }

    @Provides
    @ActivityScope
    NetIncomeTransactionViewModel provideNetIncomeTransactionViewModel(Repository repository, Context application) {
        Supplier<NetIncomeTransactionViewModel> supplier = () -> new NetIncomeTransactionViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<NetIncomeTransactionViewModel> factory = new ViewModelProviderFactory<>(NetIncomeTransactionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(NetIncomeTransactionViewModel.class);
    }

    @Provides
    @ActivityScope
    ExpenditureTransactionViewModel provideExpenditureTransactionViewModel(Repository repository, Context application) {
        Supplier<ExpenditureTransactionViewModel> supplier = () -> new ExpenditureTransactionViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ExpenditureTransactionViewModel> factory = new ViewModelProviderFactory<>(ExpenditureTransactionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ExpenditureTransactionViewModel.class);
    }

    @Provides
    @ActivityScope
    ExpenditureTransactionByGroupViewModel provideExpenditureTransactionByGroupViewModel(Repository repository, Context application) {
        Supplier<ExpenditureTransactionByGroupViewModel> supplier = () -> new ExpenditureTransactionByGroupViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ExpenditureTransactionByGroupViewModel> factory = new ViewModelProviderFactory<>(ExpenditureTransactionByGroupViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ExpenditureTransactionByGroupViewModel.class);
    }

    @Provides
    @ActivityScope
    IncomeTransactionViewModel provideIncomeTransactionViewModel(Repository repository, Context application) {
        Supplier<IncomeTransactionViewModel> supplier = () -> new IncomeTransactionViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<IncomeTransactionViewModel> factory = new ViewModelProviderFactory<>(IncomeTransactionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(IncomeTransactionViewModel.class);
    }

    @Provides
    @ActivityScope
    IncomeTransactionByGroupViewModel provideIncomeTransactionByGroupViewModel(Repository repository, Context application) {
        Supplier<IncomeTransactionByGroupViewModel> supplier = () -> new IncomeTransactionByGroupViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<IncomeTransactionByGroupViewModel> factory = new ViewModelProviderFactory<>(IncomeTransactionByGroupViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(IncomeTransactionByGroupViewModel.class);
    }

    @Provides
    @ActivityScope
    ViewReportByGroupViewModel provideViewReportByGroupViewModel(Repository repository, Context application) {
        Supplier<ViewReportByGroupViewModel> supplier = () -> new ViewReportByGroupViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ViewReportByGroupViewModel> factory = new ViewModelProviderFactory<>(ViewReportByGroupViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ViewReportByGroupViewModel.class);
    }

    @Provides
    @ActivityScope
    ViewReportByGroupTransactionViewModel provideViewReportByGroupTransactionViewModel(Repository repository, Context application) {
        Supplier<ViewReportByGroupTransactionViewModel> supplier = () -> new ViewReportByGroupTransactionViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ViewReportByGroupTransactionViewModel> factory = new ViewModelProviderFactory<>(ViewReportByGroupTransactionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ViewReportByGroupTransactionViewModel.class);
    }

}
