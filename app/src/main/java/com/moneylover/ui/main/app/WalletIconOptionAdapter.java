package com.moneylover.ui.main.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moneylover.data.model.api.response.FileResponse;
import com.moneylover.databinding.ItemLoadingFooterBinding;
import com.moneylover.databinding.ItemWalletIconOptionBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.util.List;

public class WalletIconOptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ICON = 0;
    private static final int TYPE_LOADING = 1;

    private final Context context;
    private final List<FileResponse> walletIconOptions;
    private final OnItemClickListener listener;
    private boolean isLoadingAdded = false;

    public WalletIconOptionAdapter(Context context, List<FileResponse> walletIconOptions, OnItemClickListener listener) {
        this.context = context;
        this.walletIconOptions = walletIconOptions;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == walletIconOptions.size() && isLoadingAdded) ? TYPE_LOADING : TYPE_ICON;
    }

    @Override
    public int getItemCount() {
        return walletIconOptions.size() + (isLoadingAdded ? 1 : 0);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            // Inflate item_loading_footer.xml
            ItemLoadingFooterBinding binding = ItemLoadingFooterBinding.inflate(LayoutInflater.from(context), parent, false);
            return new LoadingViewHolder(binding);
        } else {
            ItemWalletIconOptionBinding binding = ItemWalletIconOptionBinding.inflate(LayoutInflater.from(context), parent, false);
            return new IconViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IconViewHolder) {
            FileResponse icon = walletIconOptions.get(position);
            IconViewHolder iconHolder = (IconViewHolder) holder;

            Glide.with(context)
                    .load(icon.getFileUrl())
                    .into(iconHolder.binding.imgIcon);

            iconHolder.binding.imgIcon.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(position);
            });
        }
    }

    public void addLoadingFooter() {
        if (!isLoadingAdded) {
            isLoadingAdded = true;
            notifyItemInserted(walletIconOptions.size());
        }
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;
            notifyItemRemoved(walletIconOptions.size());
        }
    }

    public class IconViewHolder extends RecyclerView.ViewHolder {
        public final ItemWalletIconOptionBinding binding;

        public IconViewHolder(@NonNull ItemWalletIconOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public final ItemLoadingFooterBinding binding;
        public LoadingViewHolder(@NonNull ItemLoadingFooterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}

