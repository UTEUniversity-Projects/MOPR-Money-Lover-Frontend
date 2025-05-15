package com.moneylover.ui.main.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ItemWalletOptionBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.List;

public class WalletOptionAdapter extends RecyclerView.Adapter<WalletOptionAdapter.ViewHolder> {

    private final Context context;
    private final List<WalletResponse> walletList;
    private final OnItemClickListener listener;

    public WalletOptionAdapter(Context context, List<WalletResponse> walletList, OnItemClickListener listener) {
        this.context = context;
        this.walletList = walletList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWalletOptionBinding binding = ItemWalletOptionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WalletResponse item = walletList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return walletList != null ? walletList.size() : 0;
    }

//    public void clearSelection() {
//        for (WalletResponse wallet : walletList) {
//            wallet.setSelectedIcon(0);
//        }
//        notifyDataSetChanged();
//    }

    public void updateData(List<WalletResponse> newList) {
        this.walletList.clear();
        this.walletList.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemWalletOptionBinding binding;

        public ViewHolder(ItemWalletOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(WalletResponse walletResponse) {
            Glide.with(context).load(walletResponse.getIcon().getFileUrl()).into(binding.ivIcon);
            binding.tvName.setText(walletResponse.getName());
            binding.tvBalance.setText(NumberUtils.formatNumberWithComma(walletResponse.getBalance() != null ? walletResponse.getBalance() : BigDecimal.valueOf(0.0)));

            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });

        }

    }
}

