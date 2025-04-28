package com.moneylover.ui.main.app.transactionHistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.ActivityViewReportByGroupBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.app.transactionHistory.adapter.ViewReportAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewReportByGroupActivity extends BaseActivity<ActivityViewReportByGroupBinding, ViewReportByGroupViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_report_by_group;
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
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        setupTabLayout();
        setupSearchView();
    }

    public void setupTabLayout() {
        TabLayout tabLayout = viewBinding.tabLayout;
        tabLayout.removeAllTabs();

        List<String> groupTypes = Arrays.asList("Khoản chi", "Khoản thu");
        List<String> typeKeys = Arrays.asList("EXPENDITURE", "INCOME");
        List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();
        fragmentClasses.add(ViewReportByGroupListFragment.class);
        fragmentClasses.add(ViewReportByGroupListFragment.class);

        ViewReportAdapter adapter = new ViewReportAdapter(this, fragmentClasses);
        viewBinding.viewPager.setAdapter(adapter);
        viewBinding.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewBinding.viewPager.setOffscreenPageLimit(1);

        Bundle args = new Bundle();
        args.putString("type", typeKeys.get(0));
        adapter.setFragmentArguments(0, args);

        new TabLayoutMediator(tabLayout, viewBinding.viewPager, (tab, position) -> {
            View customView = LayoutInflater.from(this).inflate(R.layout.item_tab_group_type, tabLayout, false);
            TextView tabText = customView.findViewById(R.id.tabText);
            tabText.setText(groupTypes.get(position));
            tab.setCustomView(customView);
        }).attach();

        viewBinding.viewPager.setCurrentItem(0, false);

        viewBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                Fragment fragment = adapter.getFragment(position);
                if (fragment instanceof ViewReportByGroupListFragment) {
                    String type = typeKeys.get(position);
                    ((ViewReportByGroupListFragment) fragment).setTypeAndReload(type);
                }
            }
        });
    }

    private void setupSearchView() {
        viewBinding.searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewBinding.searchView.post(() -> {
                    removeSearchViewHintIcon();
                });

                viewBinding.tvTitle.setVisibility(View.GONE);
            } else {
                viewBinding.tvTitle.setVisibility(View.VISIBLE);
            }
        });

        viewBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCurrentFragment(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCurrentFragment(newText);
                showCloseButtonAlways();
                return true;
            }
        });
    }

    private void removeSearchViewHintIcon() {
        try {
            View searchEditText = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            if (searchEditText instanceof TextView) {
                ((TextView) searchEditText).setCompoundDrawables(null, null, null, null);
            }

            viewBinding.searchView.setIconifiedByDefault(false);
            viewBinding.searchView.setIconified(false);
            viewBinding.searchView.setSubmitButtonEnabled(false);
            viewBinding.searchView.setQueryHint("Tìm kiếm...");

            View magIcon = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
            if (magIcon != null) {
                magIcon.setVisibility(View.GONE);
            }

            View searchPlate = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_plate);
            if (searchPlate != null) {
//                searchPlate.setBackgroundColor(android.graphics.Color.TRANSPARENT); // Xóa underline nếu muốn
            }

            View closeButton = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
            if (closeButton != null) {
                closeButton.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setupCloseButtonBehavior();
    }

    private void setupCloseButtonBehavior() {
        View closeButton = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> {
                String currentText = viewBinding.searchView.getQuery().toString();
                if (!currentText.isEmpty()) {
                    viewBinding.searchView.setQuery("", false);
                } else {
                    viewBinding.searchView.clearFocus();
                    viewBinding.searchView.setIconified(true);
                    viewBinding.searchView.setIconifiedByDefault(true);

                    viewBinding.tvTitle.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    private void searchCurrentFragment(String query) {
        int position = viewBinding.viewPager.getCurrentItem();
        ViewReportAdapter adapter = (ViewReportAdapter) viewBinding.viewPager.getAdapter();
        if (adapter != null) {
            Fragment fragment = adapter.getFragment(position);
            if (fragment instanceof ViewReportByGroupListFragment) {
                ((ViewReportByGroupListFragment) fragment).search(query);
            }
        }
    }

    private void showCloseButtonAlways() {
        View closeButton = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        if (closeButton != null) {
            closeButton.setVisibility(View.VISIBLE);
        }
    }

}
