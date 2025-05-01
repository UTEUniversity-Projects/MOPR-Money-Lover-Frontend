package com.moneylover.ui.main.app.transactionHistory.viewReport.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.ReportGroupType;
import com.moneylover.databinding.ItemReportGroupTypeBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.util.List;

public class ViewReportByGroupListOptionAdapter extends RecyclerView.Adapter<ViewReportByGroupListOptionAdapter.ViewHolder> {

    private Context context;
    private List<ReportGroupType> reportGroupTypes;
    private OnItemClickListener listener;
    private ReportGroupType selectedGroupType;

    public ViewReportByGroupListOptionAdapter(Context context, List<ReportGroupType> list, ReportGroupType selectedGroupType, OnItemClickListener listener) {
        this.context = context;
        this.reportGroupTypes = list;
        this.selectedGroupType = selectedGroupType;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemReportGroupTypeBinding binding = ItemReportGroupTypeBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(reportGroupTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return reportGroupTypes != null ? reportGroupTypes.size() : 0;
    }

    public void updateData(List<ReportGroupType> newList) {
        this.reportGroupTypes = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemReportGroupTypeBinding binding;

        public ViewHolder(@NonNull ItemReportGroupTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ReportGroupType reportGroupType) {
            binding.ivGroupType.setImageResource(reportGroupType.getIcon());
            binding.tvName.setText(reportGroupType.getName());
            binding.tvWallet.setText(reportGroupType.getWallet());

            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });

            if (selectedGroupType != null &&
                    selectedGroupType.getName().equals(reportGroupType.getName())) {
                binding.ivCheck.setVisibility(View.VISIBLE);
            } else {
                binding.ivCheck.setVisibility(View.GONE);
            }

        }

    }

}
