package com.moneylover.ui.main.app.transactionHistory.viewReport.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPager2Adapter extends FragmentStateAdapter {

    private final List<Class<? extends Fragment>> fragmentClasses;
    private final Map<Integer, Bundle> fragmentArguments;
    private final Map<Integer, Fragment> fragmentCache = new HashMap<>();

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, List<Class<? extends Fragment>> fragmentClasses) {
        super(fragmentActivity);
        this.fragmentClasses = fragmentClasses;
        this.fragmentArguments = new HashMap<>();
    }

    public void setFragmentArguments(int position, Bundle args) {
        fragmentArguments.put(position, args);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (fragmentCache.containsKey(position)) {
            return fragmentCache.get(position);
        }

        try {
            Fragment fragment = fragmentClasses.get(position).newInstance();

            if (fragmentArguments.containsKey(position)) {
                fragment.setArguments(fragmentArguments.get(position));
            }

            fragmentCache.put(position, fragment);
            return fragment;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create fragment for position " + position, e);
        }
    }

    public Fragment getFragment(int position) {
        return fragmentCache.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentClasses.size();
    }

}
