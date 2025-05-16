package com.moneylover.ui.main.app.transactionHistory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.ActivityTransactionHistoryDetailBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TransactionHistoryDetailActivity extends BaseActivity<ActivityTransactionHistoryDetailBinding, TransactionHistoryDetailViewModel> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_transaction_history_detail;
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onDateClick() {
        Calendar current = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(year, month, dayOfMonth);

                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
                    String formattedDate = sdf.format(selected.getTime());

                    viewBinding.tvDate.setText(formattedDate);
                },
                current.get(Calendar.YEAR),
                current.get(Calendar.MONTH),
                current.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    public void onEditClick()  {
        Intent intent = new Intent(this, TransactionHistoryEditActivity.class);
        startActivity(intent);
    }

}