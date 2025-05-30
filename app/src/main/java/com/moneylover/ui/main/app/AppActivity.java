package com.moneylover.ui.main.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.ActivityAppBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.RefreshableFragment;

import java.util.Stack;

public class AppActivity extends BaseActivity<ActivityAppBinding, AppViewModel> {

    private Stack<Integer> fragmentStack = new Stack<>();
    private int currentPage = 0;
    private boolean isBackPressing = false;
    private boolean isBottomNavClick = false;
    private AppPagerAdapter pagerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        setTheme(R.style.Theme_MoneyLover);
        super.onCreate(savedInstanceState);

        viewBinding.bottomNavigationView.setBackground(null);
        viewBinding.bottomNavigationView.setOnApplyWindowInsetsListener(null);
        viewBinding.bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        pagerAdapter = new AppPagerAdapter(this);
        viewBinding.viewPager.setAdapter(pagerAdapter);
        viewBinding.viewPager.setOffscreenPageLimit(1);

        viewBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (currentPage != position) {
                    if (!isBackPressing && !isBottomNavClick) {
                        fragmentStack.push(currentPage);
                    }
                    currentPage = position;
                    viewBinding.bottomNavigationView.setSelectedItemId(getBottomNavItemId(position));
                    isBottomNavClick = false;
                }
            }
        });

        viewBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int position = getPagePositionForMenuId(item.getItemId());

            if (position != currentPage) {
                isBottomNavClick = true;
                fragmentStack.push(currentPage);
                viewBinding.viewPager.setCurrentItem(position, true);
                currentPage = position;
            } else {
                // The same tab was clicked again - refresh the fragment
                refreshCurrentFragment();
            }
            return true;
        });

        // Initial setup
        currentPage = 0;
        viewBinding.bottomNavigationView.setSelectedItemId(R.id.home);
        viewBinding.viewPager.setCurrentItem(currentPage, false);

        viewBinding.fabAddTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTransactionActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_up, R.anim.no_anim);
        });
    }

    /**
     * Refreshes the current fragment if it implements RefreshableFragment
     */
    private void refreshCurrentFragment() {
        if (pagerAdapter != null) {
            Fragment fragment = pagerAdapter.getFragment(currentPage);

            if (fragment instanceof RefreshableFragment) {
                ((RefreshableFragment) fragment).onRefresh();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        if (!fragmentStack.isEmpty()) {
            isBackPressing = true;
            int previousPosition = fragmentStack.pop();
            viewBinding.viewPager.setCurrentItem(previousPosition, true);
            currentPage = previousPosition;
            isBackPressing = false;
            return;
        }

        super.onBackPressed();
    }

    private int getBottomNavItemId(int position) {
        switch (position) {
            case 0: return R.id.home;
            case 1: return R.id.transaction_history;
            case 2: return R.id.budget;
            case 3: return R.id.account;
            default: throw new IllegalStateException("Unexpected position: " + position);
        }
    }

    private int getPagePositionForMenuId(int menuId) {
        if (menuId == R.id.home) {
            return 0;
        } else if (menuId == R.id.transaction_history) {
            return 1;
        } else if (menuId == R.id.budget) {
            return 2;
        } else if (menuId == R.id.account) {
            return 3;
        } else {
            throw new IllegalStateException("Unexpected menuId: " + menuId);
        }
    }
}