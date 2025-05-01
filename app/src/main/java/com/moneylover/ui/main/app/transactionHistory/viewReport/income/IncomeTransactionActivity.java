package com.moneylover.ui.main.app.transactionHistory.viewReport.income;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.Income;
import com.moneylover.data.model.IncomeTransaction;
import com.moneylover.databinding.ActivityIncomeTransactionBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.adapter.IncomeTransactionAdapter;

import java.util.ArrayList;
import java.util.List;

public class IncomeTransactionActivity extends BaseActivity<ActivityIncomeTransactionBinding, IncomeTransactionViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_income_transaction;
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
        setupIncomeTransaction();
    }

    public void setupIncomeTransaction() {
        List<IncomeTransaction> incomeTransactions = new ArrayList<>();
        List<Income> incomes = new ArrayList<>();

        incomes.add(new Income(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        incomes.add(new Income(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        incomes.add(new Income(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        incomes.add(new Income(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        incomes.add(new Income(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));

        incomeTransactions.add(new IncomeTransaction("24", "Thứ Năm/tháng 4 2025", -50000000, incomes));
        incomeTransactions.add(new IncomeTransaction("23", "Thứ Tư/tháng 4 2025", -50000000, incomes));

        IncomeTransactionAdapter adapter = new IncomeTransactionAdapter(this, incomeTransactions, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        viewBinding.rcvExpenditureTransaction.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewBinding.rcvExpenditureTransaction.setAdapter(adapter);
    }

}