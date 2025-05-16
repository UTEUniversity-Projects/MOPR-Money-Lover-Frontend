package com.moneylover.ui.main.app.transactionHistory;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.moneylover.databinding.ActivityTransactionHistoryEditBinding;
import com.moneylover.ui.base.activity.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TransactionHistoryEditActivity extends BaseActivity<ActivityTransactionHistoryEditBinding, TransactionHistoryEditViewModel> {

    private ActivityResultLauncher<Intent> noteLauncher;

    @Override
    public int getLayoutId() {
        return com.moneylover.R.layout.activity_transaction_history_edit;
    }

    @Override
    public int getBindingVariable() {
        return com.moneylover.BR.vm;
    }

    @Override
    public void performDependencyInjection(com.moneylover.di.component.ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

        noteLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                String note = result.getData().getStringExtra("note");
                if (note != null) {
                    viewBinding.tvNote.setText(note);
                }
            }
        });
    }

    public void onNoteClick() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        String currentNote = viewBinding.tvNote.getText().toString();
        intent.putExtra("note", currentNote);
        noteLauncher.launch(intent);
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

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Chọn thời gian", dialog);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Hủy", dialog);

        dialog.show();
    }

}