package com.moneylover.ui.main.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.data.model.MenuOption;
import com.moneylover.data.model.api.response.TagResponse;
import com.moneylover.databinding.ItemTagBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.custom.menu.OptionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    public interface OnTagSelectListener {
        void onSelectionChanged(boolean hasSelection);
    }

    private final Context context;
    private final List<TagResponse> tagList;
    private final OnItemClickListener listener;
    private final OnTagSelectListener tagSelectListener;
    private final List<TagResponse> selectedTags = new ArrayList<>();

    public TagAdapter(Context context, List<TagResponse> tagList,
                      OnItemClickListener listener, OnTagSelectListener tagSelectListener,
                      List<TagResponse> preSelectedTags) {
        this.context = context;
        this.tagList = tagList;
        this.listener = listener;
        this.tagSelectListener = tagSelectListener;

        if (preSelectedTags != null) {
            selectedTags.addAll(preSelectedTags);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTagBinding binding = ItemTagBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(tagList.get(position));
    }

    @Override
    public int getItemCount() {
        return tagList != null ? tagList.size() : 0;
    }

    public List<TagResponse> getSelectedTags() {
        return new ArrayList<>(selectedTags);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemTagBinding binding;
        private PopupWindow popupWindow;

        public ViewHolder(ItemTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TagResponse tagResponse) {
            binding.tvTagName.setText(tagResponse.getName());

            // Highlight tag nếu được chọn
            boolean isSelected = selectedTags.contains(tagResponse);
            binding.llTagItem.setBackgroundResource(
                    isSelected ? R.drawable.bg_tag_selected : R.drawable.bg_tag_unselected
            );

            // Bấm chọn tag
            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (selectedTags.contains(tagResponse)) {
                        selectedTags.remove(tagResponse);
                    } else {
                        selectedTags.add(tagResponse);
                    }
                    notifyItemChanged(position);

                    // Gọi callback cập nhật nút CHỌN
                    if (tagSelectListener != null) {
                        tagSelectListener.onSelectionChanged(!selectedTags.isEmpty());
                    }
                }
            });

            // Xử lý nút option (sửa / xoá)
            binding.ivOption.setOnClickListener(v -> {
                showOptionPopup(tagResponse);
            });
        }

        private void showOptionPopup(TagResponse tagResponse) {
            View popupView = LayoutInflater.from(context).inflate(R.layout.menu_popup_layout, null);
            RecyclerView recyclerView = popupView.findViewById(R.id.rcvPopupMenu);

            List<MenuOption> options = Arrays.asList(
                    new MenuOption(R.drawable.ic_edit, "Sửa", Constants.MENU_OPTION_TYPE_DEFAULT),
                    new MenuOption(R.drawable.ic_delete, "Xóa", Constants.MENU_OPTION_TYPE_DEFAULT)
            );

            OptionAdapter optionAdapter = new OptionAdapter(context, options, pos -> {
                MenuOption selected = options.get(pos);
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    if ("Xóa".equals(selected.getTitle())) {
                        listener.onItemDelete(position);
                    } else if ("Sửa".equals(selected.getTitle())) {
                        listener.onItemClick(position);
                    }
                }
                animatePopupDismiss(popupView);
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(optionAdapter);

            popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
            );

            popupWindow.setElevation(8f);
            popupWindow.setOutsideTouchable(true);

            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popupWidth = popupView.getMeasuredWidth();
            int xOffset = -popupWidth + binding.ivOption.getWidth() / 2;

            popupWindow.showAsDropDown(binding.ivOption, xOffset, 0);
        }

        private void animatePopupDismiss(View popupView) {
            if (popupWindow == null || !popupWindow.isShowing()) return;

            popupView.setPivotY(0f);
            popupView.setPivotX(popupView.getWidth());

            ValueAnimator scaleY = ValueAnimator.ofFloat(1f, 0f);
            scaleY.setDuration(150);
            scaleY.addUpdateListener(animation -> popupView.setScaleY((float) animation.getAnimatedValue()));

            ValueAnimator scaleX = ValueAnimator.ofFloat(1f, 0f);
            scaleX.setDuration(150);
            scaleX.addUpdateListener(animation -> popupView.setScaleX((float) animation.getAnimatedValue()));

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(scaleY, scaleX);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    popupWindow.dismiss();
                }
            });
            animatorSet.start();
        }
    }
}
