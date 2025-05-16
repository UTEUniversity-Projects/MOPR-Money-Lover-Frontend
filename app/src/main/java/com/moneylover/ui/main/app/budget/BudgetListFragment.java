package com.moneylover.ui.main.app.budget;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.response.BudgetResponse;
import com.moneylover.databinding.FragmentBudgetListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.MainCallback;

import java.util.List;

import timber.log.Timber;

public class BudgetListFragment extends BaseFragment<FragmentBudgetListBinding, BudgetListViewModel> {

    private ActivityResultLauncher<Intent> addBudgetLauncher;
    private BudgetAdapter adapter;

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
//        setupProgressBar();
        setupBudgetList();

        addBudgetLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        setupBudgetList();
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
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

//    private void setupProgressBar() {
//        binding.arcProgress.setMax(20000000); // giá trị tối đa
//        binding.arcProgress.setProgressAnimated(13000000); // giá trị hiện tại
//    }

    public void setupBudgetList() {
        viewModel.doGetBudget(new MainCallback<List<BudgetResponse>>() {
            @Override
            public void doError(Throwable error) {
                Timber.tag("error").d("Error %s", error.getMessage());
            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(List<BudgetResponse> budgetResponses) {
                adapter = new BudgetAdapter(getActivity(), budgetResponses, new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }

                    @Override
                    public void onItemDelete(int position) {

                    }
                });
                binding.rcvBudgetList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                binding.rcvBudgetList.setAdapter(adapter);
            }

            @Override
            public void doFail() {

            }
        });
    }

}