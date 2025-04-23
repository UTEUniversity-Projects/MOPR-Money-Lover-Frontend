package com.moneylover.ui.custom.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.data.model.MenuOption;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private final List<MenuOption> options;
    private final OnOptionClickListener listener;

    public interface OnOptionClickListener {
        void onOptionSelected(int position);
    }

    public OptionAdapter(List<MenuOption> options, OnOptionClickListener listener) {
        this.options = options;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_popup_item, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        MenuOption item = options.get(position);

        holder.optionText.setText(item.getTitle());

        if (item.isSelected()) {
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(R.drawable.ic_check);
        } else if (item.getIcon() != 0) {
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(item.getIcon());
        } else {
            holder.icon.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            for (int i = 0; i < options.size(); i++) {
                options.get(i).setSelected(i == position);
            }
            notifyDataSetChanged();
            listener.onOptionSelected(position);
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    static class OptionViewHolder extends RecyclerView.ViewHolder {
        TextView optionText;
        ImageView icon;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            optionText = itemView.findViewById(R.id.tvOption);
            icon = itemView.findViewById(R.id.ivIcon); // chỉ dùng 1 ImageView duy nhất
        }
    }
}
