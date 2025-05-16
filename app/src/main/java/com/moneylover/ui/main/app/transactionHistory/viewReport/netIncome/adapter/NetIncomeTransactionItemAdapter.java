package com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.NetIncome;
import com.moneylover.databinding.ItemNetIncomeTransactionBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class NetIncomeTransactionItemAdapter extends RecyclerView.Adapter<NetIncomeTransactionItemAdapter.ViewHolder> {

    private Context context;
    private List<NetIncome> netIncomes;
    private OnItemClickListener listener;

    public NetIncomeTransactionItemAdapter(Context context, List<NetIncome> netIncomes, OnItemClickListener listener) {
        this.context = context;
        this.netIncomes = netIncomes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemNetIncomeTransactionBinding binding = ItemNetIncomeTransactionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(netIncomes.get(position));
    }

    @Override
    public int getItemCount() {
        return netIncomes != null ? netIncomes.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNetIncomeTransactionBinding binding;

        public ViewHolder(@NonNull ItemNetIncomeTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(NetIncome netIncome) {

            binding.ivCategoryIcon.setImageResource(netIncome.getIconResId());
            binding.ivSubIcon.setImageResource(netIncome.getSubIconResId());
            binding.tvCategoryName.setText(netIncome.getName());
            binding.tvTotal.setText(NumberUtils.formatNumberWithComma((int) netIncome.getValue()));

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
