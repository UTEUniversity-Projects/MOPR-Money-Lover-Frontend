package com.moneylover.di.component;

import com.moneylover.di.module.ActivityModule;
import com.moneylover.di.scope.ActivityScope;
import com.moneylover.ui.MainActivity;
import com.moneylover.ui.main.app.AddEventActivity;
import com.moneylover.ui.main.app.AddTransactionActivity;
import com.moneylover.ui.main.app.AddTransactionNoteActivity;
import com.moneylover.ui.main.app.AppActivity;
import com.moneylover.ui.main.app.CategoryActivity;
import com.moneylover.ui.main.app.CreateFirstWalletActivity;
import com.moneylover.ui.main.app.CurrencyActivity;
import com.moneylover.ui.main.app.EventActivity;
import com.moneylover.ui.main.app.TagActivity;
import com.moneylover.ui.main.app.WalletIconOptionActivity;
import com.moneylover.ui.main.app.WalletOptionActivity;
import com.moneylover.ui.main.app.overview.mywallet.AddWalletActivity;
import com.moneylover.ui.main.app.overview.mywallet.MyWalletActivity;
import com.moneylover.ui.main.app.overview.mywallet.MyWalletEditActivity;
import com.moneylover.ui.main.app.overview.mywallet.MyWalletEditListActivity;
import com.moneylover.ui.main.app.transactionHistory.AddNoteActivity;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailActivity;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryEditActivity;
import com.moneylover.ui.main.app.transactionHistory.WalletActivity;
import com.moneylover.ui.main.app.transactionHistory.WalletEditActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.ViewReportActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureTransactionActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureTransactionByGroupActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupTransactionActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupTypeOptionActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeTransactionActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeTransactionByGroupActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.NetIncomeActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.NetIncomeTransactionActivity;
import com.moneylover.ui.main.auth.AuthActivity;
import com.moneylover.ui.main.onboarding.OnboardingActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(AuthActivity activity);

    void inject(OnboardingActivity activity);

    void inject(AppActivity activity);

    void inject(WalletActivity activity);

    void inject(CreateFirstWalletActivity activity);

    void inject(CurrencyActivity activity);

    void inject(WalletIconOptionActivity activity);

    void inject(TransactionHistoryDetailActivity activity);

    void inject(TransactionHistoryEditActivity activity);

    void inject(AddNoteActivity activity);

    void inject(WalletEditActivity activity);

    void inject(ViewReportActivity activity);

    void inject(ViewReportByGroupTypeOptionActivity activity);

    void inject(NetIncomeActivity activity);

    void inject(NetIncomeTransactionActivity activity);

    void inject(ExpenditureActivity activity);

    void inject(ExpenditureTransactionActivity activity);

    void inject(ExpenditureTransactionByGroupActivity activity);

    void inject(IncomeActivity activity);

    void inject(IncomeTransactionActivity activity);

    void inject(IncomeTransactionByGroupActivity activity);

    void inject(ViewReportByGroupActivity activity);

    void inject(ViewReportByGroupTransactionActivity activity);

    void inject(MyWalletActivity activity);

    void inject(MyWalletEditListActivity activity);

    void inject(AddWalletActivity activity);

    void inject(MyWalletEditActivity activity);

    void inject(AddTransactionActivity activity);

    void inject(WalletOptionActivity activity);

    void inject(CategoryActivity activity);

    void inject(TagActivity activity);

    void inject(AddTransactionNoteActivity activity);

    void inject(EventActivity activity);

    void inject(AddEventActivity activity);

}

