package com.moneylover.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.moneylover.R;

public class BaseActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() instanceof EditText) {
                EditText currentEditText = (EditText) getCurrentFocus();
                if (!isTouchInsideView(currentEditText, event)) {
                    hideKeyboard();
                    currentEditText.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean isTouchInsideView(EditText view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        float x = event.getRawX();
        float y = event.getRawY();
        return x >= location[0] && x <= location[0] + view.getWidth() &&
                y >= location[1] && y <= location[1] + view.getHeight();
    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
