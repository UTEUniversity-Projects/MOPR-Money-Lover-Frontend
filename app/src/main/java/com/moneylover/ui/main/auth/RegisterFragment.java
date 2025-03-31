package com.moneylover.ui.main.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.databinding.FragmentRegisterBinding;
import com.moneylover.ui.base.BaseFragment2;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterFragment extends BaseFragment2 {
    private FragmentRegisterBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = s.toString().trim();
                if (!email.isEmpty() && Pattern.matches(Constants.EMAIL_REGEX, email)) {
                    binding.textInputEmail.setError(null);
                    binding.textInputEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString().trim();

                if (password.isEmpty()) {
                    binding.textInputPassword.setError("Mật khẩu không được để trống !");
                    binding.textInputPassword.setErrorEnabled(true);
                } else if (password.length() < 8) {
                    binding.textInputPassword.setError("Mật khẩu phải có ít nhất 8 ký tự !");
                    binding.textInputPassword.setErrorEnabled(true);
                } else if (!Pattern.matches(Constants.PASSWORD_REGEX, password)) {
                    binding.textInputPassword.setError("Mật khẩu phải chứa ít nhất 1 ký tự in hoa, 1 ký tự đặc biệt và 1 ký tự số !");
                    binding.textInputPassword.setErrorEnabled(true);
                } else {
                    binding.textInputPassword.setError(null);
                    binding.textInputPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.edtRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.edtPassword.getText().toString().trim();
                String rePassword = s.toString().trim();

                if (!rePassword.isEmpty() && !rePassword.equals(password)) {
                    binding.textInputRePassword.setError("Mật khẩu nhập lại không khớp!");
                    binding.textInputRePassword.setErrorEnabled(true);
                } else {
                    binding.textInputRePassword.setError(null);
                    binding.textInputRePassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        binding.btnLogin.setOnClickListener(v -> navigateToFragment(R.id.fragmentContainer, new LoginFragment()));

        binding.btnRegister.setOnClickListener(v -> {
            String email = Objects.requireNonNull(binding.edtEmail.getText()).toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            String rePassword = binding.edtRePassword.getText().toString().trim();

            boolean isValid = true;

            if (email.isEmpty()) {
                binding.textInputEmail.setError("Email không được để trống!");
                binding.textInputEmail.setErrorEnabled(true);
                isValid = false;
            } else if (!Pattern.matches(Constants.EMAIL_REGEX, email)) {
                binding.textInputEmail.setError("Email không hợp lệ!");
                binding.textInputEmail.setErrorEnabled(true);
                isValid = false;
            } else {
                binding.textInputEmail.setError(null);
                binding.textInputEmail.setErrorEnabled(false);
            }

            if (password.isEmpty()) {
                binding.textInputPassword.setError("Mật khẩu không được để trống!");
                binding.textInputPassword.setErrorEnabled(true);
                isValid = false;
            } else if (password.length() < 8) {
                binding.textInputPassword.setError("Mật khẩu phải có ít nhất 8 ký tự!");
                binding.textInputPassword.setErrorEnabled(true);
                isValid = false;
            } else if (!Pattern.matches(Constants.PASSWORD_REGEX, password)) {
                binding.textInputPassword.setError("Mật khẩu phải chứa ít nhất 1 ký tự in hoa, 1 ký tự đặc biệt và 1 ký tự số!");
                binding.textInputPassword.setErrorEnabled(true);
                isValid = false;
            } else {
                binding.textInputPassword.setError(null);
                binding.textInputPassword.setErrorEnabled(false);
            }

            if (rePassword.isEmpty()) {
                binding.textInputRePassword.setError("Vui lòng nhập lại mật khẩu!");
                binding.textInputRePassword.setErrorEnabled(true);
                isValid = false;
            } else if (!rePassword.equals(password)) {
                binding.textInputRePassword.setError("Mật khẩu nhập lại không khớp!");
                binding.textInputRePassword.setErrorEnabled(true);
                isValid = false;
            } else {
                binding.textInputRePassword.setError(null);
                binding.textInputRePassword.setErrorEnabled(false);
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
