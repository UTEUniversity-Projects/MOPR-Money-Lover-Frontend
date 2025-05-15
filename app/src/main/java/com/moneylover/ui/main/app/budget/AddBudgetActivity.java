package com.moneylover.ui.main.app.budget;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateBudgetRequest;
import com.moneylover.data.model.api.response.CategoryResponse;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityAddBudgetBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.ui.main.app.CategoryActivity;
import com.moneylover.ui.main.app.WalletOptionActivity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddBudgetActivity extends BaseActivity<ActivityAddBudgetBinding, AddBudgetViewModel> {

    private Integer selectedPeriodType = Constants.PERIOD_TYPE_MONTH;
    private WalletResponse selectedWallet;
    private CategoryResponse selectedCategory;
    private ActivityResultLauncher<Intent> walletLauncher;
    private ActivityResultLauncher<Intent> categoryLauncher;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_budget;
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

        walletLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            selectedWallet = (WalletResponse) data.getSerializableExtra("selected_wallet");
                            Glide.with(this).load(selectedWallet.getIcon().getFileUrl()).into(viewBinding.ivWalletIcon);
                            viewBinding.tvWalletName.setText(selectedWallet.getName());
                        }
                    }
                });

        categoryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            selectedCategory = (CategoryResponse) data.getSerializableExtra("selected_category");
                            Glide.with(this).load(selectedCategory.getIcon().getFileUrl()).into(viewBinding.ivCategoryIcon);
                            viewBinding.tvCategoryName.setText(selectedCategory.getName());
                        }
                    }
                });

        viewBinding.llCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            categoryLauncher.launch(intent);
        });

        viewBinding.llWallet.setOnClickListener(v -> {
            Intent intent = new Intent(this, WalletOptionActivity.class);
            walletLauncher.launch(intent);
        });

        setupCreateBudget();
        setupPeriodTypeBottomSheet();
        setupStartDate();
        setupEndDate();
    }

    private void setupPeriodTypeBottomSheet() {
        viewBinding.llPeriodType.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(this);
            View sheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_period, null);
            dialog.setContentView(sheetView);

            sheetView.findViewById(R.id.tvWeek).setOnClickListener(view -> {
                updatePeriod(Constants.PERIOD_TYPE_WEEK, Constants.PERIOD_NAME_WEEK, dialog);
            });
            sheetView.findViewById(R.id.tvMonth).setOnClickListener(view -> {
                updatePeriod(Constants.PERIOD_TYPE_MONTH, Constants.PERIOD_NAME_MONTH, dialog);
            });
            sheetView.findViewById(R.id.tvQuarter).setOnClickListener(view -> {
                updatePeriod(Constants.PERIOD_TYPE_QUARTER, Constants.PERIOD_NAME_QUARTER, dialog);
            });
            sheetView.findViewById(R.id.tvYear).setOnClickListener(view -> {
                updatePeriod(Constants.PERIOD_TYPE_YEAR, Constants.PERIOD_NAME_YEAR, dialog);
            });
            sheetView.findViewById(R.id.tvCustom).setOnClickListener(view -> {
                updatePeriod(Constants.PERIOD_TYPE_CUSTOM, Constants.PERIOD_NAME_CUSTOM, dialog);
            });

            dialog.show();
        });
    }

    private void updatePeriod(int type, String name, BottomSheetDialog dialog) {
        selectedPeriodType = type;
        viewBinding.tvPeriodType.setText(name);
        dialog.dismiss();
    }

    private void setupStartDate() {
        viewBinding.tvStartDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        String formatted = formatDate(calendar.getTime());
                        viewBinding.tvStartDate.setText(formatted);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void setupEndDate() {
        viewBinding.tvEndDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        String formatted = formatDate(calendar.getTime());
                        viewBinding.tvEndDate.setText(formatted);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void setupCreateBudget() {
        viewBinding.btnnAd.setOnClickListener(v -> {
            try {
                String amountStr = viewBinding.etAmount.getText().toString().trim();
                if (amountStr.isEmpty()) {
                    viewModel.showErrorMessage("Vui lòng nhập số tiền");
                    return;
                }

                BigDecimal amount = new BigDecimal(amountStr);

                if (selectedWallet == null) {
                    viewModel.showErrorMessage("Vui lòng chọn ví");
                    return;
                }

                if (selectedCategory == null) {
                    viewModel.showErrorMessage("Vui lòng chọn nhóm");
                    return;
                }

                String startDate = parseDateToIso(viewBinding.tvStartDate.getText().toString());
                String endDate = parseDateToIso(viewBinding.tvEndDate.getText().toString());

                CreateBudgetRequest request = CreateBudgetRequest.builder()
                        .walletId(selectedWallet.getId())
                        .categoryId(selectedCategory.getId())
                        .periodType(selectedPeriodType)
                        .startDate(startDate)
                        .endDate(endDate)
                        .amount(amount)
                        .build();

                viewModel.doCreateBudget(new MainCallback<ResponseWrapper<?>>() {
                    @Override
                    public void doSuccess() {
                        viewModel.showSuccessMessage("Tạo ngân sách thành công");
                        finish();
                    }

                    @Override
                    public void doFail() {
                        viewModel.showErrorMessage("Không thể tạo ngân sách");
                    }

                    @Override
                    public void doError(Throwable throwable) {
                        viewModel.showErrorMessage("Lỗi: " + throwable.getMessage());
                    }
                }, request);

            } catch (Exception e) {
                e.printStackTrace();
                viewModel.showErrorMessage("Lỗi xử lý dữ liệu");
            }
        });
    }

    private String parseDateToIso(String dateStr) throws Exception {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
        Date date = inputFormat.parse(dateStr);
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return isoFormat.format(date);
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", new Locale("vi", "VN"));
        return sdf.format(date);
    }
}
