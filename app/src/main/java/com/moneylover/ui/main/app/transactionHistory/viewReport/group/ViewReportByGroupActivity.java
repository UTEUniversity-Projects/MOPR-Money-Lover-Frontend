package com.moneylover.ui.main.app.transactionHistory.viewReport.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.ReportGroupType;
import com.moneylover.databinding.ActivityViewReportByGroupBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.custom.timerangebottomsheet.TimeRangeBottomSheet;
import com.moneylover.ui.main.app.transactionHistory.NoDataFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.adapter.ViewPager2Adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ViewReportByGroupActivity extends BaseActivity<ActivityViewReportByGroupBinding, ViewReportByGroupViewModel> {

    private ReportGroupType selectedGroupType = new ReportGroupType(R.drawable.ic_drink, "Ăn uống", "Tiền mặt", "EXPENDITURE");

    private final ActivityResultLauncher<Intent> walletLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    ReportGroupType groupType = (ReportGroupType) result.getData().getSerializableExtra("selectedGroupType");
                    if (groupType != null) {
                        selectedGroupType = groupType;
                        viewBinding.ivGroupType.setImageResource(groupType.getIcon());
                        viewBinding.tvGroupName.setText(groupType.getName());
                    }
                }
            });

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
        setupCalendar();
        setonReportGroupType();
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
                fragmentClasses.add(ViewReportByGroupListFragment.class);
            }
        }

        ViewPager2Adapter adapter = new ViewPager2Adapter(this, fragmentClasses);
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

    public void setupCalendar() {
        viewBinding.ivCalendar.setOnClickListener(v -> {
            new TimeRangeBottomSheet(selectedIndex, (position, label) -> {
                viewModel.showNormalMessage(label + " " + position);
                selectedIndex = position;
            }).show(getSupportFragmentManager(), "TimeRange");
        });
    }

    public void setonReportGroupType() {
        viewBinding.walletOptions.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewReportByGroupTypeOptionActivity.class);
            intent.putExtra("selectedGroupType", selectedGroupType);
            walletLauncher.launch(intent);
        });
    }
}