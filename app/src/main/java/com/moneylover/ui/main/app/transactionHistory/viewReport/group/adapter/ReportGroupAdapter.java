package com.moneylover.ui.main.app.transactionHistory.viewReport.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.ReportGroup;
import com.moneylover.databinding.ItemReportGroupBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class ReportGroupAdapter extends RecyclerView.Adapter<ReportGroupAdapter.ViewHolder> {

    private final Context context;
    private final List<ReportGroup> reportGroups;
    private final OnItemClickListener listener;

    public ReportGroupAdapter(Context context, List<ReportGroup> reportGroups, OnItemClickListener listener) {
        this.context = context;
        this.reportGroups = reportGroups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReportGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemReportGroupBinding binding = ItemReportGroupBinding.inflate(inflater, parent, false);
        return new ReportGroupAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportGroupAdapter.ViewHolder holder, int position) {
        holder.bind(reportGroups.get(position));
    }

    @Override
    public int getItemCount() {
        return reportGroups != null ? reportGroups.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemReportGroupBinding binding;

        public ViewHolder(@NonNull ItemReportGroupBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ReportGroup reportGroup) {
            binding.tvDateRange.setText(reportGroup.getDate());
            binding.tvValue.setText(NumberUtils.formatNumberWithComma((int) reportGroup.getValue()));

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
