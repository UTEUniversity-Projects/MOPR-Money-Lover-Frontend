package com.moneylover.ui.main.app.overview;

import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.library.baseAdapters.BR;

import com.moneylover.R;
import com.moneylover.databinding.FragmentOverviewBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

public class OverviewFragment extends BaseFragment<FragmentOverviewBinding, OverviewViewModel> {

    ToolTipsManager mToolTipsManager;

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_overview;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        mToolTipsManager = new ToolTipsManager();
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    public void showTooltip() {
        ViewGroup rootView = (ViewGroup) binding.getRoot().findViewById(R.id.root_content);

        ToolTip.Builder builder = new ToolTip.Builder(
                requireContext(),
                binding.ivInfo,
                rootView,
                ContextCompat.getString(requireContext(), R.string.balance_tooltip),
                ToolTip.POSITION_BELOW
        );

        builder.setAlign(ToolTip.ALIGN_CENTER);
        builder.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black));
        builder.setTextAppearance(R.style.TooltipTextAppearance);
        builder.setMaxWidth(400);

        mToolTipsManager.show(builder.build());

        new Handler(Looper.getMainLooper()).postDelayed(() ->
                mToolTipsManager.findAndDismiss(binding.ivInfo), 2000
        );
    }

}