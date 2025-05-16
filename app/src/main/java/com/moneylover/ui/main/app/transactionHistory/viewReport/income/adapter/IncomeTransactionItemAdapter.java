package com.moneylover.ui.main.app.transactionHistory.viewReport.income.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Income;
import com.moneylover.databinding.ItemIncomeTransactionBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class IncomeTransactionItemAdapter extends RecyclerView.Adapter<IncomeTransactionItemAdapter.ViewHolder> {

    private Context context;
    private List<Income> incomes;
    private OnItemClickListener listener;

    public IncomeTransactionItemAdapter(Context context, List<Income> incomes, OnItemClickListener listener) {
        this.context = context;
        this.incomes = incomes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemIncomeTransactionBinding binding = ItemIncomeTransactionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(incomes.get(position));
    }

    @Override
    public int getItemCount() {
        return incomes != null ? incomes.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemIncomeTransactionBinding binding;

        public ViewHolder(@NonNull ItemIncomeTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Income income) {

            binding.ivCategoryIcon.setImageResource(income.getIconResId());
            binding.ivSubIcon.setImageResource(income.getSubIconResId());
            binding.tvCategoryName.setText(income.getName());
            binding.tvTotal.setText(NumberUtils.formatNumberWithComma((int) income.getValue()));

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                final int position = getAdapterPosition();
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
