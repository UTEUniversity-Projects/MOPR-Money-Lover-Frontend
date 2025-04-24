package com.moneylover.di.component;

import com.moneylover.MainActivity;
import com.moneylover.di.module.ActivityModule;
import com.moneylover.di.scope.ActivityScope;
import com.moneylover.ui.main.app.AppActivity;
import com.moneylover.ui.main.app.transactionHistory.AddNoteActivity;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailActivity;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryEditActivity;
import com.moneylover.ui.main.app.transactionHistory.ViewReportActivity;
import com.moneylover.ui.main.app.transactionHistory.WalletActivity;
import com.moneylover.ui.main.app.transactionHistory.WalletEditActivity;
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

    void inject(TransactionHistoryDetailActivity activity);

    void inject(TransactionHistoryEditActivity activity);

    void inject(AddNoteActivity activity);

    void inject(WalletEditActivity activity);

    void inject(ViewReportActivity activity);
}

