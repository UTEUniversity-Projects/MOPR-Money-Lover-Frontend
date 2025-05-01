package com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.NetIncome;
import com.moneylover.data.model.NetIncomeTransaction;
import com.moneylover.databinding.ActivityNetIncomeTransactionBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.adapter.NetIncomeTransactionAdapter;

import java.util.ArrayList;
import java.util.List;

public class NetIncomeTransactionActivity extends BaseActivity<ActivityNetIncomeTransactionBinding, NetIncomeTransactionViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_net_income_transaction;
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
        setupNetIncomeTransaction();
    }

    public void setupNetIncomeTransaction() {
        List<NetIncomeTransaction> netIncomeTransactions = new ArrayList<>();
        List<NetIncome> netIncomes = new ArrayList<>();

        netIncomes.add(new NetIncome(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        netIncomes.add(new NetIncome(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        netIncomes.add(new NetIncome(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        netIncomes.add(new NetIncome(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        netIncomes.add(new NetIncome(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));

        netIncomeTransactions.add(new NetIncomeTransaction("24", "Thứ Năm/tháng 4 2025", -50000000, netIncomes));
        netIncomeTransactions.add(new NetIncomeTransaction("23", "Thứ Tư/tháng 4 2025", -50000000, netIncomes));

        NetIncomeTransactionAdapter adapter = new NetIncomeTransactionAdapter(this, netIncomeTransactions, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        viewBinding.rcvNetIncomeTransaction.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewBinding.rcvNetIncomeTransaction.setAdapter(adapter);
    }
}