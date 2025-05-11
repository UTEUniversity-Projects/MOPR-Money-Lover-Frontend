package com.moneylover.ui.main.auth;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.constants.HttpStatusCode;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.RequestResetPasswordRequest;
import com.moneylover.data.model.api.response.TokenResponse;
import com.moneylover.databinding.FragmentForgotPasswordBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.utils.DeviceUtils;
import com.moneylover.utils.FormUtils;
import com.moneylover.utils.NavigationUtils;
import com.moneylover.utils.NetworkUtils;

import timber.log.Timber;

public class ForgotPasswordFragment extends BaseFragment<FragmentForgotPasswordBinding, ForgotPasswordViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forgot_password;
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

    private boolean validateEmail() {
        return FormUtils.validateEmail(binding.edtEmail, binding.textInputEmail, "Email không được để trống !", "Email không đúng định dạng !");
    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count) {
        validateEmail();
    }

    public void onSendOtpClick() {
        if (!validateEmail()) {
            viewModel.showWarningMessage("Email không đúng định dạng !");
            return;
        };

        RequestResetPasswordRequest request = RequestResetPasswordRequest.builder().email(binding.edtEmail.getText().toString().trim()).build();

        viewModel.doRequestForgotPassword(new MainCallback<ResponseWrapper<TokenResponse>>() {
            @Override
            public void doError(Throwable error) {
                viewModel.hideLoading();
                Timber.tag("ForgotPasswordFragment").e("Error: %s", error.getMessage());

                if (!DeviceUtils.isNetworkAvailable(requireContext())) {
                    viewModel.showErrorMessage("Vui lòng kiểm tra kết nối mạng !");
                    return;
                }

                if (NetworkUtils.checkNetworkError(error)) {
                    viewModel.showErrorMessage("Có lỗi kết nối đến server !");
                    return;
                }

                if (error.getMessage() != null && error.getMessage().contains(HttpStatusCode.NOT_FOUND.getCode())) {
                    viewModel.showErrorMessage("Email không tồn tại !");
                    return;
                }
            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(ResponseWrapper<TokenResponse> response) {
                viewModel.hideLoading();
                viewModel.setForgotPasswordToken(response.getData().getToken());
                viewModel.showSuccessMessage("Mã OTP đã được gửi đến email !");
                NavigationUtils.navigateToFragment((AuthActivity) getActivity(), R.id.fragmentContainer, ForgotPasswordOtpVerificationFragment.class);
            }

            @Override
            public void doFail() {

            }
        }, request);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
