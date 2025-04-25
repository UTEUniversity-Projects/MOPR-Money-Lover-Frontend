package com.moneylover.ui.custom.timerangebottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.moneylover.R;
import com.moneylover.ui.main.app.transactionHistory.adapter.TimeRangeAdapter;

import java.util.Arrays;
import java.util.List;

public class TimeRangeBottomSheet extends BottomSheetDialogFragment {

    private int selectedIndex;
    private final TimeRangeAdapter.OnTimeRangeClickListener listener;

    public TimeRangeBottomSheet(int selectedIndex, TimeRangeAdapter.OnTimeRangeClickListener listener) {
        this.selectedIndex = selectedIndex;
        this.listener = listener;
    }

    private final List<String> labels = Arrays.asList("Ngày", "Tuần", "Tháng", "Quý", "Năm");
    private final List<Integer> icons = Arrays.asList(
            R.drawable.ic_day, R.drawable.ic_week, R.drawable.ic_month,
            R.drawable.ic_quarter, R.drawable.ic_year
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_time_range_picker, container, false);
        RecyclerView rv = view.findViewById(R.id.rvOptions);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        TimeRangeAdapter adapter = new TimeRangeAdapter(labels, icons, selectedIndex, (position, label) -> {
            selectedIndex = position;
            listener.onClick(position, label);
            rv.postDelayed(this::dismiss, 150);
        });

        rv.setAdapter(adapter);
        return view;
    }
}

