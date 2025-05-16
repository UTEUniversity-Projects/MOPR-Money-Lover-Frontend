package com.moneylover.ui.main.auth;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.databinding.ActivityAuthBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;

public class AuthActivity extends BaseActivity<ActivityAuthBinding, AuthViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        String data = getIntent().getStringExtra("type");

        if (savedInstanceState == null && data != null) {
            if (data.equals(Constants.LOGIN_TYPE)) {
                replaceFragment(new LoginFragment(), false);
            } else if (data.equals(Constants.REGISTER_TYPE)) {
                replaceFragment(new RegisterFragment(), false);
            }
        }

        viewBinding.authLayout.setOnClickListener(v -> {
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                currentFocus.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

//    public void onBack() {
//        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
//        if (currentFragment instanceof LoginFragment) {
//            Intent intent = new Intent(this, OnboardingActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
//        } else
//            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//            overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
//        } else {
//            finish();
//            overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
//        }
//    }

    public void onBack() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(viewBinding.fragmentContainer.getId());
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        transaction.replace(viewBinding.fragmentContainer.getId(), fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewBinding = null;
    }

}
