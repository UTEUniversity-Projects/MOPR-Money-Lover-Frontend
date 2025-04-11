package com.moneylover.ui.main.auth;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.databinding.FragmentLoginBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.app.AppActivity;
import com.moneylover.utils.FormUtils;
import com.moneylover.utils.NavigationUtils;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
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
        return FormUtils.validateEmail(binding.edtEmail, binding.textInputEmail, "Email là bắt buộc !", "Email không đúng định dạng !");
    }

    private boolean validatePassword() {
        return FormUtils.validatePassword(binding.edtPassword, binding.textInputPassword,
                "Mật khẩu là bắt buộc !",
                "Mật khẩu phải từ 8 ký tự trở lên !",
                "Mật khẩu phải có ít nhất 1 ký tự chữ hoa, 1 ký tự chữ thường, 1 ký tự số và 1 ký tự đặc biệt !");
    }

    public void onLoginClick() {
//        if (!validateEmail() || !validatePassword()) {
//            viewModel.showWarningMessage("Email hoặc mật khẩu không hợp hệ !");
//            return;
//        }
//
//        LoginRequest request = LoginRequest.builder().email(binding.edtEmail.getText().toString().trim()).password(binding.edtPassword.getText().toString().trim()).build();
//        viewModel.doLogin(new MainCallback<ResponseWrapper<LoginResponse>>() {
//            @Override
//            public void doError(Throwable error) {
//                Timber.tag("LoginViewModel").e(error);
//                viewModel.hideLoading();
//                if (!DeviceUtils.isNetworkAvailable(requireContext())) {
//                    viewModel.showErrorMessage("Vui lòng kiểm tra kết nối mạng !");
//                    return;
//                }
//
//                if (error instanceof SocketTimeoutException) {
//                    viewModel.showErrorMessage("Có lỗi kết nối đến server !");
//                    return;
//                }
//
//                if (error.getMessage() != null && error.getMessage().contains(HttpStatusCode.UNAUTHORIZED.getCode())) {
//                    viewModel.showErrorMessage("Email hoặc mật khẩu không đúng !");
//                    return;
//                }
//
//                viewModel.showErrorMessage("Đăng nhập thất bại !");
//            }
//
//            @Override
//            public void doSuccess() {
//            }
//
//            @Override
//            public void doSuccess(ResponseWrapper<LoginResponse> response) {
//                viewModel.hideLoading();
//                hideKeyboard();
//                Timber.tag("LoginViewModel").d("Response: %s", response.toString());
//                viewModel.showSuccessMessage("Đăng nhập thành công !");
//                viewModel.setAccessToken(response.getData().getAccessToken());
//                NavigationUtils.navigateToActivityClearStack((AuthActivity) getActivity(), AppActivity.class, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//            }
//
//            @Override
//            public void doFail() {
//            }
//
//        }, request);
        NavigationUtils.navigateToActivityClearStack((AuthActivity) getActivity(), AppActivity.class);
    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count) {
        validateEmail();
    }

    public void onPasswordTextChanged(CharSequence s, int start, int before, int count) {
        validatePassword();
    }

    public void onRegisterClick() {
        NavigationUtils.navigateToFragment((AuthActivity) getActivity(), R.id.fragmentContainer, RegisterFragment.class);
    }

    public void onForgotPasswordClick() {
        NavigationUtils.navigateToFragment((AuthActivity) getActivity(), R.id.fragmentContainer, ForgotPasswordFragment.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
