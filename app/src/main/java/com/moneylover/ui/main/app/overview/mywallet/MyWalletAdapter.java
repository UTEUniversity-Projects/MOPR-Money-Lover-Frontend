package com.moneylover.ui.main.app.overview.mywallet;

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

import com.bumptech.glide.Glide;
import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.data.model.MenuOption;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ItemWalletBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.custom.menu.OptionAdapter;
import com.moneylover.utils.NumberUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.ViewHolder> {

    private final Context context;
    private final List<WalletResponse> walletList;
    private final OnItemClickListener listener;

    public MyWalletAdapter(Context context, List<WalletResponse> walletList, OnItemClickListener listener) {
        this.context = context;
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
        WalletResponse item = walletList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return walletList != null ? walletList.size() : 0;
    }

//    public void clearSelection() {
//        for (WalletResponse wallet : walletList) {
//            wallet.setSelectedIcon(0);
//        }
//        notifyDataSetChanged();
//    }

    public void updateData(List<WalletResponse> newList) {
        this.walletList.clear();
        this.walletList.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemWalletBinding binding;
        private PopupWindow popupWindow;

        public ViewHolder(ItemWalletBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(WalletResponse walletResponse) {
            Glide.with(context).load(walletResponse.getIcon().getFileUrl()).into(binding.ivIcon);
            binding.tvName.setText(walletResponse.getName());
            binding.tvBalance.setText(NumberUtils.formatNumberWithComma(walletResponse.getBalance() != null ? walletResponse.getBalance() : BigDecimal.valueOf(0.0)));

//            if (item.getSelectedIcon() != 0) {
//                binding.ivGreenCircle.setImageResource(item.getSelectedIcon());
//            } else {
//                binding.ivGreenCircle.setImageResource(0);
//            }

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
                        new MenuOption(R.drawable.ic_edit, "Sửa", Constants.MENU_OPTION_TYPE_DEFAULT),
                        new MenuOption(R.drawable.ic_delete, "Xóa", Constants.MENU_OPTION_TYPE_DEFAULT)
                );

                OptionAdapter optionAdapter = new OptionAdapter(context, options, pos -> {
                    MenuOption selected = options.get(pos);
                    if ("Xóa".equals(selected.getTitle())) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION) {
                            listener.onItemDelete(position);
                        }
                    } else if ("Sửa".equals(selected.getTitle())) {
                        int position = getAdapterPosition();
                        if (listener != null && position != RecyclerView.NO_POSITION) {
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
                int[] location = new int[2];
                binding.ivOption.getLocationOnScreen(location);

                int popupWidth = popupView.getMeasuredWidth();
                popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                popupWidth = popupView.getMeasuredWidth();

                int xOffset = -popupWidth + binding.ivOption.getWidth() / 2;
                popupWindow.showAsDropDown(binding.ivOption, xOffset, 0);
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

