package com.moneylover.ui.main.app.transactionHistory;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.Expenditure;
import com.moneylover.databinding.FragmentExpenditureDetailBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.custom.lineview.LineView;
import com.moneylover.ui.main.app.transactionHistory.adapter.ExpenditureDetailBarchartAdapter;
import com.moneylover.ui.main.app.transactionHistory.adapter.ExpenditureDetailPieChartAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ExpenditureDetailFragment extends BaseFragment<FragmentExpenditureDetailBinding, ExpenditureDetailViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_expenditure_detail;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        setupToggleCharts();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable hideValuesRunnable;
    private Float selectedX = null;

    private void setupToggleCharts() {
        MaterialButtonToggleGroup toggleGroup = binding.toggleGroup;
        MaterialButton btnBar = binding.btnBar;
        MaterialButton btnPie = binding.btnPie;

        btnBar.setChecked(true);
        btnBar.setIconTint(ContextCompat.getColorStateList(getContext(), R.color.green));
        btnPie.setIconTint(ContextCompat.getColorStateList(getContext(), R.color.gray));

        setupBarChart();
        setupExpendituresWithBarchart();

        binding.barChartExpenditure.setVisibility(View.VISIBLE);
        binding.chartContainerExpenditure.setVisibility(View.GONE);

        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnBar) {
                    btnBar.setIconTint(ContextCompat.getColorStateList(getContext(), R.color.green));
                    btnPie.setIconTint(ContextCompat.getColorStateList(getContext(), R.color.gray));
                    binding.barChartExpenditure.setVisibility(View.VISIBLE);
                    binding.chartContainerExpenditure.setVisibility(View.GONE);
                    binding.barChartExpenditure.animateY(1000, Easing.EaseInOutQuad);
                    setupExpendituresWithBarchart();
                } else if (checkedId == R.id.btnPie) {
                    btnPie.setIconTint(ContextCompat.getColorStateList(getContext(), R.color.green));
                    btnBar.setIconTint(ContextCompat.getColorStateList(getContext(), R.color.gray));
                    binding.barChartExpenditure.setVisibility(View.GONE);
                    binding.chartContainerExpenditure.setVisibility(View.VISIBLE);
                    setupPieChartExpenditure();
                    setupExpendituresWithPieChart();
                }
            }
        });
    }

    private void setupBarChart() {
        BarChart barChart = binding.barChartExpenditure;

        List<BarEntry> entries = new ArrayList<>();
        Random random = new Random();

        // 1. Sinh 5 giá trị dương từ 1 triệu đến 5 triệu
        for (int i = 0; i < 5; i++) {
            float value = 1_000_000f + random.nextFloat() * 4_000_000f; // random từ 1M đến 5M
            entries.add(new BarEntry(i, value));
        }

        // 2. Cấu hình dataset
        BarDataSet dataSet = new BarDataSet(entries, "Thu nhập ròng (giả lập)");
        dataSet.setColor(getActivity().getColor(R.color.crimson));
        dataSet.setDrawValues(false);
        dataSet.setHighLightAlpha(0);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);
        barChart.setData(data);

        barChart.animateY(1000, Easing.EaseInOutQuad);

        // 3. Thiết lập trục X
        barChart.getXAxis().setAxisMinimum(-0.5f);
        barChart.getXAxis().setAxisMaximum(entries.size() - 0.5f);

        // 4. Trục Y từ 0 lên 5M
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0f); // ✅ bắt đầu từ 0
        yAxisLeft.setAxisMaximum(5_000_000f);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setTextColor(Color.GRAY);
        yAxisLeft.setTextSize(12f);
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) (value / 1_000_000f) + "M ₫";
            }
        });

        barChart.getAxisRight().setEnabled(false);

        // 5. Trục X label
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(11f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{
                "01/04-06/04", "07/04-13/04", "14/04-20/04", "21/04-27/04", "28/04-04/05"
        }));

        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setExtraOffsets(5, 10, 5, 10);

        barChart.invalidate(); // Vẽ lại

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e instanceof BarEntry) {
                    selectedX = e.getX();

                    dataSet.setDrawValues(true);
                    dataSet.setValueTextSize(14f);
                    dataSet.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getBarLabel(BarEntry barEntry) {
                            if (selectedX != null && selectedX == barEntry.getX()) {
                                int xValue = (int) barEntry.getX();
                                int yValue = (int) (barEntry.getY() / 1_000_000f);
                                return xValue + "," + yValue + "M" + " ₫";
                            }
                            return "";
                        }
                    });

                    barChart.invalidate();

                    if (hideValuesRunnable != null) {
                        handler.removeCallbacks(hideValuesRunnable);
                    }

                    hideValuesRunnable = () -> {
                        dataSet.setDrawValues(false);
                        selectedX = null; // clear selection
                        barChart.highlightValues(null);
                        barChart.invalidate();
                    };
                    handler.postDelayed(hideValuesRunnable, 3000);
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });

    }

    private void setupPieChartExpenditure() {
        List<Expenditure> expenditures = Arrays.asList(
                new Expenditure("Ăn uống", 25f, R.drawable.ic_drink),
                new Expenditure("Di chuyển", 20f, R.drawable.ic_drink),
                new Expenditure("Mua sắm", 15f, R.drawable.ic_drink),
                new Expenditure("Hóa đơn", 30f, R.drawable.ic_drink),
                new Expenditure("Nhậu", 10f, R.drawable.ic_drink)
        );

        PieChart pieChart = binding.pieChartExpenditure;
        ConstraintLayout container = binding.chartContainerExpenditure;

        container.post(() -> {
            for (int i = container.getChildCount() - 1; i >= 0; i--) {
                View view = container.getChildAt(i);
                if (view.getId() != R.id.pieChartExpenditure) container.removeView(view);
            }
        });

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = Arrays.asList(
                ContextCompat.getColor(getContext(), R.color.deep_sky_blue),
                ContextCompat.getColor(getContext(), R.color.red),
                ContextCompat.getColor(getContext(), R.color.green),
                ContextCompat.getColor(getContext(), R.color.black),
                ContextCompat.getColor(getContext(), R.color.purple)
        );

        float total = 0;
        for (Expenditure e : expenditures) {
            entries.add(new PieEntry(e.value, e.name));
            total += e.value;
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);

        pieChart.setData(new PieData(dataSet));
        pieChart.animateY(1000, Easing.EaseInOutQuad);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(65f);
        pieChart.setTransparentCircleRadius(70f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTouchEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();

        float finalTotal = total;
        pieChart.postDelayed(() -> {
            float startAngle = 0f;
            for (int i = 0; i < expenditures.size(); i++) {
                Expenditure item = expenditures.get(i);
                int finalI = i;
                float angle = (item.value / finalTotal) * 360f;
                float centerAngle = startAngle + angle / 2f;
                startAngle += angle;

                double radians = Math.toRadians(centerAngle - 90);

                float centerX = pieChart.getX() + pieChart.getWidth() / 2f;
                float centerY = pieChart.getY() + pieChart.getHeight() / 2f;

                float iconRadius = 300f;
                float labelRadius = 400f;
                int iconSize = 80;

                // Icon position
                float iconX = (float) (centerX + iconRadius * Math.cos(radians));
                float iconY = (float) (centerY + iconRadius * Math.sin(radians));

                ImageView icon = new ImageView(getContext());
                icon.setImageResource(item.iconRes);
                FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(iconSize, iconSize);
                icon.setLayoutParams(iconParams);
                icon.setX(iconX - iconSize / 2f);
                icon.setY(iconY - iconSize / 2f);
                icon.setAlpha(0f);
                icon.setScaleX(0f);
                icon.setScaleY(0f);
                container.addView(icon);
                icon.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(400)
                        .setStartDelay(i * 100)
                        .start();

                // Label position
                float labelX = (float) (centerX + labelRadius * Math.cos(radians));
                float labelY = (float) (centerY + labelRadius * Math.sin(radians));

                TextView label = new TextView(getContext());
                label.setText(String.format(Locale.getDefault(), "%.0f%%", (item.value / finalTotal) * 100f));
                label.setTextColor(Color.BLACK);
                label.setTextSize(12f);
                label.setAlpha(0f);
                label.setScaleX(0f);
                label.setScaleY(0f);
                container.addView(label);
                label.post(() -> {
                    label.setX(labelX - label.getWidth() / 2f);
                    label.setY(labelY - label.getHeight() / 2f);

                    label.animate()
                            .alpha(1f)
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(400)
                            .setStartDelay(finalI * 100 + 100)
                            .start();
                });

                float iconCenterX = iconX;
                float iconCenterY = iconY;
                float distance = 40f; // khoảng cách từ icon về phía pieChart
                float dotX = (float) (iconCenterX - distance * Math.cos(radians));
                float dotY = (float) (iconCenterY - distance * Math.sin(radians));

                View greenCircle = new View(getContext());
                FrameLayout.LayoutParams circleParams = new FrameLayout.LayoutParams(20, 20); // size chấm
                greenCircle.setLayoutParams(circleParams);

//                GradientDrawable circle = new GradientDrawable();
//                circle.setShape(GradientDrawable.OVAL);
//                circle.setColor(colors.get(i));
//                circle.setSize(20, 20);
//                greenCircle.setBackground(circle);

                greenCircle.setX(dotX - 10); // center lại
                greenCircle.setY(dotY - 10);
                container.addView(greenCircle);

//                --------------------------------------------
                float _centerX = pieChart.getX() + pieChart.getWidth() / 2f;
                float _centerY = pieChart.getY() + pieChart.getHeight() / 2f;

                float radius = pieChart.getWidth() / 2f * (pieChart.getTransparentCircleRadius() / 80f);

                float x = (float) (_centerX + radius * Math.cos(radians));
                float y = (float) (_centerY + radius * Math.sin(radians));

                View dot = new View(getContext());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(20, 20);
                dot.setLayoutParams(params);
//                dot.setBackground(ContextCompat.getDrawable(getContext(), colors.get(i)));

//                GradientDrawable dotCircle = new GradientDrawable();
//                dotCircle.setShape(GradientDrawable.OVAL);
//                dotCircle.setColor(colors.get(i));
//                dotCircle.setSize(20, 20);
//                dot.setBackground(dotCircle);

                dot.setX(x - 10);
                dot.setY(y - 10);
                container.addView(dot);

//    ------------------------------------------------------
                greenCircle.post(() -> {
                    dot.post(() -> {
                        float startX = greenCircle.getX() + greenCircle.getWidth() / 2f;
                        float startY = greenCircle.getY() + greenCircle.getHeight() / 2f;
                        float endX = dot.getX() + dot.getWidth() / 2f;
                        float endY = dot.getY() + dot.getHeight() / 2f;
                        int color = colors.get(finalI);
                        LineView lineView = new LineView(getContext(), startX, startY, endX, endY, color);
                        container.addView(lineView, new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        ));
                        lineView.startAnimation();
                    });
                });

            }
        }, 300);
    }

    List<Expenditure> expenditures = Arrays.asList(
            new Expenditure("Ăn uống", 5_000_000f, R.drawable.ic_drink, "01/04-06/04"),
            new Expenditure("Di chuyển", 2_000_000f, R.drawable.ic_drink, "07/04-13/04"),
            new Expenditure("Mua sắm", 50_000_000f, R.drawable.ic_drink, "14/04-20/04"),
            new Expenditure("Hóa đơn", 3_000_000f, R.drawable.ic_drink, "21/04-27/04"),
            new Expenditure("Nhậu", 1_000_000f, R.drawable.ic_drink, "28/04-04/05")
    );

    public void setupExpendituresWithBarchart() {
        ExpenditureDetailBarchartAdapter adapter = new ExpenditureDetailBarchartAdapter(getContext(), expenditures, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        binding.rcvExpenditure.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvExpenditure.setAdapter(adapter);
    }

    public void setupExpendituresWithPieChart() {
        ExpenditureDetailPieChartAdapter adapter = new ExpenditureDetailPieChartAdapter(getContext(), expenditures, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        binding.rcvExpenditure.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvExpenditure.setAdapter(adapter);
    }
}
