package com.moneylover.ui.main.app.transactionHistory;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.TransactionHistory;
import com.moneylover.data.model.TransactionHistoryWallet;
import com.moneylover.data.model.api.response.BillResponse;
import com.moneylover.data.model.api.response.BillStatisticsResponse;
import com.moneylover.databinding.FragmentTransactionHistoryListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.adapter.RefreshableFragment;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.ui.main.app.overview.LatestTransactionAdapter;
import com.moneylover.ui.main.app.transactionHistory.adapter.TransactionHistoryListAdapter;
import com.moneylover.ui.main.app.transactionHistory.viewReport.ViewReportActivity;
import com.moneylover.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class TransactionHistoryListFragment extends BaseFragment<FragmentTransactionHistoryListBinding, TransactionHistoryListViewModel>
        implements RefreshableFragment {

    LatestTransactionAdapter adapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction_history_list;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        setupLatestTransaction();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onRefresh() {
        // This method is called when the fragment needs to be refreshed
        Timber.tag("TransactionHistoryListFragment").d("onRefresh called");

        // Clear current data if needed
        if (adapter != null) {
            adapter.clearData();
        }

        // Reload all data
        loadTransactionData();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh data when fragment becomes visible again
        loadTransactionData();
    }

    private void setupLatestTransaction() {
        // Initialize adapter with empty data first
        adapter = new LatestTransactionAdapter(getContext(), new ArrayList<>(), new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click
            }

            @Override
            public void onItemDelete(int position) {
                // Handle item delete
            }
        });

        binding.rcvTransactionHistory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvTransactionHistory.setAdapter(adapter);

        // Load data
        loadTransactionData();
    }

    private void loadTransactionData() {
        // Show loading indicator if needed

        // Load bill list
        viewModel.doGetBillList(new MainCallback<List<BillResponse>>() {
            @Override
            public void doError(Throwable error) {
                Timber.tag("TransactionHistoryListFragment").e(error, "Error fetching latest transactions");
                // Hide loading indicator if needed
            }

            @Override
            public void doSuccess() {
                // Method implementation required by interface
            }

            @Override
            public void doSuccess(List<BillResponse> billResponses) {
                if (adapter != null) {
                    adapter.updateData(billResponses);
                }
                // Hide loading indicator if needed
            }

            @Override
            public void doFail() {
                // Hide loading indicator if needed
            }
        });

        // Load income/expense statistics
        viewModel.doGetIncomeOutcome(new MainCallback<BillStatisticsResponse>() {
            @Override
            public void doError(Throwable error) {
                Timber.tag("TransactionHistoryListFragment").e(error, "Error fetching statistics");
            }

            @Override
            public void doSuccess() {
                // Method implementation required by interface
            }

            @Override
            public void doSuccess(BillStatisticsResponse billStatisticsResponse) {
                binding.tvIncome.setText(NumberUtils.formatNumberWithComma(billStatisticsResponse.getTotalIncome()));
                binding.tvExpense.setText(NumberUtils.formatNumberWithComma(billStatisticsResponse.getTotalExpense()));

                // Calculate balance (income - expense)
                BigDecimal balance = billStatisticsResponse.getTotalIncome().subtract(billStatisticsResponse.getTotalExpense());
                binding.tvBalanceTotal.setText(NumberUtils.formatNumberWithComma(balance));
            }

            @Override
            public void doFail() {
                // Handle failure
            }
        });
    }

    public void onViewReportClick() {
        Intent intent = new Intent(getActivity(), ViewReportActivity.class);
        startActivity(intent);
    }
}