package com.moneylover.ui.main.app.transactionHistory.viewReport.income.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Income;
import com.moneylover.databinding.ItemIncomePieChartBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class IncomeDetailPieChartAdapter extends RecyclerView.Adapter<IncomeDetailPieChartAdapter.ViewHolder> {

    private final Context context;
    private final List<Income> incomes;
    private final OnItemClickListener listener;

    public IncomeDetailPieChartAdapter(Context context, List<Income> incomes, OnItemClickListener listener) {
        this.context = context;
        this.incomes = incomes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IncomeDetailPieChartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemIncomePieChartBinding binding = ItemIncomePieChartBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeDetailPieChartAdapter.ViewHolder holder, int position) {
        holder.bind(incomes.get(position));
    }

    @Override
    public int getItemCount() {
        return incomes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemIncomePieChartBinding binding;

        public ViewHolder(@NonNull ItemIncomePieChartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Income income) {
            binding.ivIncome.setImageResource(income.getIconResId());
            binding.tvName.setText(income.getName());
            binding.tvIncome.setText(NumberUtils.formatNumberWithComma(income.getValue()));

            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });
        }
    }
}
