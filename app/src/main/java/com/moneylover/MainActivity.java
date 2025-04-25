package com.moneylover;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.splashscreen.SplashScreen;

import com.moneylover.constants.Constants;
import com.moneylover.databinding.ActivityMainBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.app.AppActivity;
import com.moneylover.ui.main.onboarding.OnboardingActivity;
import com.moneylover.utils.JwtUtils;
import com.moneylover.utils.NavigationUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        showStatusBar();

        viewModel.showNormalMessage("Welcome to Money Lover");

        String accessToken = viewModel.getRepository().getSharedPreferences().getStringVal(Constants.ACCESS_TOKEN);
        if (accessToken == null) {
            NavigationUtils.navigateToActivityClearStack(MainActivity.this, OnboardingActivity.class, R.anim.slide_up_animation, R.anim.slide_down_animation);
        } else if (JwtUtils.isTokenExpired(accessToken)) {
            viewModel.getRepository().getSharedPreferences().removeKey(Constants.ACCESS_TOKEN);
            NavigationUtils.navigateToActivityClearStack(MainActivity.this, OnboardingActivity.class, R.anim.slide_up_animation, R.anim.slide_down_animation);
        } else {
            NavigationUtils.navigateToActivityClearStack(MainActivity.this, AppActivity.class, R.anim.slide_up_animation, R.anim.slide_down_animation);
        }

    }

}
