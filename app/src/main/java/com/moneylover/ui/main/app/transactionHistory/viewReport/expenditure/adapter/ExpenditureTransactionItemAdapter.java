package com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Expenditure;
import com.moneylover.databinding.ItemExpenditureTransactionBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class ExpenditureTransactionItemAdapter extends RecyclerView.Adapter<ExpenditureTransactionItemAdapter.ViewHolder> {

    private Context context;
    private List<Expenditure> expenditures;
    private OnItemClickListener listener;

    public ExpenditureTransactionItemAdapter(Context context, List<Expenditure> expenditures, OnItemClickListener listener) {
        this.context = context;
        this.expenditures = expenditures;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpenditureTransactionItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemExpenditureTransactionBinding binding = ItemExpenditureTransactionBinding.inflate(inflater, parent, false);
        return new ExpenditureTransactionItemAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureTransactionItemAdapter.ViewHolder holder, int position) {
        holder.bind(expenditures.get(position));
    }

    @Override
    public int getItemCount() {
        return expenditures != null ? expenditures.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemExpenditureTransactionBinding binding;

        public ViewHolder(@NonNull ItemExpenditureTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Expenditure expenditures) {

            binding.ivCategoryIcon.setImageResource(expenditures.getIconResId());
            binding.ivSubIcon.setImageResource(expenditures.getSubIconResId());
            binding.tvCategoryName.setText(expenditures.getName());
            binding.tvTotal.setText(NumberUtils.formatNumberWithComma((int) expenditures.getValue()));

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
