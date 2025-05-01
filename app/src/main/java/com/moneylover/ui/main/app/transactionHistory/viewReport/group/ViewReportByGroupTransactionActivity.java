package com.moneylover.ui.main.app.transactionHistory.viewReport.group;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.ReportGroup;
import com.moneylover.data.model.ReportGroupTransaction;
import com.moneylover.databinding.ActivityViewReportByGroupTransactionBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.adapter.ReportGroupTransactionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewReportByGroupTransactionActivity extends BaseActivity<ActivityViewReportByGroupTransactionBinding, ViewReportByGroupTransactionViewModel> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_view_report_by_group_transaction;
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
        setupReportGroupTransaction();
    }

    public void setupReportGroupTransaction() {
        List<ReportGroupTransaction> reportGroupTransactions = new ArrayList<>();
        List<ReportGroup> reportGroups = new ArrayList<>();

        reportGroups.add(new ReportGroup(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        reportGroups.add(new ReportGroup(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        reportGroups.add(new ReportGroup(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        reportGroups.add(new ReportGroup(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));
        reportGroups.add(new ReportGroup(R.drawable.ic_drink, "Ăn uống", R.drawable.ic_wallet_2, -50000000));

        reportGroupTransactions.add(new ReportGroupTransaction("24", "Thứ Năm/tháng 4 2025", -50000000, reportGroups));
        reportGroupTransactions.add(new ReportGroupTransaction("23", "Thứ Tư/tháng 4 2025", -50000000, reportGroups));

        ReportGroupTransactionAdapter adapter = new ReportGroupTransactionAdapter(this, reportGroupTransactions, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        viewBinding.rcvGroupTransaction.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewBinding.rcvGroupTransaction.setAdapter(adapter);
    }
}