package com.moneylover.ui.main.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moneylover.data.model.api.response.CurrencyResponse;
import com.moneylover.databinding.ItemCurrencyBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private final Context context;
    private List<CurrencyResponse> currencyList;
    private final OnItemClickListener onItemClickListener;
    private Long selectedCurrencyId = null;

    public CurrencyAdapter(Context context, List<CurrencyResponse> currencyList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.currencyList = currencyList;
        this.onItemClickListener = onItemClickListener;
    }

    public void setSelectedCurrencyId(Long id) {
        this.selectedCurrencyId = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCurrencyBinding binding = ItemCurrencyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CurrencyResponse currency = currencyList.get(position);
        holder.binding.tvCurrencyName.setText(currency.getName());
        holder.binding.tvCurrencyCode.setText(currency.getCode());

        Glide.with(context)
                .load(currency.getIcon().getFileUrl())
                .into(holder.binding.imgFlag);

        holder.binding.imgCheck.setVisibility(
                currency.getId().equals(selectedCurrencyId) ? View.VISIBLE : View.INVISIBLE
        );

        holder.itemView.setOnClickListener(v -> {
            selectedCurrencyId = currency.getId();
            notifyDataSetChanged();
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currencyList != null ? currencyList.size() : 0;
    }

    public CurrencyResponse getItem(int position) {
        return currencyList.get(position);
    }

    public void updateData(List<CurrencyResponse> newList) {
        this.currencyList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ItemCurrencyBinding binding;

        public ViewHolder(@NonNull ItemCurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
