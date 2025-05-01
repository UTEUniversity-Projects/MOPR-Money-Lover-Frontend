package com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.NetIncome;
import com.moneylover.databinding.ItemNetIncomeDetailDateBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class NetIncomeAdapter extends RecyclerView.Adapter<NetIncomeAdapter.ViewHolder> {

    private Context context;
    private final List<NetIncome> netIncomes;
    private final OnItemClickListener listener;

    public NetIncomeAdapter(Context context, List<NetIncome> netIncomes, OnItemClickListener listener) {
        this.context = context;
        this.netIncomes = netIncomes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NetIncomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemNetIncomeDetailDateBinding binding = ItemNetIncomeDetailDateBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NetIncomeAdapter.ViewHolder holder, int position) {
        holder.bind(netIncomes.get(position));
    }

    @Override
    public int getItemCount() {
        return netIncomes != null ? netIncomes.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNetIncomeDetailDateBinding binding;

        public ViewHolder(ItemNetIncomeDetailDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NetIncome netIncome) {
            binding.tvIncome.setText(NumberUtils.formatNumberWithComma(netIncome.getIncome()));
            binding.tvExpenditure.setText(NumberUtils.formatNumberWithComma(netIncome.getExpenditure()));
            binding.tvNetIncome.setText(NumberUtils.formatNumberWithComma(netIncome.getIncome() - netIncome.getExpenditure()));

            binding.getRoot().setOnClickListener(v -> {

                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });
        }
    }

}
