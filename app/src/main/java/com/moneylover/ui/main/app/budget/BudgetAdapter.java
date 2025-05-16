package com.moneylover.ui.main.app.budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moneylover.data.model.api.response.BudgetResponse;
import com.moneylover.databinding.ItemBudgetBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {
    private Context context;
    private List<BudgetResponse> budgetResponseList;
    private OnItemClickListener listener;

    public BudgetAdapter(Context context, List<BudgetResponse> budgetResponseList, OnItemClickListener listener) {
        this.context = context;
        this.budgetResponseList = budgetResponseList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBudgetBinding binding = ItemBudgetBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetAdapter.ViewHolder holder, int position) {
        BudgetResponse budgetResponse = budgetResponseList.get(position);
        Glide.with(context).load(budgetResponse.getCategoryStatistics().getCategory().getIcon().getFileUrl()).into(holder.binding.iconCategory);
        holder.binding.txtCategory.setText(budgetResponse.getCategoryStatistics().getCategory().getName());
        holder.binding.txtTotal.setText(NumberUtils.formatNumberWithComma(budgetResponse.getAmount()));
        holder.binding.txtRemain.setText("Còn lại " + NumberUtils.formatNumberWithComma(budgetResponse.getAmount().subtract(budgetResponse.getSpentAmount())));
    }

    @Override
    public int getItemCount() {
        return budgetResponseList != null ? budgetResponseList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemBudgetBinding binding;

        public ViewHolder(@NonNull ItemBudgetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
