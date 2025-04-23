package com.moneylover.ui.main.app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.moneylover.ui.main.app.account.AccountFragment;
import com.moneylover.ui.main.app.budget.BudgetFragment;
import com.moneylover.ui.main.app.overview.OverviewFragment;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryFragment;

public class AppPagerAdapter extends FragmentStateAdapter {
    public AppPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new OverviewFragment();
            case 1: return new TransactionHistoryFragment();
            case 2: return new BudgetFragment();
            case 3: return new AccountFragment();
            default: throw new IllegalStateException("Unexpected position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

