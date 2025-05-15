package com.moneylover.ui.main.app;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityOptionsCompat;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.FragmentEventListBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;

public class EventListFragment extends BaseFragment<FragmentEventListBinding, EventListViewModel> {
    private ActivityResultLauncher<Intent> addEventLauncher;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_event_list;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);

        addEventLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String note = data.getStringExtra("note");
                            // Handle the note here
                        }
                    }
                }
        );

        binding.btnAddWallet.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddEventActivity.class);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                    getActivity(),
                    R.anim.slide_in_up,
                    R.anim.no_anim
            );

            addEventLauncher.launch(intent, options);
        });


    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}