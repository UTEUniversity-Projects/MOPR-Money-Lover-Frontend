package com.moneylover.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.moneylover.R;

public class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                hideKeyboard(v);
                v.performClick();
            }
            return false;
        });
    }

    /**
     * Chuyển đổi Fragment với ID container tùy chỉnh
     *
     * @param containerId ID của container chứa Fragment
     * @param fragment Fragment mới cần thay thế
     */
    protected void navigateToFragment(@IdRes int containerId, Fragment fragment) {
        navigateToFragment(containerId, fragment, R.anim.fade_in_animation, R.anim.fade_out_animation);
    }

    /**
     * Chuyển đổi Fragment với ID container và animation tùy chỉnh
     *
     * @param containerId ID của container chứa Fragment
     * @param fragment Fragment mới cần thay thế
     * @param enterAnim Animation khi vào
     * @param exitAnim Animation khi ra
     */
    protected void navigateToFragment(@IdRes int containerId, Fragment fragment, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        if (getActivity() == null) return;

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void hideKeyboard(View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        view.clearFocus();
    }
}
