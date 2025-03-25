package com.moneylover.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moneylover.R;
import com.moneylover.databinding.FragmentForgotPasswordBinding;
import com.moneylover.ui.BaseFragment;

public class ForgotPasswordFragment extends BaseFragment {

    private FragmentForgotPasswordBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);

        binding.btnSendOTP.setOnClickListener(v -> navigateToFragment(R.id.fragmentContainer, new OtpVerificationFragment()));

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
