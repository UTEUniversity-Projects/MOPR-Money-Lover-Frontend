package com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.data.model.ExpenditureTransaction;
import com.moneylover.data.model.NetIncomeTransaction;
import com.moneylover.databinding.ItemExpenditureTransactionDateBinding;
import com.moneylover.databinding.ItemNetIncomeTransactionDateBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.adapter.ExpenditureTransactionItemAdapter;
import com.moneylover.utils.NavigationUtils;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class NetIncomeTransactionAdapter extends RecyclerView.Adapter<NetIncomeTransactionAdapter.ViewHolder> {

    private Context context;
    private List<NetIncomeTransaction> netIncomeTransactions;
    private OnItemClickListener listener;

    public NetIncomeTransactionAdapter(Context context, List<NetIncomeTransaction> netIncomeTransactions, OnItemClickListener listener) {
        this.context = context;
        this.netIncomeTransactions = netIncomeTransactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemNetIncomeTransactionDateBinding binding = ItemNetIncomeTransactionDateBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(netIncomeTransactions.get(position));
    }

    @Override
    public int getItemCount() {
        return netIncomeTransactions != null ? netIncomeTransactions.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemNetIncomeTransactionDateBinding binding;

        public ViewHolder(@NonNull ItemNetIncomeTransactionDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(NetIncomeTransaction netIncomeTransaction) {

            binding.tvDay.setText(netIncomeTransaction.getDay());
            binding.tvDayOfWeek.setText(netIncomeTransaction.getDateOfWeek().split("/")[0]);
            binding.tvMonthYear.setText(netIncomeTransaction.getDateOfWeek().split("/")[1]);
            binding.tvTotal.setText(NumberUtils.formatNumberWithComma(netIncomeTransaction.getTotal()));

            NetIncomeTransactionItemAdapter adapter = new NetIncomeTransactionItemAdapter(context, netIncomeTransaction.getNetIncomes(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    NavigationUtils.navigateToActivity((AppCompatActivity) context, TransactionHistoryDetailActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
                }

                @Override
                public void onItemDelete(int position) {

                }
            });
            binding.rcvNetIncomeTransaction.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            binding.rcvNetIncomeTransaction.setAdapter(adapter);

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
