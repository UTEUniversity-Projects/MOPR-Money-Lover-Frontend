package com.moneylover.ui.main.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.response.CurrencyResponse;
import com.moneylover.databinding.ActivityCurrencyBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.base.adapter.OnItemClickListener;
import com.moneylover.ui.main.MainCallback;

import java.util.Collections;
import java.util.List;

public class CurrencyActivity extends BaseActivity<ActivityCurrencyBinding, CurrencyViewModel> {

    private List<CurrencyResponse> fullCurrencyList;
    private CurrencyAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_currency;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

        setupSearchView();
        setupCurrencyList();
    }

    private void setupSearchView() {
        viewBinding.searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                viewBinding.searchView.post(() -> {
                    removeSearchViewHintIcon();
                });

                viewBinding.tvTitle.setVisibility(View.GONE);
            } else {
                viewBinding.tvTitle.setVisibility(View.VISIBLE);
            }
        });

        viewBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showCloseButtonAlways();
                filterCurrencyList(newText);
                return true;
            }
        });
    }

    private void removeSearchViewHintIcon() {
        try {
            View searchEditText = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            if (searchEditText instanceof TextView) {
                ((TextView) searchEditText).setCompoundDrawables(null, null, null, null);
            }

            viewBinding.searchView.setIconifiedByDefault(false);
            viewBinding.searchView.setIconified(false);
            viewBinding.searchView.setSubmitButtonEnabled(false);
            viewBinding.searchView.setQueryHint("Tìm kiếm...");

            View magIcon = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
            if (magIcon != null) {
                magIcon.setVisibility(View.GONE);
            }

            View searchPlate = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_plate);
            if (searchPlate != null) {
//                searchPlate.setBackgroundColor(android.graphics.Color.TRANSPARENT); // Xóa underline nếu muốn
            }

            View closeButton = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
            if (closeButton != null) {
                closeButton.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        setupCloseButtonBehavior();
    }

    private void setupCloseButtonBehavior() {
        View closeButton = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> {
                String currentText = viewBinding.searchView.getQuery().toString();
                if (!currentText.isEmpty()) {
                    viewBinding.searchView.setQuery("", false);
                } else {
                    viewBinding.searchView.clearFocus();
                    viewBinding.searchView.setIconified(true);
                    viewBinding.searchView.setIconifiedByDefault(true);

                    viewBinding.tvTitle.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void showCloseButtonAlways() {
        View closeButton = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        if (closeButton != null) {
            closeButton.setVisibility(View.VISIBLE);
        }
    }

    private void setupCurrencyList() {
        CurrencyResponse selectedCurrency = (CurrencyResponse) getIntent().getSerializableExtra("selected_currency");
        final Long[] selectedCurrencyId = {selectedCurrency != null ? selectedCurrency.getId() : null};

        viewModel.doGetCurrencyList(new MainCallback<List<CurrencyResponse>>() {
            @Override
            public void doSuccess(List<CurrencyResponse> list) {
                Collections.sort(list, (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
                fullCurrencyList = list;

                CurrencyResponse selectedCurrency = (CurrencyResponse) getIntent().getSerializableExtra("selected_currency");
                selectedCurrencyId[0] = selectedCurrency != null ? selectedCurrency.getId() : null;

                adapter = new CurrencyAdapter(CurrencyActivity.this, list, new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        CurrencyResponse selected = adapter.getItem(position);
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("selected_currency", selected);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override public void onItemDelete(int position) {}
                });

                adapter.setSelectedCurrencyId(selectedCurrencyId[0]);
                viewBinding.rcvCurrencyList.setLayoutManager(new LinearLayoutManager(CurrencyActivity.this));
                viewBinding.rcvCurrencyList.setAdapter(adapter);
            }

            @Override public void doError(Throwable error) {}
            @Override public void doFail() {}
            @Override public void doSuccess() {}
        });
    }

    private void filterCurrencyList(String keyword) {
        if (fullCurrencyList == null || adapter == null) return;

        List<CurrencyResponse> filtered = new java.util.ArrayList<>();
        for (CurrencyResponse currency : fullCurrencyList) {
            if (currency.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filtered.add(currency);
            }
        }

        adapter.updateData(filtered);
    }

}