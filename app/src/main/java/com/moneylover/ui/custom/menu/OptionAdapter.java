package com.moneylover.ui.custom.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.data.model.MenuOption;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private final Context context;
    private final List<MenuOption> options;
    private final OnOptionClickListener listener;

    public interface OnOptionClickListener {
        void onOptionSelected(int position);
    }

    public OptionAdapter(Context context, List<MenuOption> options, OnOptionClickListener listener) {
        this.context = context;
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
        MenuOption menuOption = options.get(position);

        holder.optionText.setText(menuOption.getTitle());

        if (menuOption.isSelected() && menuOption.getType() == Constants.MENU_OPTION_TYPE_DATE_TIME) {
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(R.drawable.ic_check);
        } else if (menuOption.getIcon() != 0 && menuOption.getType() == Constants.MENU_OPTION_TYPE_DEFAULT) {
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(menuOption.getIcon());
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
