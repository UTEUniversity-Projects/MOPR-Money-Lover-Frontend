package com.moneylover.ui.main.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.library.baseAdapters.BR;

import com.bumptech.glide.Glide;
import com.moneylover.R;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.CreateWalletRequest;
import com.moneylover.data.model.api.response.CurrencyResponse;
import com.moneylover.data.model.api.response.FileResponse;
import com.moneylover.databinding.ActivityCreateFirstWalletBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.MainActivity;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.utils.NavigationUtils;

import java.util.List;

import timber.log.Timber;

public class CreateFirstWalletActivity extends BaseActivity<ActivityCreateFirstWalletBinding, CreateFirstWalletViewModel> {

    private ActivityResultLauncher<Intent> iconResultLauncher;
    private FileResponse selectedFile;
    private ActivityResultLauncher<Intent> currencyResultLauncher;
    private CurrencyResponse selectedCurrency;

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_first_wallet;
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
        SplashScreen.installSplashScreen(this);
        setTheme(R.style.Theme_MoneyLover);
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

        viewBinding.tvChangeAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(this, WalletIconOptionActivity.class);
            iconResultLauncher.launch(intent);
        });

        setupCurrencyList();
        setupWalletIconOption();
        setupCreateWallet();
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

        viewModel.doGetWalletIconOption(new MainCallback<List<FileResponse>>() {
            @Override
            public void doError(Throwable error) {

            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(List<FileResponse> fileResponses) {
                selectedFile = fileResponses.get(0);
            }

            @Override
            public void doFail() {

            }
        });
    }

    private void setupCurrencyList() {
        viewModel.doGetCurrencyByCode(new MainCallback<List<CurrencyResponse>>() {
            @Override
            public void doError(Throwable error) {
                Timber.e(error, "Error fetching currency");
            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(List<CurrencyResponse> currencyListResponses) {
                selectedCurrency = currencyListResponses.get(0);
                viewBinding.tvCurrency.setText(selectedCurrency.getName());
            }

            @Override
            public void doFail() {

            }
        }, "VND");

        currencyResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedCurrency = (CurrencyResponse) result.getData().getSerializableExtra("selected_currency");
                        if (selectedCurrency != null) {
                            viewBinding.tvCurrency.setText(selectedCurrency.getName());
                        }
                    }
                }
        );

        viewBinding.tvCurrency.setOnClickListener(v -> openCurrencySelection());
        viewBinding.btnEditCurrency.setOnClickListener(v -> openCurrencySelection());
    }

    private void openCurrencySelection() {
        Intent intent = new Intent(this, CurrencyActivity.class);
        intent.putExtra("selected_currency", selectedCurrency);
        currencyResultLauncher.launch(intent);
    }

    public void setupCreateWallet() {

        viewBinding.btnCreateFirstWallet.setOnClickListener(v -> {
            CreateWalletRequest request = CreateWalletRequest.builder()
                    .name(viewBinding.edtWalletName.getText().toString())
                    .currencyId(selectedCurrency.getId())
                    .iconId(selectedFile != null ? selectedFile.getId() : null)
                    .build();
            Timber.tag("CreateFirstWalletActivity").d("Request: %s", request);

            viewModel.doCreateWallet(new MainCallback<ResponseWrapper<?>>() {
                @Override
                public void doError(Throwable error) {

                }

                @Override
                public void doSuccess() {
                    viewModel.showSuccessMessage("Tạo ví thành công");
                    NavigationUtils.navigateToActivityDefaultClearStack(CreateFirstWalletActivity.this, MainActivity.class, null);
                }

                @Override
                public void doFail() {

                }
            }, request);
        });
    }
}