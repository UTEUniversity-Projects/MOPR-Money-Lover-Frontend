package com.moneylover.ui.main.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.data.model.api.response.FileResponse;
import com.moneylover.databinding.ActivityWalletIconOptionBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.custom.grid.GridSpacingItemDecoration;
import com.moneylover.ui.main.MainCallback;

import java.util.ArrayList;
import java.util.List;

public class WalletIconOptionActivity extends BaseActivity<ActivityWalletIconOptionBinding, WalletIconOptionViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_icon_option;
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

        setupWalletIconOption();
    }

    private void setupWalletIconOption() {
        viewModel.resetPaging();
        List<FileResponse> allIcons = new ArrayList<>();
        WalletIconOptionAdapter adapter = new WalletIconOptionAdapter(this, allIcons, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FileResponse selected = allIcons.get(position);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selected_icon", selected);
                setResult(RESULT_OK, resultIntent);
                finish();
            }

            @Override
            public void onItemDelete(int position) {

            }
        });

        int spacing = getResources().getDimensionPixelSize(com.intuit.ssp.R.dimen._4ssp);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == 1 ? 5 : 1;
            }
        });

        viewBinding.rcvWalletIcon.setLayoutManager(layoutManager);
        viewBinding.rcvWalletIcon.addItemDecoration(new GridSpacingItemDecoration(5, spacing, true));
        viewBinding.rcvWalletIcon.setAdapter(adapter);

        viewModel.doGetWalletList(new MainCallback<>() {
            @Override
            public void doSuccess(List<FileResponse> newItems) {
                int insertStart = allIcons.size();
                allIcons.addAll(newItems);
                adapter.notifyItemRangeInserted(insertStart, newItems.size());
            }

            @Override
            public void doError(Throwable error) {
            }

            @Override
            public void doFail() {
            }

            @Override
            public void doSuccess() {
            }
        }, null);

        viewBinding.rcvWalletIcon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (!viewModel.isLoading() && !viewModel.isLastPage() &&
                        (visibleItemCount + firstVisibleItem) >= totalItemCount - 2 && firstVisibleItem >= 0) {

                    adapter.addLoadingFooter();

                    viewModel.doGetWalletList(new MainCallback<>() {
                        @Override
                        public void doSuccess(List<FileResponse> newItems) {
                            adapter.removeLoadingFooter();
                            int insertStart = allIcons.size();
                            allIcons.addAll(newItems);
                            adapter.notifyItemRangeInserted(insertStart, newItems.size());
                        }

                        @Override
                        public void doError(Throwable error) {
                            adapter.removeLoadingFooter();
                        }

                        @Override
                        public void doFail() {
                            adapter.removeLoadingFooter();
                        }

                        @Override
                        public void doSuccess() {
                        }
                    }, null);
                }
            }
        });
    }
}
