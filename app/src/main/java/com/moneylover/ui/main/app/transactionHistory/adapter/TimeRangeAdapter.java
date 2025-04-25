package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_range, parent, false);
        return new ViewHolder(view);
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

        // Chỉ gọi notify nếu thực sự thay đổi
        if (oldIndex != newIndex) {
            notifyItemChanged(oldIndex);
            notifyItemChanged(newIndex);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon, check;
        TextView label;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.ivIcon);
            label = itemView.findViewById(R.id.tvLabel);
            check = itemView.findViewById(R.id.ivCheck);
        }

        public void bind(int position) {
            label.setText(labels.get(position));
            icon.setImageResource(icons.get(position));
            check.setVisibility(position == selectedIndex ? View.VISIBLE : View.INVISIBLE);

            itemView.setOnClickListener(v -> {
                // Cập nhật selected index
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

