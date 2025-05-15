package com.moneylover.ui.main.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.ActivityAddTransactionNoteBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;

public class AddTransactionNoteActivity extends BaseActivity<ActivityAddTransactionNoteBinding, AddTransactionNoteViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_transaction_note;
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

        String note = getIntent().getStringExtra("note");

        if (note != null) {
            viewBinding.edtNote.setText(note);
        }

        viewBinding.tvSave.setOnClickListener(v -> {
            String noteText = viewBinding.edtNote.getText().toString();
            Intent intent = new Intent();
            intent.putExtra("note", noteText);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

}