package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.data.model.TransactionHistory;
import com.moneylover.data.model.TransactionHistoryWallet;
import com.moneylover.databinding.ItemTransactionHistoryBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailActivity;

import java.util.List;

public class TransactionHistoryListAdapter extends RecyclerView.Adapter<TransactionHistoryListAdapter.GroupViewHolder> {
    private final Context context;
    private final List<TransactionHistory> data;
    private final OnItemClickListener onItemClickListener;

    public TransactionHistoryListAdapter(Context context, List<TransactionHistory> data, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionHistoryBinding binding = ItemTransactionHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        TransactionHistory item = data.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder {
        private final ItemTransactionHistoryBinding binding;

        public GroupViewHolder(ItemTransactionHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TransactionHistory item) {
            binding.tvCategory.setText(item.getCategory());
            binding.tvTransactionCount.setText(item.getTransactionCount());

            double total = 0;
            for (TransactionHistoryWallet wallet : item.getWallets()) {
                try {
                    double amount = Double.parseDouble(wallet.getAmount().replace(",", ""));
                    total += wallet.isIncome() ? amount : -amount;
                } catch (NumberFormatException ignored) {}
            }

            String formattedTotal = String.format("%,.0f", Math.abs(total));
            binding.tvTotalBalance.setText((total < 0 ? "-" : "") + formattedTotal);
            binding.tvTotalBalance.setTextColor(context.getColor(R.color.black));

            TransactionHistoryNestedAdapter nestedAdapter = new TransactionHistoryNestedAdapter(context, item.getWallets(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(context, TransactionHistoryDetailActivity.class);
                    context.startActivity(intent);
                }

                @Override
                public void onItemDelete(int position) {

                }
            });
            binding.rcvNested.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            binding.rcvNested.setAdapter(nestedAdapter);
        }
    }
}
