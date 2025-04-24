package com.moneylover.ui.main.app.transactionHistory;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.moneylover.R;
import com.moneylover.data.model.Expenditure;
import com.moneylover.databinding.FragmentViewReportListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.custom.lineview.LineView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ViewReportListFragment extends BaseFragment<FragmentViewReportListBinding, ViewReportListViewModel> {


    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_report_list;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        setupBarChart();
        setupPieChart();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setupBarChart() {
        BarChart barChart = binding.barChart;

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            entries.add(new BarEntry(i, new float[]{0f, 0f}));
        }

        float positiveValue = 200_000f;
        float negativeValue = -4_800_000f; // lưu ý: là số âm

        BarDataSet dataSet = new BarDataSet(entries, "Thu nhập ròng");
        dataSet.setColors(
                getActivity().getColor(R.color.curious_blue), // màu cho giá trị dương
                getActivity().getColor(R.color.crimson)       // màu cho giá trị âm
        );
        dataSet.setDrawValues(false);
        dataSet.setHighLightAlpha(0);

        BarData data = new BarData(dataSet);
        data.setBarWidth(2f);
        barChart.setData(data);
        barChart.getXAxis().setAxisMinimum(-0.5f);
        barChart.getXAxis().setAxisMaximum(5f);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(-5_000_000f); // cần bao phủ phần âm
        yAxisLeft.setAxisMaximum(positiveValue);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setTextColor(Color.GRAY);
        yAxisLeft.setTextSize(12f);
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) (-value / 1_000_000f) + "M ₫";
            }
        });

        barChart.getAxisRight().setEnabled(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(11f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(
                new String[]{"01/04-06/04", "07/04-13/04", "14/04-20/04", "21/04-27/04", "28/04-04/05"}
        ));

        barChart.setFitBars(true);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setExtraOffsets(5, 10, 5, 10);

        // Animation thủ công
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            float[] animated = new float[]{
                    positiveValue * progress,
                    negativeValue * progress
            };

            entries.set(4, new BarEntry(4, animated));

            BarDataSet updatedSet = new BarDataSet(entries, "Thu nhập ròng");
            updatedSet.setColors(
                    getActivity().getColor(R.color.curious_blue),
                    getActivity().getColor(R.color.crimson)
            );
            updatedSet.setDrawValues(false);
            updatedSet.setHighLightAlpha(0);

            barChart.setData(new BarData(updatedSet));
            barChart.invalidate();
        });

        animator.start();
    }

    private void setupPieChart() {
        List<Expenditure> expenditures = Arrays.asList(
                new Expenditure("Ăn uống", 25f, R.drawable.ic_drink),
                new Expenditure("Di chuyển", 20f, R.drawable.ic_drink),
                new Expenditure("Mua sắm", 15f, R.drawable.ic_drink),
                new Expenditure("Hóa đơn", 40f, R.drawable.ic_drink)
        );

        PieChart pieChart = binding.pieChart;
        ConstraintLayout container = binding.chartContainer;

        container.post(() -> {
            for (int i = container.getChildCount() - 1; i >= 0; i--) {
                View view = container.getChildAt(i);
                if (view.getId() != R.id.pieChart) container.removeView(view);
            }
        });

        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = Arrays.asList(
                ContextCompat.getColor(getContext(), R.color.deep_sky_blue),
                ContextCompat.getColor(getContext(), R.color.red),
                ContextCompat.getColor(getContext(), R.color.green),
                ContextCompat.getColor(getContext(), R.color.black),
                ContextCompat.getColor(getContext(), R.color.yellow)
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
                container.addView(icon);

                // Label position
                float labelX = (float) (centerX + labelRadius * Math.cos(radians));
                float labelY = (float) (centerY + labelRadius * Math.sin(radians));

                TextView label = new TextView(getContext());
                label.setText(String.format(Locale.getDefault(), "%.0f%%", (item.value / finalTotal) * 100f));
                label.setTextColor(Color.BLACK);
                label.setTextSize(12f);
                container.addView(label);
                label.post(() -> {
                    label.setX(labelX - label.getWidth() / 2f);
                    label.setY(labelY - label.getHeight() / 2f);
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
                int finalI = i;
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
                    });
                });

            }
        }, 300);

    }

}