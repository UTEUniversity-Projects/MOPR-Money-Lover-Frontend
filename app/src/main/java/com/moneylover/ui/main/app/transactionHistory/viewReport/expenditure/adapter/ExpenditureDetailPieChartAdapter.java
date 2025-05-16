package com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.Expenditure;
import com.moneylover.databinding.ItemExpenditurePieChartBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExpenditureDetailPieChartAdapter extends RecyclerView.Adapter<ExpenditureDetailPieChartAdapter.ViewHolder> {

    private final Context context;
    private final List<Expenditure> expenditures;
    private final OnItemClickListener listener;

    public ExpenditureDetailPieChartAdapter(Context context, List<Expenditure> expenditures, OnItemClickListener listener) {
        this.context = context;
        this.expenditures = expenditures;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemExpenditurePieChartBinding binding = ItemExpenditurePieChartBinding.inflate(inflater, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemExpenditurePieChartBinding binding;

        public ViewHolder(@NonNull ItemExpenditurePieChartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Expenditure expenditure) {
            binding.ivExpenditure.setImageResource(expenditure.getIconResId());
            binding.tvName.setText(expenditure.getName());
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            numberFormat.setMaximumFractionDigits(0);
            String formatted = numberFormat.format(expenditure.getValue());

            binding.tvExpenditure.setText(formatted);

            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
