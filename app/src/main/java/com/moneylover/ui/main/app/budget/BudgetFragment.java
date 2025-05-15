package com.moneylover.ui.main.app.budget;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.response.BudgetResponse;
import com.moneylover.databinding.FragmentBudgetBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.ui.main.app.transactionHistory.viewReport.adapter.ViewPager2Adapter;

import java.util.Arrays;
import java.util.List;

public class BudgetFragment extends BaseFragment<FragmentBudgetBinding, BudgetViewModel> {

    private ActivityResultLauncher<Intent> addBudgetLauncher;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);

        addBudgetLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String note = data.getStringExtra("note");
                            // Handle the note here
                        }
                    }
                }
        );

        binding.btnCreateBudget.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddBudgetActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                    getActivity(),
                    R.anim.slide_in_up,
                    R.anim.no_anim
            );
            addBudgetLauncher.launch(intent, options);
        });

        setupBudget();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void setupBudget() {
        viewModel.doGetBudgetList(new MainCallback<List<BudgetResponse>>() {
            @Override
            public void doError(Throwable error) {

            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(List<BudgetResponse> budgetResponses) {
                if (budgetResponses == null || budgetResponses.isEmpty()) {
                    binding.llEmptyBudget.setVisibility(View.VISIBLE);
                    binding.llBudget.setVisibility(View.GONE);
                } else {
                    binding.llEmptyBudget.setVisibility(View.GONE);
                    binding.llBudget.setVisibility(View.VISIBLE);

//                    setupTabLayout();
                }
            }

            @Override
            public void doFail() {

            }
        });
    }

    private void setupTabLayout() {
        List<String> tabTitles = Arrays.asList("Tháng này");
        List<Class<? extends Fragment>> fragmentClasses = Arrays.asList(
                BudgetListFragment.class
        );

        ViewPager2Adapter adapter = new ViewPager2Adapter(getActivity(), fragmentClasses);

        binding.viewPagerBudget.setAdapter(adapter);
        binding.viewPagerBudget.setOverScrollMode(View.OVER_SCROLL_NEVER);
        binding.viewPagerBudget.setOffscreenPageLimit(fragmentClasses.size());

        new TabLayoutMediator(binding.tabLayout, binding.viewPagerBudget, (tab, position) -> {
            View customView = LayoutInflater.from(getContext()).inflate(R.layout.item_tab_group_type, binding.tabLayout, false);
            ((TextView) customView.findViewById(R.id.tabText)).setText(tabTitles.get(position));
            tab.setCustomView(customView);
        }).attach();


        binding.viewPagerBudget.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

            }
        });

        binding.viewPagerBudget.setCurrentItem(0, false);
    }
}