package com.moneylover.ui.main.app.transactionHistory;


import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.NetIncome;
import com.moneylover.databinding.FragmentNetIncomeDetailBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NetIncomeDetailFragment extends BaseFragment<FragmentNetIncomeDetailBinding, NetIncomeDetailViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_net_income_detail;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        setupBarChart();
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
            float expenditure = -item.getExpenditure(); // Chi tiêu để số âm

            entries.add(new BarEntry(i, new float[]{income, expenditure}));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Thu nhập/Chi tiêu");

        // Màu stack: income màu xanh, expenditure màu đỏ
        dataSet.setColors(
                getActivity().getColor(R.color.curious_blue), // income
                getActivity().getColor(R.color.crimson)       // expenditure
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
                float million = value / 1_000_000f; // chia cho 1 triệu
                return String.format(Locale.getDefault(), "%.1fM đ", million);
            }
        });

        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(true); // Bật legend để thấy stack
        barChart.getLegend().setTextColor(Color.GRAY);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.invalidate();
    }
}