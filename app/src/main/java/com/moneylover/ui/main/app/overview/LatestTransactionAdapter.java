package com.moneylover.ui.main.app.overview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.LatestTransaction;
import com.moneylover.databinding.ItemLatestTransactionBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.util.List;

public class LatestTransactionAdapter extends RecyclerView.Adapter<LatestTransactionAdapter.ViewHolder> {

    private final List<LatestTransaction> transactionList;
    private final OnItemClickListener listener;

    public LatestTransactionAdapter(List<LatestTransaction> transactionList, OnItemClickListener listener) {
        this.transactionList = transactionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLatestTransactionBinding binding = ItemLatestTransactionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LatestTransaction transaction = transactionList.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactionList != null ? transactionList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLatestTransactionBinding binding;

        public ViewHolder(@NonNull ItemLatestTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(position);
                }
            });
        }

        public void bind(LatestTransaction transaction) {
            binding.tvCategoryName.setText(transaction.getCategory());
            binding.tvDate.setText(transaction.getDate());
            binding.tvAmount.setText(transaction.getFormattedAmount());
            binding.ivCategoryIcon.setImageResource(transaction.getCategoryIconResId());
            binding.ivSubIcon.setImageResource(transaction.getSubIconResId());
        }
    }
}
