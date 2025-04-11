package com.moneylover.ui.main.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.ActivityAppBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.app.account.AccountFragment;
import com.moneylover.ui.main.app.budget.BudgetFragment;
import com.moneylover.ui.main.app.overview.OverviewFragment;
import com.moneylover.ui.main.app.transactionLog.TransactionLogFragment;
import com.moneylover.utils.NavigationUtils;

import java.util.Stack;

public class AppActivity extends BaseActivity<ActivityAppBinding, AppViewModel> {

    private final Stack<String> fragmentTagHistory = new Stack<>();

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
        super.onCreate(savedInstanceState);

        viewBinding.bottomNavigationView.setBackground(null);
        viewBinding.bottomNavigationView.setOnApplyWindowInsetsListener(null);
        viewBinding.bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        String initialTag = "OverviewFragment";
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_frame_layout, new OverviewFragment(), initialTag)
                .commit();
        fragmentTagHistory.push(initialTag);
        viewBinding.bottomNavigationView.setSelectedItemId(R.id.home);

        viewBinding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            String targetTag = getTagFromMenuId(id);
            if (fragmentTagHistory.peek().equals(targetTag)) return true;

            final View iconView = viewBinding.bottomNavigationView.findViewById(id);
            if (iconView != null) {
                Animator animator = AnimatorInflater.loadAnimator(this, R.animator.icon_click_animator);
                animator.setTarget(iconView);
                animator.start();
            }

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.app_frame_layout);
            int currentIndex = getIndexFromFragment(currentFragment);
            int targetIndex = getIndexFromMenuId(id);
            boolean leftToRight = targetIndex < currentIndex;

            Fragment targetFragment = getFragmentByTag(targetTag);
            if (targetFragment != null) {
                pushFragment(targetTag);
                try {
                    NavigationUtils.navigateWithSlide(this, R.id.app_frame_layout, targetFragment.getClass(), targetTag, leftToRight);
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }

            return false;
        });

    }

    @Override
    public void onBackPressed() {

        if (fragmentTagHistory.size() <= 1 && fragmentTagHistory.peek().equals("OverviewFragment")) {
            finishAffinity(); // Thoát ứng dụng
            return;
        }

        if (fragmentTagHistory.size() == 2 && fragmentTagHistory.peek().equals("AccountFragment")) {
            finishAffinity();
            return;
        }

        String currentTag = fragmentTagHistory.pop();
        String previousTag = fragmentTagHistory.peek();

        int currentIndex = getIndexFromTag(currentTag);
        int previousIndex = getIndexFromTag(previousTag);
        boolean leftToRight = previousIndex < currentIndex;

        Fragment targetFragment = getFragmentByTag(previousTag);
        if (targetFragment != null) {
            try {
                NavigationUtils.navigateWithSlide(this, R.id.app_frame_layout, targetFragment.getClass(), previousTag, leftToRight);
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
            updateBottomNavSelection(previousTag);
        } else {
            super.onBackPressed();
        }
    }

    private void pushFragment(String tag) {
        if (fragmentTagHistory.empty() || !fragmentTagHistory.peek().equals(tag)) {
            fragmentTagHistory.push(tag);
        }
    }

    private void updateBottomNavSelection(String tag) {
        switch (tag) {
            case "OverviewFragment":
                viewBinding.bottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case "TransactionLogFragment":
                viewBinding.bottomNavigationView.setSelectedItemId(R.id.wallet);
                break;
            case "BudgetFragment":
                viewBinding.bottomNavigationView.setSelectedItemId(R.id.budget);
                break;
            case "AccountFragment":
                viewBinding.bottomNavigationView.setSelectedItemId(R.id.account);
                break;
        }
    }

    private Fragment getFragmentByTag(String tag) {
        switch (tag) {
            case "OverviewFragment":
                return new OverviewFragment();
            case "TransactionLogFragment":
                return new TransactionLogFragment();
            case "BudgetFragment":
                return new BudgetFragment();
            case "AccountFragment":
                return new AccountFragment();
            default:
                return null;
        }
    }

    private int getIndexFromFragment(Fragment fragment) {
        if (fragment instanceof OverviewFragment) return 0;
        if (fragment instanceof TransactionLogFragment) return 1;
        if (fragment instanceof BudgetFragment) return 2;
        if (fragment instanceof AccountFragment) return 3;
        return -1;
    }

    private int getIndexFromMenuId(int id) {
        if (id == R.id.home) {
            return 0;
        } else if (id == R.id.wallet) {
            return 1;
        } else if (id == R.id.budget) {
            return 2;
        } else if (id == R.id.account) {
            return 3;
        } else {
            return -1;
        }
    }

    private int getIndexFromTag(String tag) {
        switch (tag) {
            case "OverviewFragment":
                return 0;
            case "TransactionLogFragment":
                return 1;
            case "BudgetFragment":
                return 2;
            case "AccountFragment":
                return 3;
            default:
                return -1;
        }
    }

    private String getTagFromMenuId(int id) {
        if (id == R.id.home) {
            return "OverviewFragment";
        } else if (id == R.id.wallet) {
            return "TransactionLogFragment";
        } else if (id == R.id.budget) {
            return "BudgetFragment";
        } else if (id == R.id.account) {
            return "AccountFragment";
        } else {
            return "OverviewFragment";
        }
    }
}
