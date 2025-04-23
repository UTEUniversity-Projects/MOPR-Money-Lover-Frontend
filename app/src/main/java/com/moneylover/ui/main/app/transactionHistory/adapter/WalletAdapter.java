package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Wallet;
import com.moneylover.databinding.ItemWalletBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private List<Wallet> walletList;
    private OnItemClickListener listener;
    private int selectedPosition;

    public WalletAdapter(List<Wallet> walletList, int selectedPosition, OnItemClickListener listener) {
        this.walletList = walletList;
        this.listener = listener;
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWalletBinding binding = ItemWalletBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wallet item = walletList.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        return walletList != null ? walletList.size() : 0;
    }

    public void clearSelection() {
        selectedPosition = -1;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemWalletBinding binding;

        public ViewHolder(ItemWalletBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position;
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        public void bind(Wallet item, int position) {
            binding.imgIcon.setImageResource(item.getIcon());
            binding.tvName.setText(item.getName());
            binding.tvBalance.setText(String.valueOf(item.getBalance()));

            if (position == selectedPosition) {
                binding.imgGreenCircle.setVisibility(View.VISIBLE);
            } else {
                binding.imgGreenCircle.setVisibility(View.INVISIBLE);
            }

        }
    }
}

