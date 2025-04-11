package com.moneylover.ui.main.auth;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.constants.ErrorCode;
import com.moneylover.data.model.api.ResponseWrapper;
import com.moneylover.data.model.api.request.RequestRegisterRequest;
import com.moneylover.data.model.api.response.RequestRegisterResponse;
import com.moneylover.databinding.FragmentRegisterBinding;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.ui.base.fragment.BaseFragment;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.utils.DeviceUtils;
import com.moneylover.utils.FormUtils;
import com.moneylover.utils.NavigationUtils;
import com.moneylover.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel> {

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
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

    public boolean validateEmail() {
        return FormUtils.validateEmail(binding.edtEmail, binding.textInputEmail, "Email là bắt buộc !", "Email không đúng định dạng !");
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

    public void onEmailTextChanged(CharSequence s, int start, int before, int count) {
        validateEmail();
    }

    public void onPasswordTextChanged(CharSequence s, int start, int before, int count) {
        validatePassword();
    }

    public void onConfirmPasswordTextChanged(CharSequence s, int start, int before, int count) {
        validateConfirmPassword();
    }

    public void onLoginClick() {
        NavigationUtils.navigateToFragment((AuthActivity) getActivity(), R.id.fragmentContainer, LoginFragment.class);
    }

    public void onRegisterClick() {
//        viewModel.initializeRecaptchaClient();
//        viewModel.executeRecaptchaTask();

        if (!validateEmail() || !validatePassword() || !validateConfirmPassword()) {
            viewModel.showWarningMessage("Thông tin không hợp lệ !");
            return;
        }

        String email = Objects.requireNonNull(binding.edtEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.edtPassword.getText()).toString().trim();

        RequestRegisterRequest request = RequestRegisterRequest.builder().email(email).password(password).build();

        viewModel.doRequestRegister(new MainCallback<ResponseWrapper<RequestRegisterResponse>>() {
            @Override
            public void doError(Throwable error) {
                Timber.tag("RegisterFragment").e("Response: %s", error.getMessage());
                viewModel.hideLoading();
                if (!DeviceUtils.isNetworkAvailable(requireContext())) {
                    viewModel.showErrorMessage("Vui lòng kiểm tra kết nối mạng !");
                    return;
                }

                if (NetworkUtils.checkNetworkError(error)) {
                    viewModel.showErrorMessage("Có lỗi kết nối đến server !");
                    return;
                }

                if (error instanceof HttpException) {
                    HttpException httpException = (HttpException) error;
                    ResponseBody errorBody = httpException.response().errorBody();

                    if (errorBody != null) {
                        try {
                            String errorJson = errorBody.string();
                            Timber.d("Error body: %s", errorJson);

                            JSONObject json = new JSONObject(errorJson);
                            String errorCode = json.optString("code");

                            Timber.d("Mã lỗi: %s", errorCode);

                            if (ErrorCode.ACCOUNT_EMAIL_EXISTED.getCode().equals(errorCode)) {
                                viewModel.showErrorMessage("Email đã tồn tại !");
                                binding.edtEmail.requestFocus();
                                binding.textInputEmail.setError("Email đã tồn tại !");
                                binding.textInputEmail.setErrorEnabled(true);
                            }

                        } catch (Exception e) {
                            Timber.e(e, "Lỗi khi parse errorBody");
                        }
                    }
                    return;
                }

                viewModel.showErrorMessage("Đăng ký thất bại !");
            }

            @Override
            public void doSuccess() {
            }

            @Override
            public void doSuccess(ResponseWrapper<RequestRegisterResponse> response) {
                viewModel.hideLoading();
                Timber.tag("RegisterFragment").d("Response: %s", response.toString());
                viewModel.showSuccessMessage("Mã OTP đã được gửi đến email !");
                viewModel.setRegisterToken(response.getData().getToken());
                NavigationUtils.navigateToFragmentClearBackStack((AuthActivity) getActivity(), R.id.fragmentContainer, RegisterOtpVerificationFragment.class);
            }

            @Override
            public void doFail() {
                Timber.tag("RegisterFragment").d("Response: %s", "doFail");
            }

        }, request);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}