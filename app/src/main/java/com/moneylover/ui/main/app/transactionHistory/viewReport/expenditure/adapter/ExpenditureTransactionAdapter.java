package com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.adapter;

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
import com.moneylover.databinding.ItemExpenditureTransactionDateBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.app.transactionHistory.TransactionHistoryDetailActivity;
import com.moneylover.utils.NavigationUtils;
import com.moneylover.utils.NumberUtils;

import java.util.List;

public class ExpenditureTransactionAdapter extends RecyclerView.Adapter<ExpenditureTransactionAdapter.ViewHolder> {

    private Context context;
    private List<ExpenditureTransaction> expenditureTransactions;
    private OnItemClickListener listener;

    public ExpenditureTransactionAdapter(Context context, List<ExpenditureTransaction> expenditureTransactions, OnItemClickListener listener) {
        this.context = context;
        this.expenditureTransactions = expenditureTransactions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpenditureTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemExpenditureTransactionDateBinding binding = ItemExpenditureTransactionDateBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureTransactionAdapter.ViewHolder holder, int position) {
        holder.bind(expenditureTransactions.get(position));
    }

    @Override
    public int getItemCount() {
        return expenditureTransactions != null ? expenditureTransactions.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemExpenditureTransactionDateBinding binding;

        public ViewHolder(@NonNull ItemExpenditureTransactionDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(ExpenditureTransaction expenditureTransaction) {

            binding.tvDay.setText(expenditureTransaction.getDay());
            binding.tvDayOfWeek.setText(expenditureTransaction.getDateOfWeek().split("/")[0]);
            binding.tvMonthYear.setText(expenditureTransaction.getDateOfWeek().split("/")[1]);
            binding.tvTotal.setText(NumberUtils.formatNumberWithComma(expenditureTransaction.getTotal()));

            ExpenditureTransactionItemAdapter adapter = new ExpenditureTransactionItemAdapter(context, expenditureTransaction.getExpenditures(), new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    NavigationUtils.navigateToActivity((AppCompatActivity) context, TransactionHistoryDetailActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
                }

                @Override
                public void onItemDelete(int position) {

                }
            });
            binding.rcvExpenditureTransactionItem.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            binding.rcvExpenditureTransactionItem.setAdapter(adapter);

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
