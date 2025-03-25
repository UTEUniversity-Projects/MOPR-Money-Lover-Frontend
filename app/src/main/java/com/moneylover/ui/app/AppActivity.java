package com.moneylover.ui.app;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moneylover.R;
import com.moneylover.databinding.ActivityAppBinding;
import com.moneylover.ui.BaseActivity;

public class AppActivity extends BaseActivity {

    private ActivityAppBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.app, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;

        bottomNavigationView.setBackground(null);
        bottomNavigationView.setOnApplyWindowInsetsListener(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        navigateToFragment(R.id.frame_layout, new OverviewFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                fragment = new OverviewFragment();
            } else if (itemId == R.id.wallet) {
                fragment = new WalletFragment();
            } else if (itemId == R.id.budget) {
                fragment = new BudgetFragment();
            } else if (itemId == R.id.account) {
                fragment = new AccountFragment();
            }
            navigateToFragment(R.id.frame_layout, fragment);
            return true;
        });


    }
}
