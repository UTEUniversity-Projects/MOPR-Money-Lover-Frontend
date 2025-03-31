package com.moneylover.ui.main.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.databinding.FragmentLoginBinding;
import com.moneylover.ui.base.BaseFragment2;

import java.util.regex.Pattern;

public class LoginFragment extends BaseFragment2 {
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnLogin = binding.btnLogin;
        MaterialButton btnRegister = binding.btnRegister;
        MaterialButton btnForgotPassword = binding.btnForgotPassword;

        TextInputLayout textInputEmail = binding.textInputEmail;
        TextInputEditText edtEmail = binding.edtEmail;

        TextInputLayout textInputPassword = binding.textInputPassword;
        TextInputEditText edtPassword = binding.edtPassword;

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if (!email.isEmpty() && Pattern.matches(Constants.EMAIL_REGEX, email)) {
                    textInputEmail.setError(null);
                    textInputEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString().trim();

                if (password.isEmpty()) {
                    textInputPassword.setError("Mật khẩu không được để trống !");
                    textInputPassword.setErrorEnabled(true);
                } else if (password.length() < 8) {
                    textInputPassword.setError("Mật khẩu phải có ít nhất 8 ký tự !");
                    textInputPassword.setErrorEnabled(true);
                } else if (!Pattern.matches(Constants.PASSWORD_REGEX, password)) {
                    textInputPassword.setError("Mật khẩu phải chứa ít nhất 1 ký tự in hoa, 1 ký tự đặc biệt và 1 ký tự số !");
                    textInputPassword.setErrorEnabled(true);
                } else {
                    textInputPassword.setError(null);
                    textInputPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            boolean hasError = false;

            if (email.isEmpty()) {
                textInputEmail.setError("Email là bắt buộc !");
                textInputEmail.setErrorEnabled(true);
                hasError = true;
            } else if (!Pattern.matches(Constants.EMAIL_REGEX, email)) {
                textInputEmail.setError("Email không đúng định dạng !");
                textInputEmail.setErrorEnabled(true);
                hasError = true;
            }

            if (password.isEmpty()) {
                textInputPassword.setError("Mật khẩu là bắt buộc !");
                textInputPassword.setErrorEnabled(true);
                hasError = true;
            } else if (password.length() < 6) {
                textInputPassword.setError("Mật khẩu phải có ít nhất 6 ký tự !");
                textInputPassword.setErrorEnabled(true);
                hasError = true;
            }

            if (!hasError) {
            }
        });

        btnRegister.setOnClickListener(v -> navigateToFragment(R.id.fragmentContainer, new RegisterFragment()));

        btnForgotPassword.setOnClickListener(v -> navigateToFragment(R.id.fragmentContainer, new ForgotPasswordFragment()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
