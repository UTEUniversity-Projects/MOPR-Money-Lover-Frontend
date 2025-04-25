package com.moneylover.ui.main.app.account;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.FragmentAccountBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.app.AppActivity;
import com.moneylover.ui.main.onboarding.OnboardingActivity;
import com.moneylover.utils.NavigationUtils;

public class AccountFragment extends BaseFragment<FragmentAccountBinding, AccountViewModel> {


    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
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

    public void onLogoutClick() {
        viewModel.removeAccessToken();
        viewModel.showSuccessMessage("Đăng xuất thành công !");
        NavigationUtils.navigateToActivityClearStack((AppActivity) getActivity(), OnboardingActivity.class, R.anim.slide_up_animation, R.anim.slide_down_animation);
    }
}