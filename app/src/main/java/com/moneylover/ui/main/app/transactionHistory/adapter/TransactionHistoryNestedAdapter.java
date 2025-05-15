package com.moneylover.ui.main.app.transactionHistory.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.data.model.MenuOption;
import com.moneylover.data.model.TransactionHistoryWallet;
import com.moneylover.databinding.ItemTransactionHistoryDateBinding;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.custom.menu.OptionAdapter;

import java.util.Arrays;
import java.util.List;

public class TransactionHistoryNestedAdapter extends RecyclerView.Adapter<TransactionHistoryNestedAdapter.NestedViewHolder> {
    private final Context context;
    private final List<TransactionHistoryWallet> walletList;
    private final OnItemClickListener listener;

    public TransactionHistoryNestedAdapter(Context context, List<TransactionHistoryWallet> walletList, OnItemClickListener listener) {
        this.walletList = walletList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionHistoryDateBinding binding = ItemTransactionHistoryDateBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new NestedViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.bind(walletList.get(position), context, listener);
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public static class NestedViewHolder extends RecyclerView.ViewHolder {
        private final ItemTransactionHistoryDateBinding binding;
        private PopupWindow popupWindow;
        private boolean isPopupShowing = false;
        private boolean isAnimating = false;
        private boolean isPopupReady = false;

        public NestedViewHolder(ItemTransactionHistoryDateBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TransactionHistoryWallet wallet, Context context, OnItemClickListener listener) {
            binding.tvTransactionDate.setText(wallet.getDate());
            binding.tvTransactionAmount.setText(wallet.isIncome() ? wallet.getAmount() : "-" + wallet.getAmount());
            binding.tvTransactionAmount.setTextColor(
                    binding.getRoot().getContext().getColor(
                            wallet.isIncome() ? R.color.deep_sky_blue : R.color.red
                    )
            );

            binding.getRoot().setOnLongClickListener(v -> {
                if (isPopupShowing || isAnimating) return false;
                showCustomPopupMenuWithAnimation(context, v);
                return true;
            });

            binding.getRoot().setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(pos);
                }
            });
        }

        private void showCustomPopupMenuWithAnimation(Context context, View anchor) {
            isPopupShowing = true;
            isAnimating = true;
            isPopupReady = false;

            View popupView = LayoutInflater.from(anchor.getContext())
                    .inflate(R.layout.menu_popup_layout, null);
            popupView.setScaleX(0f);
            popupView.setScaleY(0f);

            RecyclerView rvMenu = popupView.findViewById(R.id.rcvPopupMenu);

            List<MenuOption> menuOptions = Arrays.asList(
                    new MenuOption(R.drawable.ic_edit, "Sửa", Constants.MENU_OPTION_TYPE_DEFAULT),
                    new MenuOption(R.drawable.ic_copy, context.getString(R.string.copy), Constants.MENU_OPTION_TYPE_DEFAULT),
                    new MenuOption(R.drawable.ic_delete, context.getString(R.string.delete), Constants.MENU_OPTION_TYPE_DEFAULT)
            );

            popupWindow = new PopupWindow(
                    popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true
            );
            popupWindow.setOutsideTouchable(false);
            popupWindow.setFocusable(true);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.setElevation(dpToPx(context, 6));
            }

            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            int anchorX = location[0];
            int anchorY = location[1];

            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popupWidth = popupView.getMeasuredWidth();

            popupWindow.showAtLocation(anchor,
                    Gravity.NO_GRAVITY,
                    anchorX + anchor.getWidth() - popupWidth,
                    anchorY
            );

            OptionAdapter adapter = new OptionAdapter(context, menuOptions, position -> {
                MenuOption selected = menuOptions.get(position);
                Toast.makeText(context, selected.getTitle(), Toast.LENGTH_SHORT).show();
                animatePopupDismiss(popupView);
            });

            rvMenu.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            rvMenu.setAdapter(adapter);

            animatePopup(popupView);

            popupWindow.setTouchInterceptor((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!isPopupReady) return true;

                    int[] popupLocation = new int[2];
                    popupView.getLocationOnScreen(popupLocation);
                    int left = popupLocation[0];
                    int top = popupLocation[1];
                    int right = left + popupView.getWidth();
                    int bottom = top + popupView.getHeight();

                    float x = event.getRawX();
                    float y = event.getRawY();

                    boolean isOutside = x < left || x > right || y < top || y > bottom;
                    if (isOutside && !isAnimating) {
                        animatePopupDismiss(popupView);
                        return true;
                    }
                }
                return false;
            });

            popupWindow.setOnDismissListener(() -> {
                isPopupShowing = false;
                isAnimating = false;
                isPopupReady = false;
            });
        }

        private int dpToPx(Context context, int dp) {
            return (int) (dp * context.getResources().getDisplayMetrics().density);
        }

        private void animatePopup(View popupView) {
            popupView.setPivotY(0f);
            popupView.setPivotX(popupView.getWidth());

            ValueAnimator scaleX = ValueAnimator.ofFloat(0f, 1f);
            scaleX.addUpdateListener(animation -> popupView.setScaleX((float) animation.getAnimatedValue()));

            ValueAnimator scaleY = ValueAnimator.ofFloat(0f, 1f);
            scaleY.addUpdateListener(animation -> popupView.setScaleY((float) animation.getAnimatedValue()));

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(new OvershootInterpolator());
            animatorSet.playSequentially(scaleX.setDuration(200), scaleY.setDuration(350));
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimating = false;
                    isPopupReady = true;
                    popupWindow.setOutsideTouchable(true);
                }
            });
            animatorSet.start();
        }

        private void animatePopupDismiss(View popupView) {
            if (popupWindow == null || !popupWindow.isShowing()) return;

            isAnimating = true;
            isPopupReady = false;

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
                    isPopupShowing = false;
                    isAnimating = false;
                    isPopupReady = false;
                }
            });
            animatorSet.start();
        }
    }
}
