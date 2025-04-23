package com.moneylover.ui.main.app.transactionHistory;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.ActivityAddNoteBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;

public class AddNoteActivity extends BaseActivity<ActivityAddNoteBinding, AddNoteViewModel> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_add_note;
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
        String existingNote = getIntent().getStringExtra("note");
        if (existingNote != null) {
            viewBinding.etNote.setText(existingNote);
            viewBinding.etNote.setSelection(existingNote.length());
        }
    }

    public void onSaveClick() {
        String note = viewBinding.etNote.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("note", note);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}