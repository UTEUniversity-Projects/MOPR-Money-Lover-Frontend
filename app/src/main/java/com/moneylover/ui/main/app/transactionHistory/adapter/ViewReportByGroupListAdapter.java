package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.data.model.ReportGroupType;
import com.moneylover.databinding.ItemReportGroupTypeBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.util.List;

public class ViewReportByGroupListAdapter extends RecyclerView.Adapter<ViewReportByGroupListAdapter.ViewHolder> {

    private Context context;
    private List<ReportGroupType> reportGroupTypes;
    private OnItemClickListener onItemClickListener;

    public ViewReportByGroupListAdapter(Context context, List<ReportGroupType> reportGroupTypes, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.reportGroupTypes = reportGroupTypes;
        this.onItemClickListener = onItemClickListener;
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
        private ItemReportGroupTypeBinding binding;

        public ViewHolder(@NonNull ItemReportGroupTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ReportGroupType reportGroupType) {
            binding.ivGroupType.setImageResource(reportGroupType.getIcon());
            binding.tvName.setText(reportGroupType.getName());
            binding.tvWallet.setText(reportGroupType.getWallet());
        }

    }

}
