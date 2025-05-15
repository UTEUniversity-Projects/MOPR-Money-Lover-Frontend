package com.moneylover.ui.main.app.account;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.databinding.FragmentAccountBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.MainActivity;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.ui.main.app.AppActivity;
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
        viewModel.doLogOut(new MainCallback<ResponseWrapper<Void>>() {
            @Override
            public void doError(Throwable error) {
                viewModel.showErrorMessage("Đăng xuất thất bại");
            }

            @Override
            public void doSuccess() {
                viewModel.showSuccessMessage("Đăng xuất thành công");
                viewModel.removeAccessToken();
                NavigationUtils.navigateToActivityClearStack((AppActivity) getActivity(), MainActivity.class, R.anim.slide_up_animation, R.anim.slide_down_animation);
            }

            @Override
            public void doFail() {
                viewModel.showErrorMessage("Đăng xuất thất bại");
            }
        });
    }
}