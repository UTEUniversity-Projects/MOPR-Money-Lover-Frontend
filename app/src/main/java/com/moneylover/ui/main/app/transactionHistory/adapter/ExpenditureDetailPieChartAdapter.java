package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Expenditure;
import com.moneylover.databinding.ItemExpenditurePieChartDetailBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExpenditureDetailPieChartAdapter extends RecyclerView.Adapter<ExpenditureDetailPieChartAdapter.ViewHolder> {

    private final Context context;
    private final List<Expenditure> expenditures;
    private final OnItemClickListener onItemClickListener;

    public ExpenditureDetailPieChartAdapter(Context context, List<Expenditure> expenditures, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.expenditures = expenditures;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExpenditurePieChartDetailBinding binding = ItemExpenditurePieChartDetailBinding.inflate(inflater, parent, false);
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
        private final ItemExpenditurePieChartDetailBinding binding;

        public ViewHolder(@NonNull ItemExpenditurePieChartDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Expenditure expenditure) {
            binding.ivExpenditure.setImageResource(expenditure.getIconRes());
            binding.tvName.setText(expenditure.getName());
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            numberFormat.setMaximumFractionDigits(0);
            String formatted = numberFormat.format(expenditure.getValue());

            binding.tvExpenditure.setText(formatted);        }
    }
}
