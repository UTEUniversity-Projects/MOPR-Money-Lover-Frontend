package com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.Expenditure;
import com.moneylover.data.model.ExpenditureTransaction;
import com.moneylover.databinding.ActivityExpenditureTransactionBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.adapter.ExpenditureTransactionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpenditureTransactionActivity extends BaseActivity<ActivityExpenditureTransactionBinding, ExpenditureTransactionViewModel> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_expenditure_transaction;
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
        setupExpenditureTransaction();
    }

    public void setupExpenditureTransaction() {
        List<ExpenditureTransaction> expenditureTransactions = new ArrayList<>();
        List<Expenditure> expenditures = new ArrayList<>();

        expenditures.add(new Expenditure(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        expenditures.add(new Expenditure(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        expenditures.add(new Expenditure(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        expenditures.add(new Expenditure(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        expenditures.add(new Expenditure(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));

        expenditureTransactions.add(new ExpenditureTransaction("24", "Thứ Năm/tháng 4 2025", -50000000, expenditures));
        expenditureTransactions.add(new ExpenditureTransaction("23", "Thứ Tư/tháng 4 2025", -50000000, expenditures));

        ExpenditureTransactionAdapter adapter = new ExpenditureTransactionAdapter(this, expenditureTransactions, new OnItemClickListener() {
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