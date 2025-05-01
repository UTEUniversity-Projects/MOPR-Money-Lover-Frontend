package com.moneylover.ui.main.app.transactionHistory.viewReport;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
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
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.Expenditure;
import com.moneylover.data.model.NetIncome;
import com.moneylover.databinding.FragmentViewReportListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.custom.lineview.LineView;
import com.moneylover.ui.main.app.transactionHistory.adapter.SubItemAdapter;
import com.moneylover.ui.main.app.transactionHistory.viewReport.expenditure.ExpenditureActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.ViewReportByGroupActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.income.IncomeActivity;
import com.moneylover.ui.main.app.transactionHistory.viewReport.netIncome.NetIncomeActivity;
import com.moneylover.utils.NavigationUtils;

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

        binding.layoutNetIncome.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), NetIncomeActivity.class));
        });


        binding.layoutExpenditure.setOnClickListener(v -> {
            NavigationUtils.navigateToActivity((AppCompatActivity) getActivity(), ExpenditureActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.layoutIncome.setOnClickListener(v -> {
            NavigationUtils.navigateToActivity((AppCompatActivity) getActivity(), IncomeActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.llReportByGroup.setOnClickListener(v -> {
            NavigationUtils.navigateToActivity((AppCompatActivity) getActivity(), ViewReportByGroupActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
        });

        setupBarChart();
        setupPieChart();
        setupPieChartIncome();
        setupExpandableItems();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setupBarChart() {
        BarChart barChart = binding.barChart;

        List<NetIncome> netIncomes = Arrays.asList(
                new NetIncome("01/04-06/04", 4_800_000, 200_000),
                new NetIncome("07/04-13/04", 2_000_000, 3_000_000),
                new NetIncome("14/04-20/04", 1_000_000, 1_500_000),
                new NetIncome("21/04-27/04", 3_000_000, 500_000),
                new NetIncome("28/04-04/05", 2_000_000, 1_000_000)
        );

        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < netIncomes.size(); i++) {
            NetIncome item = netIncomes.get(i);
            labels.add(item.getDate());

            float income = item.getIncome();
            float expenditure = -item.getExpenditure();

            entries.add(new BarEntry(i, new float[]{income, expenditure}));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Thu nhập/Chi tiêu");

        dataSet.setColors(
                getActivity().getColor(R.color.curious_blue),
                getActivity().getColor(R.color.crimson)
        );
        dataSet.setStackLabels(new String[]{"Thu nhập", "Chi tiêu"});

        dataSet.setDrawValues(false);
        dataSet.setHighLightAlpha(0);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);
        barChart.setData(data);

        barChart.animateY(1000, Easing.EaseInOutQuad);

        // X Axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(11f);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        // Y Axis
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(-5_000_000f);
        yAxisLeft.setAxisMaximum(5_000_000f);
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setTextColor(Color.GRAY);
        yAxisLeft.setTextSize(12f);
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                float million = value / 1_000_000f;
                return String.format(Locale.getDefault(), "%.1fM đ", million);
            }
        });

        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(true);
        barChart.getLegend().setTextColor(Color.GRAY);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.invalidate();
    }

    private void setupPieChart() {
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
            entries.add(new PieEntry(e.getValue(), e.getName()));
            total += e.getValue();
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
                float angle = (item.getValue() / finalTotal) * 360f;
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
                icon.setImageResource(item.getIconResId());
                FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(iconSize, iconSize);
                icon.setLayoutParams(iconParams);
                icon.setX(iconX - iconSize / 2f);
                icon.setY(iconY - iconSize / 2f);
                container.addView(icon);

                // Label position
                float labelX = (float) (centerX + labelRadius * Math.cos(radians));
                float labelY = (float) (centerY + labelRadius * Math.sin(radians));

                TextView label = new TextView(getContext());
                label.setText(String.format(Locale.getDefault(), "%.0f%%", (item.getValue() / finalTotal) * 100f));
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
                        lineView.startAnimation();
                    });
                });

            }
        }, 300);

    }

    private void setupPieChartIncome() {
        List<Expenditure> expenditures = Arrays.asList(
                new Expenditure("Ăn uống", 25f, R.drawable.ic_drink),
                new Expenditure("Di chuyển", 20f, R.drawable.ic_drink),
                new Expenditure("Mua sắm", 15f, R.drawable.ic_drink),
                new Expenditure("Hóa đơn", 30f, R.drawable.ic_drink),
                new Expenditure("Nhậu", 10f, R.drawable.ic_drink)
        );

        PieChart pieChart = binding.pieChartIncome;
        ConstraintLayout container = binding.chartContainerIncome;

        container.post(() -> {
            for (int i = container.getChildCount() - 1; i >= 0; i--) {
                View view = container.getChildAt(i);
                if (view.getId() != R.id.pieChartIncome) container.removeView(view);
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
            entries.add(new PieEntry(e.getValue(), e.getName()));
            total += e.getValue();
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
                float angle = (item.getValue() / finalTotal) * 360f;
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
                icon.setImageResource(item.getIconResId());
                FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(iconSize, iconSize);
                icon.setLayoutParams(iconParams);
                icon.setX(iconX - iconSize / 2f);
                icon.setY(iconY - iconSize / 2f);
                container.addView(icon);

                // Label position
                float labelX = (float) (centerX + labelRadius * Math.cos(radians));
                float labelY = (float) (centerY + labelRadius * Math.sin(radians));

                TextView label = new TextView(getContext());
                label.setText(String.format(Locale.getDefault(), "%.0f%%", (item.getValue() / finalTotal) * 100f));
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
                        lineView.startAnimation();
                    });
                });

            }
        }, 300);

    }

    private void setupExpandableItems() {
        LinearLayout container = binding.containerRoot;

        List<List<String>> dataSets = Arrays.asList(
                Arrays.asList("Nợ A", "Nợ B", "Nợ C"),
                Arrays.asList("Cho vay A", "Cho vay B"),
                Arrays.asList("Khác A", "Khác B", "Khác C", "Khác D")
        );

        for (int i = 0; i < container.getChildCount(); i++) {
            View item = container.getChildAt(i);

            TextView tvTitle = item.findViewById(R.id.tvTitle);
            TextView tvAmount = item.findViewById(R.id.tvAmount);
            ImageView ivArrow = item.findViewById(R.id.ivArrow);
            RecyclerView expandContent = item.findViewById(R.id.expandContent);

            switch (i) {
                case 0:
                    tvTitle.setText("Nợ");
                    tvAmount.setText(String.valueOf(dataSets.get(0).size()));
                    break;
                case 1:
                    tvTitle.setText("Cho vay");
                    tvAmount.setText(String.valueOf(dataSets.get(1).size()));
                    break;
                case 2:
                    tvTitle.setText("Khác");
                    tvAmount.setText(String.valueOf(dataSets.get(2).size()));
                    break;
            }

            expandContent.setLayoutManager(new LinearLayoutManager(getContext()));
            expandContent.setAdapter(new SubItemAdapter(dataSets.get(i)));
            expandContent.setVisibility(View.GONE);

            LinearLayout headerRow = item.findViewById(R.id.headerRow);
            headerRow.setOnClickListener(v -> {
                boolean isVisible = expandContent.getVisibility() == View.VISIBLE;

                rotateArrow(ivArrow, !isVisible);

                if (isVisible) {
                    collapse(expandContent);
                } else {
                    expand(expandContent);
                }
            });
        }
    }

    private void rotateArrow(ImageView arrow, boolean expand) {
        float from = expand ? 0f : 90f;
        float to = expand ? 90f : 0f;

        ObjectAnimator rotate = ObjectAnimator.ofFloat(arrow, "rotation", from, to);
        rotate.setDuration(200);
        rotate.start();
    }

    private void expand(View view) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int targetHeight = view.getMeasuredHeight();

        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });
        animator.setDuration(300);
        animator.start();
    }

    private void collapse(View view) {
        int initialHeight = view.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });

        animator.setDuration(300);
        animator.start();
    }

}