package com.moneylover.ui.main.app.transactionHistory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.moneylover.R;
import com.moneylover.BR;
import com.moneylover.databinding.ActivityExpenditureBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.custom.timerangebottomsheet.TimeRangeBottomSheet;
import com.moneylover.ui.main.app.transactionHistory.adapter.ViewReportAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExpenditureActivity extends BaseActivity<ActivityExpenditureBinding, ExpenditureViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_expenditure;
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
    }

    public void setupTabLayout() {
        List<String> months = generateMonthList();
        TabLayout tabLayout = viewBinding.tabLayout;
        tabLayout.removeAllTabs();

        List<Class<? extends Fragment>> fragmentClasses = new ArrayList<>();
        for (int i = 0; i < months.size(); i++) {
            if (i % 3 == 0) {
                fragmentClasses.add(NoDataFragment.class);
            } else {
                fragmentClasses.add(ExpenditureDetailFragment.class);
            }
        }

        ViewReportAdapter adapter = new ViewReportAdapter(this, fragmentClasses);
        viewBinding.viewPager.setAdapter(adapter);
        viewBinding.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewBinding.viewPager.setOffscreenPageLimit(1);

        new TabLayoutMediator(tabLayout, viewBinding.viewPager, (tab, position) -> {
            View customView = LayoutInflater.from(this).inflate(R.layout.item_tab_month, null);
            TextView tabText = customView.findViewById(R.id.tabText);
            tabText.setText(months.get(position));
            tab.setCustomView(customView);
        }).attach();

        int defaultTabIndex = 6;
        viewBinding.viewPager.setCurrentItem(defaultTabIndex, false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                scrollToCenter(tabLayout, tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabLayout.post(() -> {
            TabLayout.Tab defaultTab = tabLayout.getTabAt(defaultTabIndex);
            if (defaultTab != null) {
                scrollToCenter(tabLayout, defaultTab);
            }
        });
    }

    private void scrollToCenter(TabLayout tabLayout, TabLayout.Tab tab) {
        View tabView = tab.view;
        if (tabView != null) {
            int scrollX = tabView.getLeft() + tabView.getWidth() / 2 - tabLayout.getWidth() / 2;
            tabLayout.post(() -> tabLayout.smoothScrollTo(scrollX, 0));
        }
    }

    private List<String> generateMonthList() {
        List<String> months = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendar.add(Calendar.MONTH, -6);

        for (int i = 0; i < 13; i++) {
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            months.add(String.format(Locale.getDefault(), "%02d/%d", month, year));
            calendar.add(Calendar.MONTH, 1);
        }

        months.add("THÁNG TRƯỚC");

        return months;
    }

    int selectedIndex = 2;

    public void onCalendarClick() {
        new TimeRangeBottomSheet(selectedIndex, (position, label) -> {
            viewModel.showNormalMessage(label + " " + position);
            selectedIndex = position;
        }).show(getSupportFragmentManager(), "TimeRange");
    }
}