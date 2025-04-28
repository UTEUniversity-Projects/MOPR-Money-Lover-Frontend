package com.moneylover.ui.main.app.transactionHistory;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.ReportGroupType;
import com.moneylover.databinding.FragmentViewReportByGroupListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.app.transactionHistory.adapter.ViewReportByGroupListAdapter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class ViewReportByGroupListFragment extends BaseFragment<FragmentViewReportByGroupListBinding, ViewReportByGroupListViewModel> {

    private String type;
    private List<ReportGroupType> originalList = new ArrayList<>();
    private ViewReportByGroupListAdapter adapter;

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

        if (getArguments() != null) {
            type = getArguments().getString("type");
        }

    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void setupViewReportList() {
        originalList.clear();

        if (type.equalsIgnoreCase("EXPENDITURE")) {
            for (int i = 0; i < 10; i++) {
                originalList.add(new ReportGroupType(R.drawable.ic_drink, "Ăn uống " + i, "Tiền mặt"));
            }
        } else {
            for (int i = 0; i < 10; i++) {
                originalList.add(new ReportGroupType(R.drawable.ic_wallet_2, "Lương " + i, "Tiền mặt"));
            }
        }

        adapter = new ViewReportByGroupListAdapter(getContext(), originalList, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {}

            @Override
            public void onItemDelete(int position) {}
        });

        binding.rcvGroupType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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

    public void setTypeAndReload(String type) {
        this.type = type;
        setupViewReportList();
    }

    private String normalize(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.toLowerCase();
    }

}