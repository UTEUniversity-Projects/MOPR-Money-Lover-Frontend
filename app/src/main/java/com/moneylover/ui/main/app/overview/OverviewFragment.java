package com.moneylover.ui.main.app.overview;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointD;
import com.google.android.material.tabs.TabLayout;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.LatestTransaction;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.FragmentOverviewBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.custom.tooltips.ToolTip;
import com.moneylover.ui.custom.tooltips.ToolTipsManager;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.ui.main.app.overview.mywallet.MyWalletActivity;
import com.moneylover.utils.NavigationUtils;
import com.moneylover.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class OverviewFragment extends BaseFragment<FragmentOverviewBinding, OverviewViewModel> {

    ToolTipsManager mToolTipsManager;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_overview;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        mToolTipsManager = new ToolTipsManager();

        new Handler(Looper.getMainLooper()).post(() -> {
//            setupMonthlyReportTabs();
//            setupBalance();
//            setupLineChart();
//            setupMonthlySpendingTabs();
//            setupLatestTransaction();
            setupTotalBalance();
            setupWallet();
            setupInfoClick();
            setupViewAllWallet();
        });
    }


    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void setupInfoClick() {
       binding.ivInfo.setOnClickListener(v -> {
           ViewGroup rootView = (ViewGroup) binding.getRoot().findViewById(R.id.rootContent);

           ToolTip.Builder builder = new ToolTip.Builder(
                   requireContext(),
                   binding.ivInfo,
                   rootView,
                   ContextCompat.getString(requireContext(), R.string.balance_tooltip),
                   ToolTip.POSITION_BELOW
           );

           builder.setAlign(ToolTip.ALIGN_CENTER);
           builder.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black));
           builder.setTextAppearance(R.style.TooltipTextAppearance);
           builder.withArrow(true);
           builder.setMaxWidth(500);

           mToolTipsManager.show(builder.build());

           new Handler(Looper.getMainLooper()).postDelayed(() ->
                   mToolTipsManager.findAndDismiss(binding.ivInfo), 2000
           );
       });
    }

    public void setupViewAllWallet() {
        binding.tvViewAll.setOnClickListener(v -> {
            NavigationUtils.navigateToActivityDefault((AppCompatActivity) getActivity(), MyWalletActivity.class, null);
        });
    }

    private void setupMonthlyReportTabs() {
        String[] titles = {"Tổng đã chi", "Tổng thu"};
        String[] values = {"0 ₫", "50,000 ₫"};

        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = binding.tabReportType.newTab();
            View customTab = LayoutInflater.from(requireContext())
                    .inflate(R.layout.item_tab_report, null);

            TextView tvTitle = customTab.findViewById(R.id.tvTabTitle);
            TextView tvValue = customTab.findViewById(R.id.tvTabValue);

            tvTitle.setText(titles[i]);
            tvValue.setText(values[i]);

            tab.setCustomView(customTab);
            binding.tabReportType.addTab(tab);
        }

        binding.tabReportType.setSelectedTabIndicatorHeight(0);
        binding.tabReportType.setTabRippleColor(null);

        binding.tabReportType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int currentPos = tab.getPosition();
                int color = (currentPos == 0) ? Color.parseColor("#F44336") : Color.parseColor("#2196F3");

                ViewGroup tabStrip = (ViewGroup) binding.tabReportType.getChildAt(0);
                View currentTabView = tabStrip.getChildAt(currentPos);

                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView tvValue = customView.findViewById(R.id.tvTabValue);
                    if (tvValue != null) {
                        tvValue.setTextColor(color);
                    }
                }

                if (currentTabView != null) {
                    currentTabView.post(() -> {
                        int tabWidth = currentTabView.getWidth();
                        float tabX = currentTabView.getX();

                        View indicator = binding.viewTabIndicator;
                        indicator.setBackgroundColor(color);
                        indicator.getLayoutParams().width = tabWidth;
                        indicator.requestLayout();

                        indicator.animate()
                                .translationX(tabX)
                                .setDuration(250)
                                .setInterpolator(new DecelerateInterpolator())
                                .start();
                    });
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    TextView tvValue = customView.findViewById(R.id.tvTabValue);
                    if (tvValue != null) {
                        tvValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.dim_gray));
                    }
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.tabReportType.post(() -> {
            TabLayout.Tab firstTab = binding.tabReportType.getTabAt(0);
            if (firstTab != null) {
                View customView = firstTab.getCustomView();
                if (customView != null) {
                    TextView tvValue = customView.findViewById(R.id.tvTabValue);
                    if (tvValue != null)
                        tvValue.setTextColor(Color.parseColor("#F44336"));
                }

                ViewGroup tabStrip = (ViewGroup) binding.tabReportType.getChildAt(0);
                View firstTabView = tabStrip.getChildAt(0);

                if (firstTabView != null) {
                    int tabWidth = firstTabView.getWidth();
                    float tabX = firstTabView.getX();

                    View indicator = binding.viewTabIndicator;
                    indicator.getLayoutParams().width = tabWidth;
                    indicator.setTranslationX(tabX);
                    indicator.requestLayout();
                }
            }
        });
    }

    private Highlight lastHighlight = null;

    private void setupLineChart() {
        LineChart chart = binding.lineChart;
        int today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        // --- Dữ liệu "Cùng kỳ" ---
        List<Entry> oldData = new ArrayList<>();
        for (int day = 1; day <= 30; day++) {
            float value = (day < 15) ? 0 : 50000f;
            oldData.add(new Entry(day, value));
        }

        LineDataSet oldDataSet = new LineDataSet(oldData, "Cùng kỳ");
        oldDataSet.setColor(Color.LTGRAY);
        oldDataSet.setLineWidth(2f);
        oldDataSet.setDrawCircles(false);
        oldDataSet.setDrawValues(false);
        oldDataSet.setDrawHighlightIndicators(true);
        oldDataSet.setDrawHorizontalHighlightIndicator(false);
        oldDataSet.setHighLightColor(Color.parseColor("#F44336"));
        oldDataSet.setHighlightLineWidth(2f);
        oldDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        // --- Đường cam đáy ---
        List<Entry> orangeLine = new ArrayList<>();
        orangeLine.add(new Entry(1, 0f));
        if (today > 1) orangeLine.add(new Entry(today, 0f));

        LineDataSet orangeDataSet = new LineDataSet(orangeLine, "Đã qua");
        orangeDataSet.setColor(Color.parseColor("#F44336"));
        orangeDataSet.setLineWidth(3f);
        orangeDataSet.setDrawCircles(false);
        orangeDataSet.setDrawValues(false);
        orangeDataSet.setDrawHighlightIndicators(false);
        orangeDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        orangeDataSet.setMode(LineDataSet.Mode.LINEAR);

        // --- Chấm đỏ hôm nay ---
        Entry todayDot = new Entry(today, 0f);
        LineDataSet dotDataSet = new LineDataSet(Collections.singletonList(todayDot), "Hôm nay");
        dotDataSet.setColor(Color.TRANSPARENT);
        dotDataSet.setDrawCircles(true);
        dotDataSet.setDrawCircleHole(true);
        dotDataSet.setCircleColor(Color.parseColor("#F44336"));
        dotDataSet.setCircleHoleColor(Color.WHITE);
        dotDataSet.setCircleRadius(6f);
        dotDataSet.setCircleHoleRadius(3f);
        dotDataSet.setDrawValues(false);
        dotDataSet.setDrawHighlightIndicators(false);
        dotDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        // --- Highlight dataset hack ---
        List<Entry> highlightEntries = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            highlightEntries.add(new Entry(i, 0.01f));
        }

        LineDataSet highlightSet = new LineDataSet(highlightEntries, "Highlight hack");
        highlightSet.setColor(Color.TRANSPARENT);
        highlightSet.setDrawCircles(false);
        highlightSet.setDrawValues(false);
        highlightSet.setDrawHighlightIndicators(true);
        highlightSet.setHighlightEnabled(true);
        highlightSet.setHighLightColor(Color.parseColor("#F44336"));
        highlightSet.setHighlightLineWidth(2f);
        highlightSet.setDrawHorizontalHighlightIndicator(false);
        highlightSet.setAxisDependency(YAxis.AxisDependency.RIGHT);

        chart.setData(new LineData(oldDataSet, orangeDataSet, dotDataSet, highlightSet));

        // --- Trục X ---
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextSize(12f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if ((int) value == 1 || (int) value == 30) {
                    return String.format("%02d/04", (int) value);
                }
                return "";
            }
        });

        // --- Trục Y bên phải ---
        chart.getAxisLeft().setEnabled(false);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(50000f);
        rightAxis.setLabelCount(6, true);
        rightAxis.setTextSize(12f);
        rightAxis.setDrawGridLines(true);
        rightAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return String.format("%.0f K", value / 1000f);
            }
        });

        // --- Cấu hình chart ---
        chart.setDescription(null);
        chart.getLegend().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.setHighlightPerDragEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.setDrawMarkers(false);

        // --- Tap / drag ---
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                lastHighlight = h;
                updateChartInfo(e.getX(), 0f);
            }

            @Override
            public void onNothingSelected() {
                if (lastHighlight != null) {
                    chart.highlightValue(lastHighlight);
                } else {
                    binding.tvChartInfo.setVisibility(View.GONE);
                }
            }
        });

        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                MPPointD point = chart.getTransformer(YAxis.AxisDependency.RIGHT)
                        .getValuesByTouchPoint(me.getX(), me.getY());

                float currentX = Math.max(1f, Math.min(30f, (float) point.x));
                int day = Math.round(currentX);

                // Thay đổi màu của highlight line nếu sau hôm nay
                LineDataSet highlightSet = (LineDataSet) chart.getLineData().getDataSetByLabel("Highlight hack", true);
                if (day > today) {
                    highlightSet.setHighLightColor(Color.LTGRAY); // Màu xám
                } else {
                    highlightSet.setHighLightColor(Color.parseColor("#F44336")); // Màu đỏ gốc
                }

                chart.highlightValue(new Highlight(currentX, 0f, 3)); // 3 = index của highlightSet
                updateChartInfo(currentX, 0f);
            }
        });

        // --- Highlight mặc định ---
        float todayY = oldDataSet.getEntryForXValue(today, 0f).getY();
        chart.highlightValue(new Highlight(today, todayY, 0));
        binding.tvChartInfo.post(() -> updateChartInfo(today, 0f));

        chart.invalidate();
    }

    private void updateChartInfo(float dayFloat, float value) {
        int day = Math.round(dayFloat);
        String date = String.format(Locale.getDefault(), "%02d/04", day);
        String formatted = String.format(Locale.getDefault(), "%,.0f ₫", value);
        binding.tvChartInfo.setText(date + ":\n" + formatted);

        MPPointD point = binding.lineChart.getTransformer(YAxis.AxisDependency.RIGHT)
                .getPixelForValues(dayFloat, value);

        float offsetX = -binding.tvChartInfo.getWidth() / 2f;
        float targetX = (float) point.x + offsetX;

        moveChartInfoSmooth(targetX);
        binding.tvChartInfo.setVisibility(View.VISIBLE);
    }

    private void moveChartInfoSmooth(float targetX) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                binding.tvChartInfo, "translationX",
                binding.tvChartInfo.getTranslationX(), targetX
        );
        animator.setDuration(250);
        animator.start();
    }

    private void setupMonthlySpendingTabs() {
        binding.tabWeek.setOnClickListener(v -> switchTab(true));
        binding.tabMonth.setOnClickListener(v -> switchTab(false));
        binding.tabContainer.post(() -> switchTab(true));
    }

    private void switchTab(boolean isWeek) {
        int containerWidth = binding.tabContainer.getWidth();
        int padding = binding.tabContainer.getPaddingStart(); // paddingStart = paddingEnd = 4dp

        int tabWidth = (containerWidth - padding * 2) / 2;
        int targetX = isWeek ? 0 : tabWidth;

        // Update width
        ViewGroup.LayoutParams params = binding.tabIndicator.getLayoutParams();
        params.width = tabWidth;
        binding.tabIndicator.setLayoutParams(params);

        // Reset background để đảm bảo bo góc luôn đúng
        binding.tabIndicator.setBackgroundResource(0);
        binding.tabIndicator.setBackgroundResource(R.drawable.bg_tab_selected);

        // Animate chuyển tab
        ObjectAnimator.ofFloat(binding.tabIndicator, "translationX", targetX)
                .setDuration(250)
                .start();

        // Text color
        binding.tabWeek.setTextColor(ContextCompat.getColor(requireContext(), isWeek ? R.color.black : R.color.gray));
        binding.tabMonth.setTextColor(ContextCompat.getColor(requireContext(), isWeek ? R.color.gray : R.color.black));

        // Nội dung mô phỏng
        binding.tvMessage.setText(isWeek ?
                "Dữ liệu theo tuần sẽ hiển thị ở đây 🙌" :
                "Nhóm chi tiêu nhiều nhất sẽ hiển thị ở đây 🙌");
    }

    private void setupLatestTransaction() {
        List<LatestTransaction> arrayList = new ArrayList<>();
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));
        arrayList.add(new LatestTransaction("Ăn uống", "01/04", "50,000 ₫", R.drawable.ic_drink, R.drawable.ic_wallet_2));

        LatestTransactionAdapter adapter = new LatestTransactionAdapter(arrayList, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                LatestTransaction item = arrayList.get(position);
                viewModel.showNormalMessage(item.getCategory());
            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        binding.rcvLatestTransaction.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvLatestTransaction.setAdapter(adapter);
    }

    private boolean isBalanceVisible = true;
    private String originalBalance = "-50,000 ₫";

    public void setupTotalBalance() {
        binding.tvTotalBalance.post(() -> {
            int width = binding.tvTotalBalance.getWidth();
            binding.tvTotalBalance.setMinWidth(width);
        });
    }

    public void onShowBalanceClick() {
        isBalanceVisible = !isBalanceVisible;

        if (isBalanceVisible) {
            binding.tvTotalBalance.setText(originalBalance);
            binding.ivEye.setImageResource(R.drawable.ic_eye);
        } else {
            int length = originalBalance.replaceAll("\\s+", "").length();

            StringBuilder masked = new StringBuilder();
            for (int i = 0; i < length; i++) {
                masked.append("*");
            }

            SpannableString spannable = new SpannableString(masked.toString());
            for (int i = 0; i < masked.length(); i++) {
                spannable.setSpan(new ScaleXSpan(1.3f), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            binding.tvTotalBalance.setText(spannable);
            binding.ivEye.setImageResource(R.drawable.ic_eye_diagonal);
        }
    }

    public void setupWallet() {
        viewModel.doGetFirstWallet(new MainCallback<List<WalletResponse>>() {
            @Override
            public void doError(Throwable error) {

            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doFail() {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void doSuccess(List<WalletResponse> walletResponses) {
                if(walletResponses == null || walletResponses.isEmpty()) {
                    binding.llWallet.setVisibility(View.GONE);
                    return;
                }
                binding.llWallet.setVisibility(View.VISIBLE);
                WalletResponse walletResponse = walletResponses.get(0);

                Glide.with(requireContext())
                        .load(walletResponse.getIcon().getFileUrl())
                        .into(binding.ivWalletIcon);
                binding.tvWalletName.setText(walletResponse.getName());
                binding.tvWalletBalance.setText(NumberUtils.formatNumberWithComma(walletResponse.getBalance()) + " " + walletResponse.getCurrency().getCode());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setupWallet();
    }
}