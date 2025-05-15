package com.moneylover.ui.main.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityWalletOptionBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.MainCallback;

import java.util.List;
import java.util.stream.Collectors;

public class WalletOptionActivity extends BaseActivity<ActivityWalletOptionBinding, WalletOptionViewModel> {

    private WalletOptionAdapter includeAdapter;
    private WalletOptionAdapter notIncludeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_option;
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
                    includeAdapter = new WalletOptionAdapter(WalletOptionActivity.this, includeInTotal, getClickListener(includeInTotal));
                    viewBinding.rcvIncludeTotalWalletList.setLayoutManager(new LinearLayoutManager(WalletOptionActivity.this, LinearLayoutManager.VERTICAL, false));
                    viewBinding.rcvIncludeTotalWalletList.setAdapter(includeAdapter);
                } else {
                    includeAdapter.updateData(includeInTotal);
                }
                viewBinding.tvIncludeInTotal.setVisibility(includeInTotal.isEmpty() ? View.GONE : View.VISIBLE);

                if (notIncludeAdapter == null) {
                    notIncludeAdapter = new WalletOptionAdapter(WalletOptionActivity.this, notIncludeInTotal, getClickListener(notIncludeInTotal));
                    viewBinding.rcvNotIncludeTotalWalletList.setLayoutManager(new LinearLayoutManager(WalletOptionActivity.this, LinearLayoutManager.VERTICAL, false));
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

                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_wallet", selectedWallet);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onItemDelete(int position) {
            }
        };
    }
}