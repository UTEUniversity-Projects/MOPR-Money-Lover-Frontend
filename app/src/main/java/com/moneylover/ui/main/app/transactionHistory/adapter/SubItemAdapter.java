package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.databinding.ItemSubRowBinding;

import java.util.List;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.ViewHolder> {

    private final List<String> items;

    public SubItemAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public SubItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSubRowBinding binding = ItemSubRowBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubRowBinding binding;

        public ViewHolder(@NonNull ItemSubRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String item) {
            binding.tvItem.setText(item);
        }
    }
}
