package com.moneylover.ui.main.app.overview.mywallet;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityMyWalletEditListBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.MainCallback;

import java.util.List;

public class MyWalletEditListActivity extends BaseActivity<ActivityMyWalletEditListBinding, MyWalletEditListViewModel> {

    private ActivityResultLauncher<Intent> addWalletLauncher;
    private ActivityResultLauncher<Intent> editWalletLauncher;
    private boolean isWalletDeleted = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_wallet_edit_list;
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

        editWalletLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        setupWalletList();
                    }
                }
        );

        viewBinding.btnBack.setOnClickListener(v -> {
            finish();
        });

        setupWalletList();
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
                MyWalletAdapter adapter = new MyWalletAdapter(MyWalletEditListActivity.this, walletResponses, new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        WalletResponse selectedWallet = walletResponses.get(position);
                        Intent intent = new Intent(MyWalletEditListActivity.this, MyWalletEditActivity.class);
                        intent.putExtra("wallet", selectedWallet);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                                MyWalletEditListActivity.this,
                                R.anim.slide_in_up,
                                R.anim.no_anim
                        );
                        editWalletLauncher.launch(intent, options);
                    }

                    @Override
                    public void onItemDelete(int position) {
                        WalletResponse selectedWallet = walletResponses.get(position);
                        Long walletId = selectedWallet.getId();

                        viewModel.doDeleteWallet(new MainCallback<ResponseWrapper<?>>() {
                            @Override
                            public void doError(Throwable error) {
                                viewModel.showErrorMessage("Xóa ví thất bại");
                            }

                            @Override
                            public void doSuccess() {
                                isWalletDeleted = true;
                                setupWalletList();
                                viewModel.showSuccessMessage("Xóa ví thành công");
                            }

                            @Override
                            public void doFail() {
                                viewModel.showErrorMessage("Xóa ví thất bại");
                            }
                        }, walletId);
                    }
                });
                viewBinding.rcvWalletList.setLayoutManager(new LinearLayoutManager(MyWalletEditListActivity.this, LinearLayoutManager.VERTICAL, false));
                viewBinding.rcvWalletList.setAdapter(adapter);
            }

            @Override
            public void doFail() {

            }
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
            Intent intent = new Intent(MyWalletEditListActivity.this, AddWalletActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                    MyWalletEditListActivity.this,
                    R.anim.slide_in_up,
                    R.anim.no_anim
            );
            addWalletLauncher.launch(intent, options);
        });
    }

    @Override
    public void onBackPressed() {
        if (isWalletDeleted) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}