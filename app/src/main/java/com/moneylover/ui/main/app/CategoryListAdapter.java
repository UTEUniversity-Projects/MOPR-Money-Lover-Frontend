package com.moneylover.ui.main.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moneylover.data.model.api.response.CategoryResponse;
import com.moneylover.databinding.ItemCategoryBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    private final Context context;
    private final List<CategoryResponse> categoryList;
    private final CategoryResponse selectedCategory;
    private final OnItemClickListener listener;

    public CategoryListAdapter(Context context, List<CategoryResponse> categoryList, CategoryResponse selectedCategory, OnItemClickListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        this.selectedCategory = selectedCategory;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryResponse category = categoryList.get(position);
        Glide.with(context).load(category.getIcon().getFileUrl()).into(holder.binding.ivCategoryIcon);
        holder.binding.tvCategoryName.setText(category.getName());

        if (selectedCategory != null && selectedCategory.getId().equals(category.getId())) {
            holder.binding.ivCheck.setVisibility(View.VISIBLE);
        } else {
            holder.binding.ivCheck.setVisibility(View.GONE);
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemCategoryBinding binding;
        public ViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
