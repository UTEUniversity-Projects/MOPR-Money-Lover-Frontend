package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Income;
import com.moneylover.databinding.ItemIncomeDetailBarchartBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class IncomeDetailBarchartAdapter extends RecyclerView.Adapter<IncomeDetailBarchartAdapter.ViewHolder> {

    private final Context context;
    private final List<Income> incomes;
    private final OnItemClickListener onItemClickListener;

    public IncomeDetailBarchartAdapter(Context context, List<Income> incomes, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.incomes = incomes;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemIncomeDetailBarchartBinding binding = ItemIncomeDetailBarchartBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(incomes.get(position));
    }

    @Override
    public int getItemCount() {
        return incomes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemIncomeDetailBarchartBinding binding;

        public ViewHolder(@NonNull ItemIncomeDetailBarchartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Income income) {
            binding.tvDateRange.setText(income.getDate());

            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            numberFormat.setMaximumFractionDigits(0);
            String formatted = numberFormat.format(income.getValue());

            binding.tvIncome.setText(formatted);

        }
    }
}
