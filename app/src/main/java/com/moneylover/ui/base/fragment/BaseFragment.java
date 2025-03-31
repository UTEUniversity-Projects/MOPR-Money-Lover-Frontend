package com.moneylover.ui.base.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.moneylover.MVVMApplication;
import com.moneylover.R;
import com.moneylover.di.component.DaggerFragmentComponent;
import com.moneylover.di.component.FragmentComponent;
import com.moneylover.di.module.FragmentModule;
import com.moneylover.utils.DialogUtils;

import javax.inject.Inject;
import javax.inject.Named;

public abstract class BaseFragment<B extends ViewDataBinding, V extends BaseFragmentViewModel> extends Fragment {

    protected B binding;
    @Inject
    protected V viewModel;

    private Dialog progressDialog;


    @Named("access_token")
    @Inject
    protected String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        performDependencyInjection(getBuildComponent());
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        binding.setVariable(getBindingVariable(), viewModel);
        performDataBinding();
        viewModel.setToken(token);
        viewModel.mErrorMessage.observe(getViewLifecycleOwner(), toastMessage -> {
            if (toastMessage != null) {
                toastMessage.showMessage(requireContext());
            }
        });
        viewModel.mIsLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {

            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (((ObservableBoolean) sender).get()) {
                    showProgressbar(getResources().getString(R.string.msg_loading));
                } else {
                    hideProgress();
                }
            }
        });
        setupHideKeyboardOnTouch(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus(); // Lấy focus hiện tại từ Activity
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupHideKeyboardOnTouch(View rootView) {
        rootView.setOnTouchListener((v, event) -> {
            hideKeyboard();
            return false;
        });
    }

    public abstract int getBindingVariable();

    protected abstract int getLayoutId();

    protected abstract void performDataBinding();

    protected abstract void performDependencyInjection(FragmentComponent buildComponent);

    private FragmentComponent getBuildComponent() {
        return DaggerFragmentComponent.builder().appComponent(((MVVMApplication) requireActivity().getApplication()).getAppComponent()).fragmentModule(new FragmentModule(this)).build();
    }

    public void showProgressbar(String msg) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = DialogUtils.createDialogLoading(requireContext(), msg);
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
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
        if (getActivity() == null) return;

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
