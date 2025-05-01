package com.moneylover.ui.main.app.transactionHistory.viewReport.group;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.ReportGroup;
import com.moneylover.databinding.FragmentViewReportByGroupListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.adapter.ReportGroupAdapter;
import com.moneylover.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ViewReportByGroupListFragment extends BaseFragment<FragmentViewReportByGroupListBinding, ViewReportByGroupListViewModel> {
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_report_by_group_list;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        setupBarChart();
        setupReport();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable hideValuesRunnable;
    private Float selectedX = null;

    private void setupBarChart() {
        BarChart barChart = binding.barChartGroup;

        List<BarEntry> entries = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            float value = 1_000_000f + random.nextFloat() * 4_000_000f;
            entries.add(new BarEntry(i, value));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Thu nhập ròng (giả lập)");
        dataSet.setColor(getActivity().getColor(R.color.crimson));
        dataSet.setDrawValues(false);
        dataSet.setHighLightAlpha(0);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.4f);
        barChart.setData(data);

        barChart.animateY(1000, Easing.EaseInOutQuad);

        barChart.getXAxis().setAxisMinimum(-0.5f);
        barChart.getXAxis().setAxisMaximum(entries.size() - 0.5f);

        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0f);
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

        barChart.invalidate();

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
                        selectedX = null;
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

    List<ReportGroup> reportGroups = Arrays.asList(
            new ReportGroup("01/04-06/04", 1_000_000f),
            new ReportGroup("07/04-13/04", 2_000_000f),
            new ReportGroup("14/04-20/04", 3_000_000f),
            new ReportGroup("21/04-27/04", 4_000_000f),
            new ReportGroup("28/04-04/05", 5_000_000f)
    );

    public void setupReport() {
        ReportGroupAdapter adapter = new ReportGroupAdapter(getContext(), reportGroups, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NavigationUtils.navigateToActivity((AppCompatActivity) getActivity(), ViewReportByGroupTransactionActivity.class, R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void onItemDelete(int position) {

            }
        });
        binding.rcvGroup.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.rcvGroup.setAdapter(adapter);
    }
}
