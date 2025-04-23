package com.moneylover.ui.main.app.transactionHistory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.Wallet;
import com.moneylover.databinding.ActivityWalletBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.adapter.WalletAdapter;

import java.util.ArrayList;
import java.util.List;

public class WalletActivity extends BaseActivity<ActivityWalletBinding, WalletViewModel> {

    private final Wallet totalWallet = new Wallet(R.drawable.ic_wallet_2, "Tổng cộng", -4156598);
    private WalletAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
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

        Wallet selectedWallet = (Wallet) getIntent().getSerializableExtra("selected_wallet");

        if (selectedWallet != null && !"Tổng cộng".equals(selectedWallet.getName())) {
            viewBinding.imgGreenCircle.setVisibility(View.INVISIBLE);
        } else {
            viewBinding.imgGreenCircle.setVisibility(View.VISIBLE);
        }
        setupWalletList(selectedWallet);

        viewBinding.totalBalance.setOnClickListener(v -> {
            viewBinding.imgGreenCircle.setVisibility(View.VISIBLE);
            adapter.clearSelection();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("wallet", totalWallet);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

    }

    public void setupWalletList(Wallet selectedWallet) {
        List<Wallet> walletList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            walletList.add(new Wallet(R.drawable.ic_wallet_2, "Tiền mặt " + i, 100000 + i));
        }

        int selectedPosition = -1;
        if (selectedWallet != null && !"Tổng cộng".equals(selectedWallet.getName())) {
            for (int i = 0; i < walletList.size(); i++) {
                if (walletList.get(i).equals(selectedWallet)) {
                    selectedPosition = i;
                    break;
                }
            }
        }

        adapter = new WalletAdapter(walletList, selectedPosition, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                viewBinding.imgGreenCircle.setVisibility(View.INVISIBLE); // ẩn chấm xanh tổng cộng

                Wallet chosen = walletList.get(position);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("wallet", chosen);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onItemDelete(int position) {
            }
        });

        viewBinding.rcvWalletList.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.rcvWalletList.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}