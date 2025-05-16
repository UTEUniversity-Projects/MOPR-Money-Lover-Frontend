package com.moneylover.ui.main.app.overview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moneylover.R;
import com.moneylover.data.model.api.response.BillResponse;
import com.moneylover.databinding.ItemLatestTransactionBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.utils.NumberUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LatestTransactionAdapter extends RecyclerView.Adapter<LatestTransactionAdapter.ViewHolder> {

    private final Context context;
    private List<BillResponse> transactionList;
    private final OnItemClickListener listener;

    public LatestTransactionAdapter(Context context, List<BillResponse> transactionList, OnItemClickListener listener) {
        this.context = context;
        this.transactionList = transactionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLatestTransactionBinding binding = ItemLatestTransactionBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillResponse billResponse = transactionList.get(position);
        holder.bind(billResponse);
    }

    @Override
    public int getItemCount() {
        return transactionList != null ? transactionList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLatestTransactionBinding binding;

        public ViewHolder(@NonNull ItemLatestTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(position);
                }
            });
        }

        public void bind(BillResponse billResponse) {
            binding.tvCategoryName.setText(billResponse.getCategory().getName());
            binding.tvDate.setText(formatIsoDateToVietnamese(billResponse.getDate()));
            binding.tvAmount.setText(NumberUtils.formatNumberWithComma(billResponse.getAmount()));
            if (billResponse.getCategory().getIsExpense()) {
                binding.tvAmount.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                binding.tvAmount.setTextColor(context.getResources().getColor(R.color.deep_sky_blue));
            }
            Glide.with(context)
                    .load(billResponse.getCategory().getIcon().getFileUrl())
                    .into(binding.ivCategoryIcon);
            Glide.with(context).load(billResponse.getWallet().getIcon().getFileUrl()).into(binding.ivSubIcon);
        }
    }

    public String formatIsoDateToVietnamese(String isoDateStr) {
        try {
            Instant instant = Instant.parse(isoDateStr);
            Date date = Date.from(instant);

            Locale locale = new Locale("vi", "VN");
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy", locale);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return isoDateStr;
        }
    }

    // Add these methods to your LatestTransactionAdapter class

    /**
     * Clear all data from the adapter
     */
    public void clearData() {
        if (transactionList != null) {
            transactionList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Update adapter with new bill data
     * @param newBills List of new bill data
     */
    public void updateData(List<BillResponse> newBills) {
        if (this.transactionList == null) {
            this.transactionList = new ArrayList<>();
        } else {
            this.transactionList.clear();
        }

        if (newBills != null) {
            this.transactionList.addAll(newBills);
        }

        notifyDataSetChanged();
    }
}
