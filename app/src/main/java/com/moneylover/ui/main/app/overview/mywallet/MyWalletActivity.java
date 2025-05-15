package com.moneylover.ui.main.app.overview.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityMyWalletBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.stream.Collectors;

public class MyWalletActivity extends BaseActivity<ActivityMyWalletBinding, MyWalletViewModel> {

    private ActivityResultLauncher<Intent> addWalletLauncher;
    private ActivityResultLauncher<Intent> editWalletLauncher;
    private MyWalletAdapter includeAdapter;
    private MyWalletAdapter notIncludeAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet;
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

        setupWalletList();
        setupEditWalletList();
        setupAddWallet();
    }

    public void setupWalletList() {
        viewModel.doGetWalletList(new MainCallback<List<WalletResponse>>() {
            @Override
            public void doError(Throwable error) {

            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(List<WalletResponse> walletResponses) {
                List<WalletResponse> includeInTotal = walletResponses.stream()
                        .filter(WalletResponse::getChargeToTotal)
                        .collect(Collectors.toList());

                List<WalletResponse> notIncludeInTotal = walletResponses.stream()
                        .filter(wallet -> !wallet.getChargeToTotal())
                        .collect(Collectors.toList());

                if (includeAdapter == null) {
                    includeAdapter = new MyWalletAdapter(MyWalletActivity.this, includeInTotal, getClickListener(includeInTotal));
                    viewBinding.rcvIncludeTotalWalletList.setLayoutManager(new LinearLayoutManager(MyWalletActivity.this, LinearLayoutManager.VERTICAL, false));
                    viewBinding.rcvIncludeTotalWalletList.setAdapter(includeAdapter);
                } else {
                    includeAdapter.updateData(includeInTotal);
                }
                viewBinding.tvIncludeInTotal.setVisibility(includeInTotal.isEmpty() ? View.GONE : View.VISIBLE);

                if (notIncludeAdapter == null) {
                    notIncludeAdapter = new MyWalletAdapter(MyWalletActivity.this, notIncludeInTotal, getClickListener(notIncludeInTotal));
                    viewBinding.rcvNotIncludeTotalWalletList.setLayoutManager(new LinearLayoutManager(MyWalletActivity.this, LinearLayoutManager.VERTICAL, false));
                    viewBinding.rcvNotIncludeTotalWalletList.setAdapter(notIncludeAdapter);
                } else {
                    notIncludeAdapter.updateData(notIncludeInTotal);
                }

                viewBinding.tvNotIncludeInTotal.setVisibility(notIncludeInTotal.isEmpty() ? View.GONE : View.VISIBLE);

            }

            @Override
            public void doFail() {

            }
        });
    }

    public void setupEditWalletList() {
        editWalletLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        setupWalletList();
                    }
                }
        );
        viewBinding.tvEdit.setOnClickListener(v -> {
            Intent intent = new Intent(MyWalletActivity.this, MyWalletEditListActivity.class);
            editWalletLauncher.launch(intent);
        });
    }

    public void setupAddWallet() {
        addWalletLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        setupWalletList();
                    }
                }
        );
        viewBinding.btnAddWallet.setOnClickListener(v -> {
            Intent intent = new Intent(MyWalletActivity.this, AddWalletActivity.class);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                    MyWalletActivity.this,
                    R.anim.slide_in_up,
                    R.anim.no_anim
            );

            addWalletLauncher.launch(intent, options);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupWalletList();
    }

    private OnItemClickListener getClickListener(List<WalletResponse> walletList) {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                WalletResponse selectedWallet = walletList.get(position);
                Intent intent = new Intent(MyWalletActivity.this, MyWalletEditActivity.class);
                intent.putExtra("wallet", selectedWallet);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                        MyWalletActivity.this,
                        R.anim.slide_in_up,
                        R.anim.no_anim
                );
                editWalletLauncher.launch(intent, options);
            }

            @Override
            public void onItemDelete(int position) {
                WalletResponse selectedWallet = walletList.get(position);
                Long walletId = selectedWallet.getId();
                viewModel.doDeleteWallet(new MainCallback<ResponseWrapper<?>>() {
                    @Override
                    public void doError(Throwable error) {
                        viewModel.showErrorMessage("Xóa ví thất bại");
                    }

                    @Override
                    public void doSuccess() {
                        setupWalletList();
                        viewModel.showSuccessMessage("Xóa ví thành công");
                    }

                    @Override
                    public void doFail() {
                        viewModel.showErrorMessage("Xóa ví thất bại");
                    }
                }, walletId);
            }
        };
    }

}