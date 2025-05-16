package com.moneylover.ui.main.onboarding;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.moneylover.BR;
import com.moneylover.R;
import com.moneylover.constants.Constants;
import com.moneylover.data.model.Onboarding;
import com.moneylover.databinding.ActivityOnboardingBinding;
import com.moneylover.di.component.ActivityComponent;
import com.moneylover.ui.base.activity.BaseActivity;
import com.moneylover.ui.main.auth.AuthActivity;
import com.moneylover.utils.NavigationUtils;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends BaseActivity<ActivityOnboardingBinding, OnboardingViewModel> {

    private List<Onboarding> onboardingList;
    private OnboardingAdapter onboardingAdapter;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

        onboardingList = getOnboardingItems();
        onboardingAdapter = new OnboardingAdapter(onboardingList);
        viewBinding.viewPager2.setAdapter(onboardingAdapter);

        viewBinding.circleIndicator.setViewPager(viewBinding.viewPager2);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewBinding.viewPager2.getCurrentItem();
                if (currentItem == onboardingList.size() - 1) {
                    viewBinding.viewPager2.setCurrentItem(0);
                } else {
                    viewBinding.viewPager2.setCurrentItem(currentItem + 1);
                }
            }
        };

        viewBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });
    }

    public void onLoginClick() {
        Bundle bundle = new Bundle();
        bundle.putString("type", Constants.LOGIN_TYPE);
        NavigationUtils.navigateToActivityDefault(this, AuthActivity.class, bundle);
    }

    public void onRegisterClick() {
        Bundle bundle = new Bundle();
        bundle.putString("type", Constants.REGISTER_TYPE);
        NavigationUtils.navigateToActivityDefault(this, AuthActivity.class, bundle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_onboarding;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    private List<Onboarding> getOnboardingItems() {
        List<Onboarding> imagesList = new ArrayList<>();
        imagesList.add(new Onboarding(R.drawable.onboarding1, "1", "1"));
        imagesList.add(new Onboarding(R.drawable.onboarding2, "2", "2"));
        imagesList.add(new Onboarding(R.drawable.onboarding3, "3", "3"));
        imagesList.add(new Onboarding(R.drawable.onboarding1, "1", "1"));
        imagesList.add(new Onboarding(R.drawable.onboarding2, "2", "2"));
        imagesList.add(new Onboarding(R.drawable.onboarding3, "3", "3"));
        return imagesList;
    }
}