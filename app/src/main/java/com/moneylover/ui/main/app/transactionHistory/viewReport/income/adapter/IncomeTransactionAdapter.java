package com.moneylover.ui.main.app.transactionHistory.viewReport.income.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.data.model.IncomeTransaction;
import com.moneylover.databinding.ItemIncomeTransactionDateBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailActivity;
import com.moneylover.utils.NavigationUtils;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class IncomeTransactionAdapter extends RecyclerView.Adapter<IncomeTransactionAdapter.ViewHolder> {

    private Context context;
    private List<IncomeTransaction> incomeTransactions;
    private OnItemClickListener listener;

    public IncomeTransactionAdapter(Context context, List<IncomeTransaction> incomeTransactions, OnItemClickListener listener) {
        this.context = context;
        this.incomeTransactions = incomeTransactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemIncomeTransactionDateBinding binding = ItemIncomeTransactionDateBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(incomeTransactions.get(position));
    }

    @Override
    public int getItemCount() {
        return incomeTransactions != null ? incomeTransactions.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemIncomeTransactionDateBinding binding;

        public ViewHolder(@NonNull ItemIncomeTransactionDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(IncomeTransaction incomeTransaction) {

            binding.tvDay.setText(incomeTransaction.getDay());
            binding.tvDayOfWeek.setText(incomeTransaction.getDateOfWeek().split("/")[0]);
            binding.tvMonthYear.setText(incomeTransaction.getDateOfWeek().split("/")[1]);
            binding.tvTotal.setText(NumberUtils.formatNumberWithComma(incomeTransaction.getTotal()));

            IncomeTransactionItemAdapter adapter = new IncomeTransactionItemAdapter(context, incomeTransaction.getIncomes(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    NavigationUtils.navigateToActivity((AppCompatActivity) context, TransactionHistoryDetailActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
                }

                @Override
                public void onItemDelete(int position) {

                }
            });
            binding.rcvIncomeTransaction.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            binding.rcvIncomeTransaction.setAdapter(adapter);

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
