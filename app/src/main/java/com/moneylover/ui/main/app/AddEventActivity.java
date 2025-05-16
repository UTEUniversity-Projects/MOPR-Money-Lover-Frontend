package com.moneylover.ui.main.app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateEventRequest;
import com.moneylover.data.model.api.response.FileResponse;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityAddEventBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.MainCallback;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEventActivity extends BaseActivity<ActivityAddEventBinding, AddEventViewModel> {

    private ActivityResultLauncher<Intent> iconLauncher;
    private FileResponse selectedFile;
    private ActivityResultLauncher<Intent> walletLauncher;
    private WalletResponse selectedWallet;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event;
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

        viewBinding.btnBack.setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.no_anim, R.anim.slide_out_down);
        });

        iconLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedFile = (FileResponse) result.getData().getSerializableExtra("selected_icon");

                        Glide.with(this)
                                .load(selectedFile.getFileUrl())
                                .into(viewBinding.ivEventIcon);

                    }
                }
        );

        walletLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedWallet = (WalletResponse) result.getData().getSerializableExtra("selected_wallet");

                        Glide.with(this)
                                .load(selectedWallet.getIcon().getFileUrl())
                                .into(viewBinding.ivWalletIcon);

                        viewBinding.tvWalletName.setText(selectedWallet.getName());
                    }
                }
        );

        // Set mặc định ngày hôm nay
        Date today = new Date();
        String todayFormatted = formatDateWithDay(today);
        viewBinding.tvStartDate.setText(todayFormatted);
        viewBinding.endDate.setText(todayFormatted);

        // Click chọn ngày bắt đầu
        viewBinding.tvStartDate.setOnClickListener(v -> showDatePicker(viewBinding.tvStartDate));

        // Click chọn ngày kết thúc
        viewBinding.endDate.setOnClickListener(v -> showDatePicker(viewBinding.endDate));

        viewBinding.ivEventIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, WalletIconOptionActivity.class);
            iconLauncher.launch(intent);
        });

        viewBinding.llWallet.setOnClickListener(v -> {
            Intent intent = new Intent(this, WalletOptionActivity.class);
            walletLauncher.launch(intent);
        });

        setupCreateWallet();
    }

    private void showDatePicker(TextView targetTextView) {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    String formatted = formatDateWithDay(calendar.getTime());
                    targetTextView.setText(formatted);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private String formatDateWithDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
        return sdf.format(date);
    }

    public void setupCreateWallet() {
        viewBinding.btnAddEvent.setOnClickListener(v -> {
            try {

                if (selectedFile == null) {
                    viewModel.showErrorMessage("Vui lòng chọn biểu tượng sự kiện");
                    return;
                }

                if (viewBinding.tvEventName.getText().toString().isEmpty()) {
                    viewModel.showErrorMessage("Tên sự kiện không được để trống");
                    return;
                }

                if (viewBinding.tvStartDate.getText().toString().isEmpty()) {
                    viewModel.showErrorMessage("Ngày bắt đầu không được để trống");
                    return;
                }

                if (viewBinding.endDate.getText().toString().isEmpty()) {
                    viewModel.showErrorMessage("Ngày kết thúc không được để trống");
                    return;
                }

                if (parseDateToInstant(viewBinding.tvStartDate.getText().toString()).isAfter(parseDateToInstant(viewBinding.endDate.getText().toString()))) {
                    viewModel.showErrorMessage("Ngày bắt đầu không được lớn hơn ngày kết thúc");
                    return;
                }

                Instant now = Instant.now();
                Instant start = parseDateToInstant(viewBinding.tvStartDate.getText().toString());
                Instant end = parseDateToInstant(viewBinding.endDate.getText().toString());

                if (!start.isAfter(now)) {
                    viewModel.showErrorMessage("Ngày bắt đầu phải ở trong tương lai");
                    return;
                }

                if (!end.isAfter(now)) {
                    viewModel.showErrorMessage("Ngày kết thúc phải ở trong tương lai");
                    return;
                }

                if (selectedWallet == null) {
                    viewModel.showErrorMessage("Vui lòng chọn ví");
                    return;
                }

                if (viewBinding.tvDesc.getText().toString().isEmpty()) {
                    viewModel.showErrorMessage("Mô tả không được để trống");
                    return;
                }

                String startIso = parseDateToInstant(viewBinding.tvStartDate.getText().toString()).toString();
                String endIso = parseDateToInstant(viewBinding.endDate.getText().toString()).toString();
                CreateEventRequest request = CreateEventRequest.builder()
                        .name(viewBinding.tvEventName.getText().toString())
                        .description(viewBinding.tvDesc.getText().toString())
                        .startDate(startIso)
                        .endDate(endIso)
                        .walletId(selectedWallet != null ? selectedWallet.getId() : null)
                        .iconId(selectedFile != null ? selectedFile.getId() : null)
                        .build();

                viewModel.doCreateEvent(new MainCallback<ResponseWrapper<?>>() {
                    @Override
                    public void doError(Throwable error) {

                    }

                    @Override
                    public void doSuccess() {
                        viewModel.showSuccessMessage("Thêm sự kiện thành công");
                        finish();
                        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_down);
                    }

                    @Override
                    public void doFail() {

                    }
                }, request);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Instant parseDateToInstant(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
        sdf.setTimeZone(java.util.TimeZone.getDefault()); // local timezone
        Date date = sdf.parse(dateStr);
        return date.toInstant(); // convert to Instant (UTC)
    }
}
