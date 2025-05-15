package com.moneylover.ui.main.app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.response.CategoryResponse;
import com.moneylover.data.model.api.response.EventResponse;
import com.moneylover.data.model.api.response.TagResponse;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityAddTransactionBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends BaseActivity<ActivityAddTransactionBinding, AddTransactionViewModel> {

    private ActivityResultLauncher<Intent> walletLauncher;
    private ActivityResultLauncher<Intent> categoryLauncher;
    private ActivityResultLauncher<Intent> tagLauncher;
    private ActivityResultLauncher<Intent> noteLauncher;
    private ActivityResultLauncher<Intent> eventLauncher;

    private ArrayList<TagResponse> selectedTags = new ArrayList<>();
    private CategoryResponse selectedCategory;
    private String note;
    private EventResponse selectedEvent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_transaction;
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
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

        viewBinding.btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.no_anim, R.anim.slide_out_down);
        });

        walletLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        WalletResponse selectedWallet = (WalletResponse) result.getData().getSerializableExtra("selected_wallet");

                        viewBinding.tvWalletName.setText(selectedWallet.getName());
                    }
                }
        );

        categoryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedCategory = (CategoryResponse) result.getData().getSerializableExtra("selected_category");

                        Glide.with(this)
                                .load(selectedCategory.getIcon().getFileUrl())
                                .into(viewBinding.ivCategoryIcon);

                        viewBinding.tvCategoryName.setText(selectedCategory.getName());
                    }
                }
        );

        tagLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedTags = (ArrayList<TagResponse>) result.getData().getSerializableExtra("selected_tags");

                        if (selectedTags != null && !selectedTags.isEmpty()) {
                            displaySelectedTags(selectedTags);
                        } else {
                            viewBinding.chipGroupTags.removeAllViews();
                        }
                    }
                }
        );

        noteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        note = result.getData().getStringExtra("note");
                        viewBinding.tvNote.setText(note);
                    }
                }
        );

        eventLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Handle the result from the event activity
                    }
                }
        );

        if (selectedTags == null || selectedTags.isEmpty()) {
            addDefaultChip();
        } else {
            displaySelectedTags(selectedTags);
        }

        viewBinding.llWallet.setOnClickListener(v -> {
            Intent intent = new Intent(this, WalletOptionActivity.class);
            walletLauncher.launch(intent);
        });

        viewBinding.llCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra("selected_category", selectedCategory);
            categoryLauncher.launch(intent);
        });

        viewBinding.llTag.setOnClickListener(v -> {
            Intent intent = new Intent(this, TagActivity.class);
            intent.putExtra("selected_tags", selectedTags);
            tagLauncher.launch(intent);
        });

        viewBinding.llNote.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTransactionNoteActivity.class);
            intent.putExtra("note", note);
            noteLauncher.launch(intent);
        });

        setTodayToTextView();

        viewBinding.tvDate.setOnClickListener(v -> showDatePicker());

        viewBinding.llEvent.setOnClickListener(v -> {
            Intent intent = new Intent(this, EventActivity.class);
            intent.putExtra("selected_event", selectedEvent);
            eventLauncher.launch(intent);
        });

    }

    private void addDefaultChip() {
        viewBinding.chipGroupTags.removeAllViews();

        Chip defaultChip = new Chip(this);
        defaultChip.setText("Chọn thẻ");
        defaultChip.setChipBackgroundColorResource(R.color.lavender_gray);
        defaultChip.setTextColor(getResources().getColor(R.color.black));
        defaultChip.setClickable(false);
        defaultChip.setCheckable(false);

        viewBinding.chipGroupTags.addView(defaultChip);
    }

    private void displaySelectedTags(ArrayList<TagResponse> selectedTags) {
        viewBinding.chipGroupTags.removeAllViews();

        for (TagResponse tag : selectedTags) {
            Chip chip = new Chip(this);
            chip.setText(tag.getName());
            chip.setChipBackgroundColorResource(R.color.lavender_gray);
            chip.setTextColor(getResources().getColor(R.color.black));
            chip.setCloseIconVisible(false);
            chip.setClickable(false);
            chip.setCheckable(false);

            viewBinding.chipGroupTags.addView(chip);
        }
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, _year, _month, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(_year, _month, dayOfMonth);

                    String formattedDate = formatDateToVietnamese(selectedDate.getTimeInMillis());
                    viewBinding.tvDate.setText(formattedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private String formatDateToVietnamese(long timestamp) {
        Locale locale = new Locale("vi", "VN");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", locale);
        return sdf.format(new Date(timestamp));
    }

    private void setTodayToTextView() {
        Locale locale = new Locale("vi", "VN");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", locale);
        String today = sdf.format(new Date());

        viewBinding.tvDate.setText(today);
    }


}