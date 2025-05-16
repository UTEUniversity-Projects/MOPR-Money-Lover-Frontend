package com.moneylover.ui.main.app.overview.mywallet;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.UpdateWalletRequest;
import com.moneylover.data.model.api.response.CurrencyResponse;
import com.moneylover.data.model.api.response.FileResponse;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityMyWalletEditBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.ui.main.app.CurrencyActivity;
import com.moneylover.ui.main.app.WalletIconOptionActivity;

public class MyWalletEditActivity extends BaseActivity<ActivityMyWalletEditBinding, MyWalletEditViewModel> {

    private WalletResponse wallet;
    private ActivityResultLauncher<Intent> iconResultLauncher;
    private FileResponse selectedFile;
    private ActivityResultLauncher<Intent> currencyResultLauncher;
    private CurrencyResponse selectedCurrency;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet_edit;
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

        setupEditWallet();
        setupWalletIconOption();
        setupCurrencyList();
        setupUpdateWallet();
        setupDeleteWallet();
    }

    public void setupEditWallet() {
        wallet = (WalletResponse) getIntent().getSerializableExtra("wallet");
        if (wallet != null) {
            Glide.with(this).load(wallet.getIcon().getFileUrl()).into(viewBinding.imgWalletIcon);
            viewBinding.edtWalletName.setText(wallet.getName());
            viewBinding.tvCurrencyName.setText(wallet.getCurrency().getName());
            viewBinding.switchNotification.setChecked(wallet.getTurnOnNotifications());
            viewBinding.switchChargeToTotal.setChecked(wallet.getChargeToTotal());
        }
    }

    private void setupUpdateWallet() {
        viewBinding.tvSave.setOnClickListener(v -> {
            UpdateWalletRequest request = UpdateWalletRequest.builder()
                    .id(wallet.getId())
                    .name(viewBinding.edtWalletName.getText().toString())
                    .turnOnNotifications(viewBinding.switchNotification.isChecked())
                    .chargeToTotal(viewBinding.switchChargeToTotal.isChecked())
                    .isPrimary(wallet.getIsPrimary())
                    .currencyId(selectedCurrency != null ? selectedCurrency.getId() : wallet.getCurrency().getId())
                    .iconId(selectedFile != null ? selectedFile.getId() : wallet.getIcon().getId())
                    .build();

            viewModel.doUpdateWallet(new MainCallback<ResponseWrapper<?>>() {
                @Override
                public void doError(Throwable error) {
                    viewModel.showErrorMessage("Cập nhật ví thất bại !");
                }

                @Override
                public void doSuccess() {
                    viewModel.showSuccessMessage("Cập nhật ví thành công !");
                    setResult(RESULT_OK);
                    finish();
                    overridePendingTransition(R.anim.no_anim, R.anim.slide_out_down);
                }

                @Override
                public void doFail() {
                    viewModel.showErrorMessage("Cập nhật ví thất bại !");
                }
            }, request);
        });


    }

    public void setupDeleteWallet() {
        viewBinding.llDeleteWallet.setOnClickListener(v -> {
            viewModel.doDeleteWallet(new MainCallback<ResponseWrapper<?>>() {
                @Override
                public void doError(Throwable error) {
                    viewModel.showErrorMessage("Xóa ví thất bại !");
                }

                @Override
                public void doSuccess() {
                    viewModel.showSuccessMessage("Xóa ví thành công !");
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void doFail() {
                    viewModel.showErrorMessage("Xóa ví thất bại !");
                }
            }, wallet.getId());
        });
    }

    private void setupWalletIconOption() {
        iconResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedFile = (FileResponse) result.getData().getSerializableExtra("selected_icon");
                        if (selectedFile != null) {
                            Glide.with(this)
                                    .load(selectedFile.getFileUrl())
                                    .into(viewBinding.imgWalletIcon);
                        }
                    }
                }
        );

        viewBinding.imgWalletIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, WalletIconOptionActivity.class);
            iconResultLauncher.launch(intent);
        });

    }

    private void setupCurrencyList() {
        viewBinding.tvCurrencyName.setText(selectedCurrency != null ? selectedCurrency.getName() : wallet.getCurrency().getName());

        viewBinding.tvCurrencyName.setOnClickListener(v -> {
            Intent intent = new Intent(this, CurrencyActivity.class);
            intent.putExtra("selected_currency", selectedCurrency);
            currencyResultLauncher.launch(intent);
        });

        currencyResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedCurrency = (CurrencyResponse) result.getData().getSerializableExtra("selected_currency");
                        if (selectedCurrency != null) {
                            viewBinding.tvCurrencyName.setText(selectedCurrency.getName());
                        }
                    }
                }
        );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_down);
    }
}