package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.data.model.MenuOption;
import com.moneylover.data.model.Wallet;
import com.moneylover.databinding.ItemWalletBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.custom.menu.OptionAdapter;

import java.util.Arrays;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {

    private final List<Wallet> walletList;
    private final OnItemClickListener listener;

    public WalletAdapter(List<Wallet> walletList, OnItemClickListener listener) {
        this.walletList = walletList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWalletBinding binding = ItemWalletBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wallet item = walletList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return walletList != null ? walletList.size() : 0;
    }

    public void clearSelection() {
        for (Wallet wallet : walletList) {
            wallet.setSelectedIcon(0);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemWalletBinding binding;
        private PopupWindow popupWindow;

        public ViewHolder(ItemWalletBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(Wallet item) {
            binding.ivIcon.setImageResource(item.getIcon());
            binding.tvName.setText(item.getName());
            binding.tvBalance.setText(String.valueOf(item.getBalance()));

            if (item.getSelectedIcon() != 0) {
                binding.ivGreenCircle.setImageResource(item.getSelectedIcon());
            } else {
                binding.ivGreenCircle.setImageResource(0);
            }

            binding.getRoot().setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });

            binding.ivOption.setOnClickListener(v -> {
                Context context = v.getContext();
                View popupView = LayoutInflater.from(context).inflate(R.layout.menu_popup_layout, null);
                RecyclerView recyclerView = popupView.findViewById(R.id.rcvPopupMenu);

                List<MenuOption> options = Arrays.asList(
                        new MenuOption(R.drawable.ic_two_ways, "Chuyển tiền đến ví khác"),
                        new MenuOption(R.drawable.ic_edit, "Sửa"),
                        new MenuOption(R.drawable.ic_delete, "Xóa")
                );

                OptionAdapter optionAdapter = new OptionAdapter(options, pos -> {
                    MenuOption selected = options.get(pos);
                    Toast.makeText(context, "Selected: " + selected.getTitle(), Toast.LENGTH_SHORT).show();
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
                popupWindow.showAsDropDown(binding.ivOption, -16, 0);
            });
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

