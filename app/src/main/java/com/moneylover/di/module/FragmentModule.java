package com.moneylover.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import com.moneylover.MVVMApplication;
import com.moneylover.ViewModelProviderFactory;
import com.moneylover.data.Repository;
import com.moneylover.di.scope.FragmentScope;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.app.CategoryListViewModel;
import com.moneylover.ui.main.app.EventListViewModel;
import com.moneylover.ui.main.app.account.AccountViewModel;
import com.moneylover.ui.main.app.budget.BudgetListViewModel;
import com.moneylover.ui.main.app.budget.BudgetViewModel;
import com.moneylover.ui.main.app.overview.OverviewViewModel;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryListViewModel;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.ViewReportListViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureDetailViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureTransactionByGroupListViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupListViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupTypeOptionListViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeDetailViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeTransactionByGroupListViewModel;
import com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.NetIncomeDetailViewModel;
import com.moneylover.ui.main.auth.ForgotPasswordOtpVerificationViewModel;
import com.moneylover.ui.main.auth.ForgotPasswordViewModel;
import com.moneylover.ui.main.auth.LoginViewModel;
import com.moneylover.ui.main.auth.RegisterOtpVerificationViewModel;
import com.moneylover.ui.main.auth.RegisterViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private final BaseFragment<?, ?> fragment;

    public FragmentModule(BaseFragment<?, ?> fragment) {
        this.fragment = fragment;
    }

    @Named("access_token")
    @Provides
    @FragmentScope
    String provideToken(Repository repository) {
        return repository.getToken();
    }

    @Provides
    @FragmentScope
    LoginViewModel provideLoginViewModel(Repository repository, Context application) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(LoginViewModel.class);
    }

    @Provides
    @FragmentScope
    RegisterViewModel provideRegisterViewModel(Repository repository, Context application) {
        Supplier<RegisterViewModel> supplier = () -> new RegisterViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<RegisterViewModel> factory = new ViewModelProviderFactory<>(RegisterViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(RegisterViewModel.class);
    }

    @Provides
    @FragmentScope
    ForgotPasswordViewModel provideForgotPasswordViewModel(Repository repository, Context application) {
        Supplier<ForgotPasswordViewModel> supplier = () -> new ForgotPasswordViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ForgotPasswordViewModel> factory = new ViewModelProviderFactory<>(ForgotPasswordViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ForgotPasswordViewModel.class);
    }

    @Provides
    @FragmentScope
    RegisterOtpVerificationViewModel provideRegisterOtpVerificationViewModel(Repository repository, Context application) {
        Supplier<RegisterOtpVerificationViewModel> supplier = () -> new RegisterOtpVerificationViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<RegisterOtpVerificationViewModel> factory = new ViewModelProviderFactory<>(RegisterOtpVerificationViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(RegisterOtpVerificationViewModel.class);
    }

    @Provides
    @FragmentScope
    ForgotPasswordOtpVerificationViewModel provideForgotPasswordOtpVerificationViewModel(Repository repository, Context application) {
        Supplier<ForgotPasswordOtpVerificationViewModel> supplier = () -> new ForgotPasswordOtpVerificationViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ForgotPasswordOtpVerificationViewModel> factory = new ViewModelProviderFactory<>(ForgotPasswordOtpVerificationViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ForgotPasswordOtpVerificationViewModel.class);
    }

    @Provides
    @FragmentScope
    OverviewViewModel provideOverviewViewModel(Repository repository, Context application) {
        Supplier<OverviewViewModel> supplier = () -> new OverviewViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<OverviewViewModel> factory = new ViewModelProviderFactory<>(OverviewViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(OverviewViewModel.class);
    }

    @Provides
    @FragmentScope
    AccountViewModel provideAccountViewModel(Repository repository, Context application) {
        Supplier<AccountViewModel> supplier = () -> new AccountViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<AccountViewModel> factory = new ViewModelProviderFactory<>(AccountViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AccountViewModel.class);
    }

    @Provides
    @FragmentScope
    TransactionHistoryViewModel provideTransactionHistoryViewModel(Repository repository, Context application) {
        Supplier<TransactionHistoryViewModel> supplier = () -> new TransactionHistoryViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<TransactionHistoryViewModel> factory = new ViewModelProviderFactory<>(TransactionHistoryViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TransactionHistoryViewModel.class);
    }

    @Provides
    @FragmentScope
    TransactionHistoryListViewModel provideTransactionHistoryListViewModel(Repository repository, Context application) {
        Supplier<TransactionHistoryListViewModel> supplier = () -> new TransactionHistoryListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<TransactionHistoryListViewModel> factory = new ViewModelProviderFactory<>(TransactionHistoryListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TransactionHistoryListViewModel.class);
    }

    @Provides
    @FragmentScope
    ViewReportListViewModel provideViewReportListViewModel(Repository repository, Context application) {
        Supplier<ViewReportListViewModel> supplier = () -> new ViewReportListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ViewReportListViewModel> factory = new ViewModelProviderFactory<>(ViewReportListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ViewReportListViewModel.class);
    }

    @Provides
    @FragmentScope
    NetIncomeDetailViewModel provideNetIncomeDetailViewModel(Repository repository, Context application) {
        Supplier<NetIncomeDetailViewModel> supplier = () -> new NetIncomeDetailViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<NetIncomeDetailViewModel> factory = new ViewModelProviderFactory<>(NetIncomeDetailViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(NetIncomeDetailViewModel.class);
    }

    @Provides
    @FragmentScope
    ExpenditureDetailViewModel provideExpenditureDetailViewModel(Repository repository, Context application) {
        Supplier<ExpenditureDetailViewModel> supplier = () -> new ExpenditureDetailViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ExpenditureDetailViewModel> factory = new ViewModelProviderFactory<>(ExpenditureDetailViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ExpenditureDetailViewModel.class);
    }

    @Provides
    @FragmentScope
    ViewReportByGroupTypeOptionListViewModel provideViewReportByGroupTypeOptionListViewModel(Repository repository, Context application) {
        Supplier<ViewReportByGroupTypeOptionListViewModel> supplier = () -> new ViewReportByGroupTypeOptionListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ViewReportByGroupTypeOptionListViewModel> factory = new ViewModelProviderFactory<>(ViewReportByGroupTypeOptionListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ViewReportByGroupTypeOptionListViewModel.class);
    }

    @Provides
    @FragmentScope
    ExpenditureTransactionByGroupListViewModel provideExpenditureByGroupListViewModel(Repository repository, Context application) {
        Supplier<ExpenditureTransactionByGroupListViewModel> supplier = () -> new ExpenditureTransactionByGroupListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ExpenditureTransactionByGroupListViewModel> factory = new ViewModelProviderFactory<>(ExpenditureTransactionByGroupListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ExpenditureTransactionByGroupListViewModel.class);
    }


    @Provides
    @FragmentScope
    IncomeDetailViewModel provideIncomeDetailViewModel(Repository repository, Context application) {
        Supplier<IncomeDetailViewModel> supplier = () -> new IncomeDetailViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<IncomeDetailViewModel> factory = new ViewModelProviderFactory<>(IncomeDetailViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(IncomeDetailViewModel.class);
    }

    @Provides
    @FragmentScope
    IncomeTransactionByGroupListViewModel provideIncomeByGroupListViewModel(Repository repository, Context application) {
        Supplier<IncomeTransactionByGroupListViewModel> supplier = () -> new IncomeTransactionByGroupListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<IncomeTransactionByGroupListViewModel> factory = new ViewModelProviderFactory<>(IncomeTransactionByGroupListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(IncomeTransactionByGroupListViewModel.class);
    }

    @Provides
    @FragmentScope
    ViewReportByGroupListViewModel provideViewReportByGroupListViewModel(Repository repository, Context application) {
        Supplier<ViewReportByGroupListViewModel> supplier = () -> new ViewReportByGroupListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<ViewReportByGroupListViewModel> factory = new ViewModelProviderFactory<>(ViewReportByGroupListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ViewReportByGroupListViewModel.class);
    }

    @Provides
    @FragmentScope
    CategoryListViewModel provideCategoryListViewModel(Repository repository, Context application) {
        Supplier<CategoryListViewModel> supplier = () -> new CategoryListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<CategoryListViewModel> factory = new ViewModelProviderFactory<>(CategoryListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(CategoryListViewModel.class);
    }

    @Provides
    @FragmentScope
    BudgetViewModel provideBudgetViewModel(Repository repository, Context application) {
        Supplier<BudgetViewModel> supplier = () -> new BudgetViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<BudgetViewModel> factory = new ViewModelProviderFactory<>(BudgetViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(BudgetViewModel.class);
    }

    @Provides
    @FragmentScope
    BudgetListViewModel provideBudgetListViewModel(Repository repository, Context application) {
        Supplier<BudgetListViewModel> supplier = () -> new BudgetListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<BudgetListViewModel> factory = new ViewModelProviderFactory<>(BudgetListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(BudgetListViewModel.class);
    }

    @Provides
    @FragmentScope
    EventListViewModel provideEventListViewModel(Repository repository, Context application) {
        Supplier<EventListViewModel> supplier = () -> new EventListViewModel(repository, (MVVMApplication) application);
        ViewModelProviderFactory<EventListViewModel> factory = new ViewModelProviderFactory<>(EventListViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(EventListViewModel.class);
    }

}
