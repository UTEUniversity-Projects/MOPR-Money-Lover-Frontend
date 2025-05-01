package com.moneylover.ui.main.app.transactionHistory.viewReport.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.ReportGroup;
import com.moneylover.databinding.ItemReportGroupTransactionBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class ReportGroupTransactionItemAdapter extends RecyclerView.Adapter<ReportGroupTransactionItemAdapter.ViewHolder> {

    private Context context;
    private List<ReportGroup> reportGroups;
    private OnItemClickListener listener;

    public ReportGroupTransactionItemAdapter(Context context, List<ReportGroup> reportGroups, OnItemClickListener listener) {
        this.context = context;
        this.reportGroups = reportGroups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemReportGroupTransactionBinding binding = ItemReportGroupTransactionBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(reportGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return reportGroups != null ? reportGroups.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemReportGroupTransactionBinding binding;

        public ViewHolder(@NonNull ItemReportGroupTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(ReportGroup reportGroup) {

            binding.ivCategoryIcon.setImageResource(reportGroup.getIconResId());
            binding.ivSubIcon.setImageResource(reportGroup.getSubIconResId());
            binding.tvCategoryName.setText(reportGroup.getName());
            binding.tvTotal.setText(NumberUtils.formatNumberWithComma((int) reportGroup.getValue()));

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
