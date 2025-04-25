package com.moneylover.ui.main.app.transactionHistory;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.MenuOption;
import com.moneylover.data.model.Wallet;
import com.moneylover.databinding.FragmentTransactionHistoryBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.custom.menu.OptionAdapter;
import com.moneylover.ui.main.app.transactionHistory.adapter.TransactionHistoryViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionHistoryFragment extends BaseFragment<FragmentTransactionHistoryBinding, TransactionHistoryViewModel> {

    private Wallet selectedWallet = new Wallet(R.drawable.bg_green_circle, R.drawable.ic_wallet_2, "Tổng cộng", -4156598);

    private final ActivityResultLauncher<Intent> walletLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Wallet wallet = (Wallet) result.getData().getSerializableExtra("wallet");
                    if (wallet != null) {
                        selectedWallet = wallet;
                        binding.ivWalletIcon.setImageResource(wallet.getIcon());
                        binding.tvWalletName.setText(wallet.getName());

                    }

                }
            });

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction_history;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        setupTabLayout();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void onMenuClick() {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.menu_popup_layout, null);
        RecyclerView rvMenu = popupView.findViewById(R.id.rcvPopupMenu);

        List<MenuOption> menuOptions = Arrays.asList(
                new MenuOption(getString(R.string.time_line)),
                new MenuOption(getString(R.string.view_by_group)),
                new MenuOption(getString(R.string.view_by_transaction)),
                new MenuOption(getString(R.string.change_balance))
        );

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(dpToPx(6));
        }

        int[] location = new int[2];
        binding.ivMenu.getLocationOnScreen(location);
        int y = getStatusBarHeight() + dpToPx(4);
        popupWindow.showAtLocation(binding.getRoot(), Gravity.TOP | Gravity.END, 16, y);

        OptionAdapter adapter = new OptionAdapter(menuOptions, position -> {
            MenuOption selected = menuOptions.get(position);
            handleMenuSelection(selected.getTitle());
            popupWindow.dismiss();
        });

        rvMenu.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rvMenu.setAdapter(adapter);

    }

    private void handleMenuSelection(String title) {
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.menu_popup_layout, null);
        RecyclerView rvMenu = popupView.findViewById(R.id.rcvPopupMenu);

        List<MenuOption> menuOptions = Arrays.asList(
                new MenuOption(R.drawable.ic_check, "Ngày"),
                new MenuOption("Tuần"),
                new MenuOption("Tháng"),
                new MenuOption("Quý"),
                new MenuOption("Năm")
        );

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setWidth(500);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(dpToPx(6));
        }

        int[] location = new int[2];
        binding.ivMenu.getLocationOnScreen(location);
        int y = getStatusBarHeight() + dpToPx(4);
        popupWindow.showAtLocation(binding.getRoot(), Gravity.TOP | Gravity.END, 16, y);

        OptionAdapter adapter = new OptionAdapter(menuOptions, position -> {
            MenuOption selected = menuOptions.get(position);
            handleMenuSelection(selected.getTitle());
            popupWindow.dismiss();
        });

        rvMenu.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rvMenu.setAdapter(adapter);
    }

    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? getResources().getDimensionPixelSize(resourceId) : 0;
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    public void onWalletClick() {
        Intent intent = new Intent(getActivity(), WalletActivity.class);
        intent.putExtra("selected_wallet", selectedWallet);
        walletLauncher.launch(intent);
    }

    public void setupTabLayout() {
        List<String> months = generateMonthList();
        TabLayout tabLayout = binding.tabLayout;
        tabLayout.removeAllTabs();

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < months.size(); i++) {
            fragments.add(new TransactionHistoryListFragment());
        }

        TransactionHistoryViewPagerAdapter adapter = new TransactionHistoryViewPagerAdapter(this, fragments);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        binding.viewPager.setOffscreenPageLimit(4);

        new TabLayoutMediator(tabLayout, binding.viewPager, (tab, position) -> {
            View customView = LayoutInflater.from(requireContext()).inflate(R.layout.item_tab_month, null);
            TextView tabText = customView.findViewById(R.id.tabText);
            tabText.setText(months.get(position));
            tab.setCustomView(customView);
        }).attach();

        int defaultTabIndex = 6;
        binding.viewPager.setCurrentItem(defaultTabIndex, false);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                scrollToCenter(tabLayout, tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabLayout.post(() -> {
            TabLayout.Tab defaultTab = tabLayout.getTabAt(defaultTabIndex);
            if (defaultTab != null) {
                scrollToCenter(tabLayout, defaultTab);
            }
        });
    }

    private void scrollToCenter(TabLayout tabLayout, TabLayout.Tab tab) {
        View tabView = tab.view;
        if (tabView != null) {
            int scrollX = tabView.getLeft() + tabView.getWidth() / 2 - tabLayout.getWidth() / 2;
            tabLayout.post(() -> tabLayout.smoothScrollTo(scrollX, 0));
        }
    }

    private List<String> generateMonthList() {
        List<String> months = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendar.add(Calendar.MONTH, -6);

        for (int i = 0; i < 13; i++) {
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            months.add(String.format(Locale.getDefault(), "%02d/%d", month, year));
            calendar.add(Calendar.MONTH, 1);
        }

        months.add("THÁNG TRƯỚC");

        return months;
    }

}