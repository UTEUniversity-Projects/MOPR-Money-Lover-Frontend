package com.moneylover.ui.main.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moneylover.data.model.api.response.EventResponse;
import com.moneylover.databinding.ItemEventBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private final Context context;
    private final List<EventResponse> eventResponses;
    private final OnItemClickListener listener;

    public EventAdapter(Context context, List<EventResponse> eventResponses, OnItemClickListener listener) {
        this.context = context;
        this.eventResponses = eventResponses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder holder, int position) {
        EventResponse eventResponse = eventResponses.get(position);
        holder.binding.tvTitle.setText(eventResponse.getName());
        holder.binding.tvDesc.setText(eventResponse.getDescription());
        Glide.with(context).load(eventResponse.getIcon().getFileUrl()).into(holder.binding.ivEventIcon);
        holder.binding.getRoot().setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventResponses != null ? eventResponses.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemEventBinding binding;
        public ViewHolder(@NonNull ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
