package com.moneylover.ui.main.auth;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.constants.HttpStatusCode;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.ForgotPasswordOtpVerificationRequest;
import com.moneylover.data.model.api.response.ForgotPasswordOtpVerificationResponse;
import com.moneylover.databinding.FragmentForgotPasswordOtpVerificationBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.utils.DeviceUtils;
import com.moneylover.utils.FormUtils;
import com.moneylover.utils.NavigationUtils;

import java.net.SocketTimeoutException;

public class ForgotPasswordOtpVerificationFragment extends BaseFragment<FragmentForgotPasswordOtpVerificationBinding, ForgotPasswordOtpVerificationViewModel> {


    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forgot_password_otp_verification;
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
        setupOtpDeleteHandlers();
    }

    private void setupOtpDeleteHandlers() {
        View.OnKeyListener keyListener = (v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                EditText current = (EditText) v;

                if (current.getText().toString().isEmpty()) {
                    if (current == binding.otp6) binding.otp5.requestFocus();
                    else if (current == binding.otp5) binding.otp4.requestFocus();
                    else if (current == binding.otp4) binding.otp3.requestFocus();
                    else if (current == binding.otp3) binding.otp2.requestFocus();
                    else if (current == binding.otp2) binding.otp1.requestFocus();
                }
            }
            return false;
        };

        binding.otp1.setOnKeyListener(keyListener);
        binding.otp2.setOnKeyListener(keyListener);
        binding.otp3.setOnKeyListener(keyListener);
        binding.otp4.setOnKeyListener(keyListener);
        binding.otp5.setOnKeyListener(keyListener);
        binding.otp6.setOnKeyListener(keyListener);
    }

    public boolean validatePassword() {
        return FormUtils.validatePassword(binding.edtPassword, binding.textInputPassword,
                "Mật khẩu là bắt buộc !",
                "Mật khẩu phải từ 8 ký tự trở lên !",
                "Mật khẩu phải có ít nhất 1 ký tự chữ hoa, 1 ký tự chữ thường, 1 ký tự số và 1 ký tự đặc biệt !");
    }

    public boolean validateConfirmPassword() {

        return FormUtils.validateConfirmPassword(binding.edtPassword, binding.edtConfirmPassword,
                binding.textInputConfirmPassword,
                "Mật khẩu xác nhận không được để trống !",
                "Mật khẩu xác nhận không khớp !");
    }

    public void onOtpTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 1) {
            if (binding.otp1.hasFocus()) {
                binding.otp2.requestFocus();
            } else if (binding.otp2.hasFocus()) {
                binding.otp3.requestFocus();
            } else if (binding.otp3.hasFocus()) {
                binding.otp4.requestFocus();
            } else if (binding.otp4.hasFocus()) {
                binding.otp5.requestFocus();
            } else if (binding.otp5.hasFocus()) {
                binding.otp6.requestFocus();
            }
        }

        if (s.length() == 0) {
            if (binding.otp6.hasFocus()) {
                binding.otp5.requestFocus();
            } else if (binding.otp5.hasFocus()) {
                binding.otp4.requestFocus();
            } else if (binding.otp4.hasFocus()) {
                binding.otp3.requestFocus();
            } else if (binding.otp3.hasFocus()) {
                binding.otp2.requestFocus();
            } else if (binding.otp2.hasFocus()) {
                binding.otp1.requestFocus();
            }
        }
    }

    public void onPasswordTextChanged(CharSequence s, int start, int before, int count) {
        validatePassword();
    }

    public void onConfirmPasswordTextChanged(CharSequence s, int start, int before, int count) {
        validateConfirmPassword();
    }

    public void onResetPasswordClick() {
        String forgotPasswordToken = viewModel.getForgotPasswordToken();
        StringBuilder sb = new StringBuilder();
        sb.append(binding.otp1.getText().toString())
                .append(binding.otp2.getText().toString())
                .append(binding.otp3.getText().toString())
                .append(binding.otp4.getText().toString())
                .append(binding.otp5.getText().toString())
                .append(binding.otp6.getText().toString());
        boolean isValid = sb.toString().length() == 6 && validatePassword() && validateConfirmPassword();

        if (!isValid) {
            viewModel.showWarningMessage("Thông tin không hợp lệ !");
            return;
        }

        ForgotPasswordOtpVerificationRequest request = ForgotPasswordOtpVerificationRequest.builder().otp(sb.toString()).newPassword(binding.edtPassword.getText().toString().trim()).token(forgotPasswordToken).build();

        viewModel.doForgotPasswordOtpVerification(new MainCallback< ResponseWrapper< ForgotPasswordOtpVerificationResponse > > () {

            @Override
            public void doError(Throwable error) {
                viewModel.hideLoading();

                if (!DeviceUtils.isNetworkAvailable(requireContext())) {
                    viewModel.showErrorMessage("Vui lòng kiểm tra kết nối mạng !");
                    return;
                }

                if (error instanceof SocketTimeoutException) {
                    viewModel.showErrorMessage("Có lỗi kết nối đến server !");
                    return;
                }

                if (error.getMessage() != null && error.getMessage().contains(HttpStatusCode.BAD_REQUEST.getCode())) {
                    viewModel.showErrorMessage("Mã OTP không hợp lệ !");
                    return;
                }

            }

            @Override
            public void doSuccess() {

            }

            @Override
            public void doSuccess(ResponseWrapper<ForgotPasswordOtpVerificationResponse> response) {
                viewModel.hideLoading();
                viewModel.removeForgotPasswordToken();
                viewModel.showSuccessMessage("Đặt lại mật khẩu thành công !");
                NavigationUtils.navigateToFragmentClearBackStack((AuthActivity)getActivity(), R.id.fragmentContainer, LoginFragment.class);
            }

            @Override
            public void doFail() {

            }
        }, request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}