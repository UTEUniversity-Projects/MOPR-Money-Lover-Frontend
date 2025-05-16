package com.moneylover.ui.main.app.transactionHistory.viewReport.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.data.model.ReportGroupTransaction;
import com.moneylover.databinding.ItemReportGroupTransactionDateBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailActivity;
import com.moneylover.utils.NavigationUtils;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class ReportGroupTransactionAdapter extends RecyclerView.Adapter<ReportGroupTransactionAdapter.ViewHolder> {

    private Context context;
    private List<ReportGroupTransaction> reportGroupTransactions;
    private OnItemClickListener listener;

    public ReportGroupTransactionAdapter(Context context, List<ReportGroupTransaction> reportGroupTransactions, OnItemClickListener listener) {
        this.context = context;
        this.reportGroupTransactions = reportGroupTransactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemReportGroupTransactionDateBinding binding = ItemReportGroupTransactionDateBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(reportGroupTransactions.get(position));
    }

    @Override
    public int getItemCount() {
        return reportGroupTransactions != null ? reportGroupTransactions.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemReportGroupTransactionDateBinding binding;

        public ViewHolder(@NonNull ItemReportGroupTransactionDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(ReportGroupTransaction reportGroupTransaction) {

            binding.tvDay.setText(reportGroupTransaction.getDay());
            binding.tvDayOfWeek.setText(reportGroupTransaction.getDateOfWeek().split("/")[0]);
            binding.tvMonthYear.setText(reportGroupTransaction.getDateOfWeek().split("/")[1]);
            binding.tvTotal.setText(NumberUtils.formatNumberWithComma(reportGroupTransaction.getTotal()));

            ReportGroupTransactionItemAdapter adapter = new ReportGroupTransactionItemAdapter(context, reportGroupTransaction.getReportGroups(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    NavigationUtils.navigateToActivity((AppCompatActivity) context, TransactionHistoryDetailActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
                }

                @Override
                public void onItemDelete(int position) {

                }
            });
            binding.rcvReportGroupTransaction.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            binding.rcvReportGroupTransaction.setAdapter(adapter);

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
