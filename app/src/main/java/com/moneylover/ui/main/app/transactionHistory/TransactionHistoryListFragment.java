package com.moneylover.ui.main.app.transactionHistory;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.TransactionHistory;
import com.moneylover.data.model.TransactionHistoryWallet;
import com.moneylover.databinding.FragmentTransactionHistoryListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.app.transactionHistory.adapter.TransactionHistoryListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryListFragment extends BaseFragment<FragmentTransactionHistoryListBinding, TransactionHistoryListViewModel> {


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
        setupTransactionHistoryList();
        onViewReportClick(); // Bỏ dòng này
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setupTransactionHistoryList() {
        List<TransactionHistory> arrayList = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            List<TransactionHistoryWallet> wallets = new ArrayList<>();
            wallets.add(new TransactionHistoryWallet("14/04/2025", "500,000", true));
            wallets.add(new TransactionHistoryWallet("15/04/2025", "200,000", false));
            wallets.add(new TransactionHistoryWallet("16/04/2025", "95,000", false));
            TransactionHistoryWallet.sortByDateDescending(wallets);
            arrayList.add(new TransactionHistory("Nhóm " + (i + 1), wallets.size() + " giao dịch", wallets));
        }

        TransactionHistoryListAdapter adapter = new TransactionHistoryListAdapter(getContext(), arrayList, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {}
            @Override
            public void onItemDelete(int position) {}
        });

        if (binding.rcvTransactionHistory.getLayoutManager() == null) {
            binding.rcvTransactionHistory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        }
        binding.rcvTransactionHistory.setAdapter(adapter);
    }

    public void onViewReportClick() {
        Intent intent = new Intent(getActivity(), ViewReportActivity.class);
        startActivity(intent);
    }

}