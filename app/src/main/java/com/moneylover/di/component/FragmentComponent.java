package com.moneylover.di.component;

import com.moneylover.di.module.FragmentModule;
import com.moneylover.di.scope.FragmentScope;
import com.moneylover.ui.main.app.account.AccountFragment;
import com.moneylover.ui.main.app.overview.OverviewFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureTransactionByGroupListFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureDetailFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupListFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupTypeOptionListFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeDetailFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeTransactionByGroupListFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.NetIncomeDetailFragment;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryFragment;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryListFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.ViewReportListFragment;
import com.moneylover.ui.main.auth.ForgotPasswordFragment;
import com.moneylover.ui.main.auth.ForgotPasswordOtpVerificationFragment;
import com.moneylover.ui.main.auth.LoginFragment;
import com.moneylover.ui.main.auth.RegisterFragment;
import com.moneylover.ui.main.auth.RegisterOtpVerificationFragment;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(LoginFragment fragment);

    void inject(RegisterFragment fragment);

    void inject(ForgotPasswordFragment fragment);

    void inject(ForgotPasswordOtpVerificationFragment fragment);

    void inject(RegisterOtpVerificationFragment fragment);

    void inject(OverviewFragment fragment);

    void inject(AccountFragment fragment);

    void inject(TransactionHistoryFragment fragment);

    void inject(TransactionHistoryListFragment fragment);

    void inject(ViewReportListFragment fragment);

    void inject(NetIncomeDetailFragment fragment);

    void inject(ExpenditureDetailFragment fragment);

    void inject(IncomeDetailFragment fragment);

    void inject(ViewReportByGroupTypeOptionListFragment fragment);

    void inject(ExpenditureTransactionByGroupListFragment fragment);

    void inject(IncomeTransactionByGroupListFragment fragment);

    void inject(ViewReportByGroupListFragment fragment);

}
