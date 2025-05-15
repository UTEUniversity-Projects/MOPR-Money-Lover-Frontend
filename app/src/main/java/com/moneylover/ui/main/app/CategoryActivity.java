package com.moneylover.ui.main.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.response.CategoryResponse;
import com.moneylover.databinding.ActivityCategoryBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.adapter.ViewPager2Adapter;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupTypeOptionListFragment;

import java.util.Arrays;
import java.util.List;

public class CategoryActivity extends BaseActivity<ActivityCategoryBinding, CategoryViewModel> {
    private CategoryResponse selectedCategory;

    @Override
    public int getLayoutId() {
        return R.layout.activity_category;
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

        selectedCategory = (CategoryResponse) getIntent().getSerializableExtra("selected_category");

        setupTabLayout();
        setupSearchView();
    }

    public void setupTabLayout() {
        List<String> tabTitles = Arrays.asList("Khoản chi", "Khoản thu");
        List<String> typeKeys = Arrays.asList("EXPENDITURE", "INCOME");
        List<Class<? extends Fragment>> fragmentClasses = Arrays.asList(
                CategoryListFragment.class,
                CategoryListFragment.class
        );

        ViewPager2Adapter adapter = new ViewPager2Adapter(this, fragmentClasses);

        for (int i = 0; i < typeKeys.size(); i++) {
            Bundle args = new Bundle();
            args.putString("type", typeKeys.get(i));
            args.putSerializable("selected_category", selectedCategory);
            adapter.setFragmentArguments(i, args);
        }

        viewBinding.viewPager.setAdapter(adapter);
        viewBinding.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewBinding.viewPager.setOffscreenPageLimit(fragmentClasses.size());

        new TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager, (tab, position) -> {
            View customView = LayoutInflater.from(this).inflate(R.layout.item_tab_group_type, viewBinding.tabLayout, false);
            ((TextView) customView.findViewById(R.id.tabText)).setText(tabTitles.get(position));
            tab.setCustomView(customView);
        }).attach();


        viewBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

            }
        });

        int defaultTabIndex = (selectedCategory == null || selectedCategory.getIsExpense()) ? 0 : 1;
        viewBinding.viewPager.setCurrentItem(defaultTabIndex, false);
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
        ViewPager2Adapter adapter = (ViewPager2Adapter) viewBinding.viewPager.getAdapter();
        if (adapter != null) {
            Fragment fragment = adapter.getFragment(position);
            if (fragment instanceof ViewReportByGroupTypeOptionListFragment) {
                ((ViewReportByGroupTypeOptionListFragment) fragment).search(query);
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