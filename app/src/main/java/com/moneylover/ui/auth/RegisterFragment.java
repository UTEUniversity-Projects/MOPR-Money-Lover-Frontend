package com.moneylover.ui.auth;

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
import com.moneylover.databinding.FragmentRegisterBinding;
import com.moneylover.ui.BaseFragment;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterFragment extends BaseFragment {
    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnLogin = binding.btnLogin;
        MaterialButton btnRegister = binding.btnRegister;

        TextInputLayout textInputEmail = binding.textInputEmail;
        TextInputLayout textInputPassword = binding.textInputPassword;
        TextInputLayout textInputRePassword = binding.textInputRePassword;

        TextInputEditText edtEmail = binding.edtEmail;
        TextInputEditText edtPassword = binding.edtPassword;
        TextInputEditText edtRePassword = binding.edtRePassword;

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

        edtRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = edtPassword.getText().toString().trim();
                String rePassword = s.toString().trim();

                if (!rePassword.isEmpty() && !rePassword.equals(password)) {
                    textInputRePassword.setError("Mật khẩu nhập lại không khớp!");
                    textInputRePassword.setErrorEnabled(true);
                } else {
                    textInputRePassword.setError(null);
                    textInputRePassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        btnLogin.setOnClickListener(v -> navigateToFragment(R.id.fragmentContainer, new LoginFragment()));

        btnRegister.setOnClickListener(v -> {
            String email = Objects.requireNonNull(edtEmail.getText()).toString().trim();
            String password = edtPassword.getText().toString().trim();
            String rePassword = edtRePassword.getText().toString().trim();

            boolean isValid = true;

            if (email.isEmpty()) {
                textInputEmail.setError("Email không được để trống!");
                textInputEmail.setErrorEnabled(true);
                isValid = false;
            } else if (!Pattern.matches(Constants.EMAIL_REGEX, email)) {
                textInputEmail.setError("Email không hợp lệ!");
                textInputEmail.setErrorEnabled(true);
                isValid = false;
            } else {
                textInputEmail.setError(null);
                textInputEmail.setErrorEnabled(false);
            }

            if (password.isEmpty()) {
                textInputPassword.setError("Mật khẩu không được để trống!");
                textInputPassword.setErrorEnabled(true);
                isValid = false;
            } else if (password.length() < 8) {
                textInputPassword.setError("Mật khẩu phải có ít nhất 8 ký tự!");
                textInputPassword.setErrorEnabled(true);
                isValid = false;
            } else if (!Pattern.matches(Constants.PASSWORD_REGEX, password)) {
                textInputPassword.setError("Mật khẩu phải chứa ít nhất 1 ký tự in hoa, 1 ký tự đặc biệt và 1 ký tự số!");
                textInputPassword.setErrorEnabled(true);
                isValid = false;
            } else {
                textInputPassword.setError(null);
                textInputPassword.setErrorEnabled(false);
            }

            if (rePassword.isEmpty()) {
                textInputRePassword.setError("Vui lòng nhập lại mật khẩu!");
                textInputRePassword.setErrorEnabled(true);
                isValid = false;
            } else if (!rePassword.equals(password)) {
                textInputRePassword.setError("Mật khẩu nhập lại không khớp!");
                textInputRePassword.setErrorEnabled(true);
                isValid = false;
            } else {
                textInputRePassword.setError(null);
                textInputRePassword.setErrorEnabled(false);
            }

            if (isValid) {
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
