package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.databinding.ItemTimeRangeBinding;

import java.util.List;

import timber.log.Timber;

public class TimeRangeAdapter extends RecyclerView.Adapter<TimeRangeAdapter.ViewHolder> {

    public interface OnTimeRangeClickListener {
        void onClick(int position, String label);
    }

    private final List<String> labels;
    private final List<Integer> icons;
    private int selectedIndex;
    private final OnTimeRangeClickListener listener;

    public TimeRangeAdapter(List<String> labels, List<Integer> icons, int selectedIndex, OnTimeRangeClickListener listener) {
        this.labels = labels;
        this.icons = icons;
        this.selectedIndex = selectedIndex;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTimeRangeBinding binding = ItemTimeRangeBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }

    public void setSelectedIndex(int newIndex) {
        int oldIndex = selectedIndex;
        selectedIndex = newIndex;
        Timber.tag("TimeRangeAdapter").d("Selected index changed from " + oldIndex + " to " + newIndex);

        if (oldIndex != newIndex) {
            notifyItemChanged(oldIndex);
            notifyItemChanged(newIndex);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTimeRangeBinding binding;

        public ViewHolder(@NonNull ItemTimeRangeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.tvLabel.setText(labels.get(position));
            binding.ivIcon.setImageResource(icons.get(position));
            binding.ivCheck.setVisibility(position == selectedIndex ? View.VISIBLE : View.INVISIBLE);

            itemView.setOnClickListener(v -> {
                if (position != selectedIndex) {
                    setSelectedIndex(position);
                }
                if (listener != null) {
                    listener.onClick(position, labels.get(position));
                }
            });
        }
    }
}
