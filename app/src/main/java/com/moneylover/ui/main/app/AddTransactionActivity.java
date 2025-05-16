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
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateBillRequest;
import com.moneylover.data.model.api.request.CreateReminderRequest;
import com.moneylover.data.model.api.response.CategoryResponse;
import com.moneylover.data.model.api.response.EventResponse;
import com.moneylover.data.model.api.response.ReminderResponse;
import com.moneylover.data.model.api.response.TagResponse;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityAddTransactionBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.MainCallback;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTransactionActivity extends BaseActivity<ActivityAddTransactionBinding, AddTransactionViewModel> {

    private ActivityResultLauncher<Intent> walletLauncher;
    private ActivityResultLauncher<Intent> categoryLauncher;
    private ActivityResultLauncher<Intent> tagLauncher;
    private ActivityResultLauncher<Intent> noteLauncher;
    private ActivityResultLauncher<Intent> eventLauncher;

    private ArrayList<TagResponse> selectedTags = new ArrayList<>();
    private CategoryResponse selectedCategory;
    private WalletResponse selectedWallet;
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
                        selectedWallet = (WalletResponse) result.getData().getSerializableExtra("selected_wallet");
                        Glide.with(this).load(selectedWallet.getIcon().getFileUrl()).into(viewBinding.ivWalletIcon);
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
                        selectedEvent = (EventResponse) result.getData().getSerializableExtra("selected_event");
                        viewBinding.tvEventName.setText(selectedEvent.getName());
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
            intent.putExtra("selected_wallet", selectedWallet);
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

        setupCreateDate();

        viewBinding.llEvent.setOnClickListener(v -> {
            Intent intent = new Intent(this, EventActivity.class);
            intent.putExtra("selected_event", selectedEvent);
            eventLauncher.launch(intent);
        });

        setupReminder();
        setupCreateBill();

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

    private void setupCreateDate() {
        viewBinding.tvDate.setOnClickListener(v -> {
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
        });

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

    private void setupReminder() {
        viewBinding.tvReminderDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);

                        String formatted = formatDateToVietnamese(calendar.getTimeInMillis());
                        viewBinding.tvReminderDate.setText(formatted);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        });
    }

    private void setupCreateBill() {
        viewBinding.btnSave.setOnClickListener(v -> {
            try {
                if (selectedWallet == null) {
                    viewModel.showErrorMessage("Vui lòng chọn ví");
                    return;
                }

                String rawAmount = viewBinding.etAmount.getText().toString().trim();
                if (rawAmount.isEmpty()) {
                    viewModel.showErrorMessage("Vui lòng nhập số tiền");
                    return;
                }

                BigDecimal amount;
                try {
                    NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
                    Number parsed = nf.parse(rawAmount);
                    amount = new BigDecimal(parsed.toString());
                } catch (ParseException e) {
                    viewModel.showErrorMessage("Số tiền không hợp lệ");
                    return;
                }

                if (selectedCategory == null) {
                    viewModel.showErrorMessage("Vui lòng chọn nhóm");
                    return;
                }

                String dateStr = viewBinding.tvDate.getText().toString();
                if (dateStr.isEmpty()) {
                    viewModel.showErrorMessage("Vui lòng chọn ngày giao dịch");
                    return;
                }

                String isoDate = parseDateToInstant(dateStr).toString();

                // Kiểm tra nếu người dùng đã chọn reminder
                String reminderDateStr = viewBinding.tvReminderDate.getText().toString();
                if (!reminderDateStr.equals("Đặt nhắc hẹn") && !reminderDateStr.isEmpty()) {
                    Instant reminderInstant = parseDateToInstant(reminderDateStr);

                    CreateReminderRequest reminderRequest = CreateReminderRequest.builder()
                            .time(reminderInstant.toString())
                            .build();

                    viewModel.doCreateReminder(new MainCallback<ResponseWrapper<?>>() {
                        @Override
                        public void doSuccess() {
                            // Gọi lấy reminder mới tạo
                            viewModel.doGetLatestReminder(new MainCallback<List<ReminderResponse>>() {
                                @Override
                                public void doSuccess(List<ReminderResponse> list) {
                                    if (!list.isEmpty()) {
                                        Long reminderId = list.get(0).getId();
                                        createBill(amount, isoDate, reminderId);
                                    } else {
                                        createBill(amount, isoDate, null);
                                    }
                                }

                                @Override
                                public void doFail() {
                                    createBill(amount, isoDate, null);
                                }

                                @Override
                                public void doError(Throwable throwable) {
                                    createBill(amount, isoDate, null);
                                }

                                @Override
                                public void doSuccess() {

                                }
                            });
                        }

                        @Override
                        public void doFail() {
                            createBill(amount, isoDate, null);
                        }

                        @Override
                        public void doError(Throwable throwable) {
                            createBill(amount, isoDate, null);
                        }
                    }, reminderRequest);
                } else {
                    createBill(amount, isoDate, null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                viewModel.showErrorMessage("Lỗi xử lý dữ liệu");
            }
        });
    }

    private void createBill(BigDecimal amount, String isoDate, Long reminderId) {
        CreateBillRequest request = CreateBillRequest.builder()
                .amount(amount)
                .date(isoDate)
                .note(note)
                .walletId(selectedWallet.getId())
                .categoryId(selectedCategory.getId())
                .tagIds(getTagIdsFromSelectedTags())
                .eventId(selectedEvent != null ? selectedEvent.getId() : null)
                .reminderId(reminderId)
                .isIncludedReport(!viewBinding.cbExcludeFromReport.isChecked())
                .build();

        viewModel.doCreateBill(new MainCallback<ResponseWrapper<?>>() {
            @Override
            public void doSuccess() {
                viewModel.showSuccessMessage("Thêm giao dịch thành công");
                finish();
                overridePendingTransition(R.anim.no_anim, R.anim.slide_out_down);
            }

            @Override
            public void doFail() {
                viewModel.showErrorMessage("Không thể tạo giao dịch");
            }

            @Override
            public void doError(Throwable throwable) {
                viewModel.showErrorMessage("Lỗi: " + throwable.getMessage());
            }
        }, request);
    }

    private List<Long> getTagIdsFromSelectedTags() {
        List<Long> tagIds = new ArrayList<>();
        for (TagResponse tag : selectedTags) {
            tagIds.add(tag.getId());
        }
        return tagIds;
    }

    private Instant parseDateToInstant(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
        sdf.setTimeZone(java.util.TimeZone.getDefault());
        Date date = sdf.parse(dateStr);
        return date.toInstant();
    }


}