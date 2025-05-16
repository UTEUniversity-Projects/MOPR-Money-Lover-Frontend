package com.moneylover.ui.main.app.transactionHistory;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.Wallet;
import com.moneylover.databinding.ActivityWalletEditBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.adapter.WalletAdapter;

import java.util.ArrayList;
import java.util.List;

public class WalletEditActivity extends BaseActivity<ActivityWalletEditBinding, WalletEditViewModel> {

    private WalletAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_edit;
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
        setupWallet();
    }

    public void setupWallet() {
        List<Wallet> walletList = new ArrayList<>();
        walletList.add(new Wallet(R.drawable.ic_yellow_star, R.drawable.ic_wallet_2, "Tiền mặt ", 100000));

        for (int i = 1; i <= 10; i++) {
            walletList.add(new Wallet(0, R.drawable.ic_wallet_2, "Tiền mặt " + i, 100000 + i));
        }

        adapter = new WalletAdapter(walletList, new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {
            }
        });

        viewBinding.rcvWalletList.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.rcvWalletList.setAdapter(adapter);

    }

}