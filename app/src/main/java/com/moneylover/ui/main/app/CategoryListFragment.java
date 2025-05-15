package com.moneylover.ui.main.app;

import android.app.Activity;
import android.content.Intent;

import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.R;
import com.moneylover.data.model.api.response.CategoryResponse;
import com.moneylover.databinding.FragmentCategoryListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.MainCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryListFragment extends BaseFragment<FragmentCategoryListBinding, CategoryListViewModel> {

    private String type;
    private CategoryResponse selectedCategory;
    private List<CategoryResponse> originalList = new ArrayList<>();
    private CategoryListAdapter adapter;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category_list;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);

        if (getArguments() != null) {
            type = getArguments().getString("type");
            selectedCategory = (CategoryResponse) getArguments().getSerializable("selected_category");
        }

        setupCategoryList();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    private void setupCategoryList() {
        originalList.clear();

        Map<String, Object> params = new HashMap<>();
        params.put("page", 0);
        params.put("size", 1000);

        if ("EXPENDITURE".equalsIgnoreCase(type)) {
            params.put("isExpense", true);
        } else if ("INCOME".equalsIgnoreCase(type)) {
            params.put("isExpense", false);
        }

        viewModel.doGetCategoryList(new MainCallback<List<CategoryResponse>>() {
            @Override
            public void doError(Throwable error) {

            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(List<CategoryResponse> categoryResponses) {
                adapter = new CategoryListAdapter(getContext(), categoryResponses, selectedCategory, new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        selectedCategory = categoryResponses.get(position);

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("selected_category", selectedCategory);
                        requireActivity().setResult(Activity.RESULT_OK, resultIntent);
                        requireActivity().finish();
                    }

                    @Override
                    public void onItemDelete(int position) {

                    }
                });
                binding.rcvCategoryList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                binding.rcvCategoryList.setAdapter(adapter);
            }

            @Override
            public void doFail() {

            }
        }, params);
    }
}