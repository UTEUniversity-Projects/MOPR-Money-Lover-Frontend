package com.moneylover.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.moneylover.MVVMApplication;
import com.moneylover.ViewModelProviderFactory;
import com.moneylover.data.Repository;
import com.moneylover.di.scope.ActivityScope;
import com.moneylover.ui.MainViewModel;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.app.AddEventViewModel;
import com.moneylover.ui.main.app.AddTransactionNoteViewModel;
import com.moneylover.ui.main.app.AddTransactionViewModel;
import com.moneylover.ui.main.app.AppViewModel;
import com.moneylover.ui.main.app.CategoryViewModel;
import com.moneylover.ui.main.app.CreateFirstWalletViewModel;
import com.moneylover.ui.main.app.CurrencyViewModel;
import com.moneylover.ui.main.app.EventViewModel;
import com.moneylover.ui.main.app.TagViewModel;
import com.moneylover.ui.main.app.WalletIconOptionViewModel;
import com.moneylover.ui.main.app.WalletOptionViewModel;
import com.moneylover.ui.main.app.overview.mywallet.AddWalletViewModel;
import com.moneylover.ui.main.app.overview.mywallet.MyWalletEditListViewModel;
import com.moneylover.ui.main.app.overview.mywallet.MyWalletEditViewModel;
import com.moneylover.ui.main.app.overview.mywallet.MyWalletViewModel;
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
    CreateFirstWalletViewModel provideCreateFirstWalletViewModel(Repository repository, Context application) {
        Supplier<CreateFirstWalletViewModel> supplier = () -> new CreateFirstWalletViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<CreateFirstWalletViewModel> factory = new ViewModelProviderFactory<>(CreateFirstWalletViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(CreateFirstWalletViewModel.class);
    }

    @Provides
    @ActivityScope
    CurrencyViewModel provideCurrencyViewModel(Repository repository, Context application) {
        Supplier<CurrencyViewModel> supplier = () -> new CurrencyViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<CurrencyViewModel> factory = new ViewModelProviderFactory<>(CurrencyViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(CurrencyViewModel.class);
    }

    @Provides
    @ActivityScope
    WalletIconOptionViewModel provideWalletIconOptionViewModel(Repository repository, Context application) {
        Supplier<WalletIconOptionViewModel> supplier = () -> new WalletIconOptionViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<WalletIconOptionViewModel> factory = new ViewModelProviderFactory<>(WalletIconOptionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(WalletIconOptionViewModel.class);
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

    @Provides
    @ActivityScope
    MyWalletViewModel provideMyWalletViewModel(Repository repository, Context application) {
        Supplier<MyWalletViewModel> supplier = () -> new MyWalletViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<MyWalletViewModel> factory = new ViewModelProviderFactory<>(MyWalletViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MyWalletViewModel.class);
    }

    @Provides
    @ActivityScope
    MyWalletEditListViewModel provideMyWalletEditListViewModel(Repository repository, Context application) {
        Supplier<MyWalletEditListViewModel> supplier = () -> new MyWalletEditListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<MyWalletEditListViewModel> factory = new ViewModelProviderFactory<>(MyWalletEditListViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MyWalletEditListViewModel.class);
    }

    @Provides
    @ActivityScope
    AddWalletViewModel provideAddWalletViewModel(Repository repository, Context application) {
        Supplier<AddWalletViewModel> supplier = () -> new AddWalletViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AddWalletViewModel> factory = new ViewModelProviderFactory<>(AddWalletViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AddWalletViewModel.class);
    }

    @Provides
    @ActivityScope
    MyWalletEditViewModel provideMyWalletEditViewModel(Repository repository, Context application) {
        Supplier<MyWalletEditViewModel> supplier = () -> new MyWalletEditViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<MyWalletEditViewModel> factory = new ViewModelProviderFactory<>(MyWalletEditViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MyWalletEditViewModel.class);
    }

    @Provides
    @ActivityScope
    AddTransactionViewModel provideAddTransactionViewModel(Repository repository, Context application) {
        Supplier<AddTransactionViewModel> supplier = () -> new AddTransactionViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AddTransactionViewModel> factory = new ViewModelProviderFactory<>(AddTransactionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AddTransactionViewModel.class);
    }

    @Provides
    @ActivityScope
    WalletOptionViewModel provideWalletOptionViewModel(Repository repository, Context application) {
        Supplier<WalletOptionViewModel> supplier = () -> new WalletOptionViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<WalletOptionViewModel> factory = new ViewModelProviderFactory<>(WalletOptionViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(WalletOptionViewModel.class);
    }

    @Provides
    @ActivityScope
    CategoryViewModel provideCategoryViewModel(Repository repository, Context application) {
        Supplier<CategoryViewModel> supplier = () -> new CategoryViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<CategoryViewModel> factory = new ViewModelProviderFactory<>(CategoryViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(CategoryViewModel.class);
    }

    @Provides
    @ActivityScope
    TagViewModel provideTagViewModel(Repository repository, Context application) {
        Supplier<TagViewModel> supplier = () -> new TagViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<TagViewModel> factory = new ViewModelProviderFactory<>(TagViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(TagViewModel.class);
    }

    @Provides
    @ActivityScope
    AddTransactionNoteViewModel provideAddTransactionNoteViewModel(Repository repository, Context application) {
        Supplier<AddTransactionNoteViewModel> supplier = () -> new AddTransactionNoteViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AddTransactionNoteViewModel> factory = new ViewModelProviderFactory<>(AddTransactionNoteViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AddTransactionNoteViewModel.class);
    }

    @Provides
    @ActivityScope
    EventViewModel provideEventViewModel(Repository repository, Context application) {
        Supplier<EventViewModel> supplier = () -> new EventViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<EventViewModel> factory = new ViewModelProviderFactory<>(EventViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(EventViewModel.class);
    }

    @Provides
    @ActivityScope
    AddEventViewModel provideAddEventViewModel(Repository repository, Context application) {
        Supplier<AddEventViewModel> supplier = () -> new AddEventViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AddEventViewModel> factory = new ViewModelProviderFactory<>(AddEventViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(AddEventViewModel.class);
    }

}
