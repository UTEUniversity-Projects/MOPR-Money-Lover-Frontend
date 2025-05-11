package com.moneylover.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.data.model.api.response.WalletResponse;
import com.moneylover.databinding.ActivityMainBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.MainCallback;
import com.moneylover.ui.main.app.AppActivity;
import com.moneylover.ui.main.app.CreateFirstWalletActivity;
import com.moneylover.ui.main.onboarding.OnboardingActivity;
import com.moneylover.utils.JwtUtils;
import com.moneylover.utils.NavigationUtils;

import java.util.List;

import timber.log.Timber;

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
        super.onCreate(savedInstanceState);
        showStatusBar();

        String accessToken = viewModel.getRepository().getToken();
        if (accessToken == null) {
            NavigationUtils.navigateToActivityClearStack(MainActivity.this, OnboardingActivity.class, R.anim.slide_up_animation, R.anim.slide_down_animation);
        } else if (JwtUtils.isTokenExpired(accessToken)) {
            viewModel.getRepository().setToken(null);
            NavigationUtils.navigateToActivityClearStack(MainActivity.this, OnboardingActivity.class, R.anim.slide_up_animation, R.anim.slide_down_animation);
        } else {
            viewModel.doGetWalletList(new MainCallback<List<WalletResponse>>() {
                @Override
                public void doError(Throwable error) {
                    Timber.tag("MainActivity").d("doError: %s", error);
                }

                @Override
                public void doSuccess() {

                }

                @Override
                public void doSuccess(List<WalletResponse> walletResponses) {
                    Timber.tag("MainActivity").d("doSuccess: %s", walletResponses);
                    if (walletResponses.isEmpty()) {
                        NavigationUtils.navigateToActivityDefaultClearStack(MainActivity.this, CreateFirstWalletActivity.class, null);
                    } else {
                        NavigationUtils.navigateToActivityDefaultClearStack(MainActivity.this, AppActivity.class, null);
                    }
                }

                @Override
                public void doFail() {

                }
            });
        }

    }

}
