package com.moneylover.ui.main.app.transactionHistory;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private final Wallet totalWallet = new Wallet(R.drawable.bg_green_circle, R.drawable.ic_wallet_2, "Tổng cộng", -4156598);
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
            viewBinding.imgGreenCircle.setImageResource(0);
        } else {
            viewBinding.imgGreenCircle.setImageResource(R.drawable.bg_green_circle);
        }
        setupWalletList(selectedWallet);

        viewBinding.totalBalance.setOnClickListener(v -> {
            adapter.clearSelection();
            viewBinding.imgGreenCircle.setImageResource(R.drawable.bg_green_circle);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("wallet", totalWallet);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

    }

    public void setupWalletList(Wallet selectedWallet) {
        List<Wallet> walletList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            walletList.add(new Wallet(0, R.drawable.ic_wallet_2, "Tiền mặt " + i, 100000 + i));
        }

        int selectedPosition = -1;
        if (selectedWallet != null && !"Tổng cộng".equals(selectedWallet.getName())) {
            for (int i = 0; i < walletList.size(); i++) {
                if (walletList.get(i).equals(selectedWallet)) {
                    selectedPosition = i;
                    walletList.get(i).setSelectedIcon(R.drawable.bg_green_circle);
                    break;
                }
            }
        }

        adapter = new WalletAdapter(walletList, new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                for (Wallet w : walletList) {
                    w.setSelectedIcon(0);
                }

                Wallet chosen = walletList.get(position);
                chosen.setSelectedIcon(R.drawable.bg_green_circle);

                viewBinding.imgGreenCircle.setImageResource(0);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("wallet", chosen);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onItemDelete(int position) {}
        });

        viewBinding.rcvWalletList.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.rcvWalletList.setAdapter(adapter);

        if (selectedPosition != -1) {
            int finalSelectedPosition = selectedPosition;
            viewBinding.rcvWalletList.post(() -> {
                RecyclerView.ViewHolder holder = viewBinding.rcvWalletList.findViewHolderForAdapterPosition(finalSelectedPosition);
                if (holder != null) {
                    int[] location = new int[2];
                    holder.itemView.getLocationOnScreen(location);

                    int[] listLocation = new int[2];
                    viewBinding.rcvWalletList.getLocationOnScreen(listLocation);

                    int scrollY = location[1] - listLocation[1];

                    viewBinding.nestedScrollView.smoothScrollBy(0, scrollY);
                } else {
                    viewBinding.rcvWalletList.scrollToPosition(finalSelectedPosition);
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onEditClick() {
        Intent intent = new Intent(this, WalletEditActivity.class);
        startActivity(intent);
    }
}