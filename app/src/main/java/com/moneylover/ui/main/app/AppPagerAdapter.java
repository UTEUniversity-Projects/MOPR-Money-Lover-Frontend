package com.moneylover.ui.main.app;

import androidx.annotation.NonNull;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.moneylover.ui.main.app.account.AccountFragment;
import com.moneylover.ui.main.app.budget.BudgetFragment;
import com.moneylover.ui.main.app.overview.OverviewFragment;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryFragment;

public class AppPagerAdapter extends FragmentStateAdapter {
    private final SparseArrayCompat<Fragment> fragmentCache = new SparseArrayCompat<>();

    public AppPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0: fragment = new OverviewFragment(); break;
            case 1: fragment = new TransactionHistoryFragment(); break;
            case 2: fragment = new BudgetFragment(); break;
            case 3: fragment = new AccountFragment(); break;
            default: throw new IllegalStateException("Unexpected position: " + position);
        }
        fragmentCache.put(position, fragment);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public Fragment getFragment(int position) {
        return fragmentCache.get(position);
    }
}