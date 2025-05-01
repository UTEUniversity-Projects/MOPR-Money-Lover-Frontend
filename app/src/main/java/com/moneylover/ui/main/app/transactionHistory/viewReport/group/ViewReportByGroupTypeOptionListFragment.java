package com.moneylover.ui.main.app.transactionHistory.viewReport.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.ReportGroupType;
import com.moneylover.databinding.FragmentViewReportByGroupTypeOptionListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.app.transactionHistory.viewReport.group.adapter.ViewReportByGroupListOptionAdapter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class ViewReportByGroupTypeOptionListFragment extends BaseFragment<FragmentViewReportByGroupTypeOptionListBinding, ViewReportByGroupTypeOptionListViewModel> {

    private String type;
    private ReportGroupType selectedGroupType;
    private List<ReportGroupType> originalList = new ArrayList<>();
    private ViewReportByGroupListOptionAdapter adapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_report_by_group_type_option_list;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            type = getArguments().getString("type");
            selectedGroupType = (ReportGroupType) getArguments().getSerializable("selectedGroupType");
        }

        setupViewReportGroupType();
    }

    public void setupViewReportGroupType() {
        originalList.clear();

        if ("EXPENDITURE".equalsIgnoreCase(type)) {
            for (int i = 0; i < 10; i++) {
                originalList.add(new ReportGroupType(R.drawable.ic_drink, "Ăn uống " + i, "Tiền mặt", "EXPENDITURE"));
            }
        } else if ("INCOME".equalsIgnoreCase(type)) {
            for (int i = 0; i < 10; i++) {
                originalList.add(new ReportGroupType(R.drawable.ic_wallet_2, "Lương " + i, "Tiền mặt", "INCOME"));
            }
        }

        adapter = new ViewReportByGroupListOptionAdapter(
                getContext(),
                originalList,
                selectedGroupType,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ReportGroupType group = originalList.get(position);
                        Intent intent = new Intent();
                        intent.putExtra("selectedGroupType", group);
                        requireActivity().setResult(Activity.RESULT_OK, intent);
                        requireActivity().finish();
                    }

                    @Override
                    public void onItemDelete(int position) {
                    }
                });

        binding.rcvGroupType.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcvGroupType.setAdapter(adapter);
    }

    public void search(String keyword) {
        if (adapter != null) {
            filterList(keyword);
        }
    }

    private void filterList(String keyword) {
        if (adapter == null) return;

        List<ReportGroupType> filtered = new ArrayList<>();
        String normalizedKeyword = normalize(keyword);

        for (ReportGroupType item : originalList) {
            String normalizedItemName = normalize(item.getName());
            if (normalizedItemName.contains(normalizedKeyword)) {
                filtered.add(item);
            }
        }

        adapter.updateData(filtered);
    }

    private String normalize(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.toLowerCase();
    }
}
