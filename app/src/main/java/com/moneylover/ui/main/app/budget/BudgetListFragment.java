package com.moneylover.ui.main.app.budget;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.FragmentBudgetListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;

public class BudgetListFragment extends BaseFragment<FragmentBudgetListBinding, BudgetListViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_budget_list;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        setupProgressBar();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setupProgressBar() {
        binding.arcProgress.setMax(20000000); // giá trị tối đa
        binding.arcProgress.setProgressAnimated(13000000); // giá trị hiện tại

    }

}