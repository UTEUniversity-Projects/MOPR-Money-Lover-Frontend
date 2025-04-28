package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Expenditure;
import com.moneylover.databinding.ItemExpenditureBarchartDetailBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExpenditureDetailBarchartAdapter extends RecyclerView.Adapter<ExpenditureDetailBarchartAdapter.ViewHolder> {

    private final Context context;
    private final List<Expenditure> expenditures;
    private final OnItemClickListener onItemClickListener;

    public ExpenditureDetailBarchartAdapter(Context context, List<Expenditure> expenditures, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.expenditures = expenditures;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExpenditureBarchartDetailBinding binding = ItemExpenditureBarchartDetailBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(expenditures.get(position));
    }

    @Override
    public int getItemCount() {
        return expenditures.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemExpenditureBarchartDetailBinding binding;

        public ViewHolder(@NonNull ItemExpenditureBarchartDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Expenditure expenditure) {
            binding.tvDateRange.setText(expenditure.getDate());

            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            numberFormat.setMaximumFractionDigits(0);
            String formatted = numberFormat.format(expenditure.getValue());

            binding.tvExpenditure.setText(formatted);

        }
    }
}
