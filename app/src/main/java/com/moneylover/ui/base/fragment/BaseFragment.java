package com.moneylover.ui.base.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

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
                viewModel.mErrorMessage.setValue(null);
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View focusedView = requireActivity().getCurrentFocus();
                if (focusedView instanceof EditText) {
                    Rect outRect = new Rect();
                    focusedView.getGlobalVisibleRect(outRect);
                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        focusedView.clearFocus();
                        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                        }
                    }
                }
            }
            return false;
        });
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
        if (!isAdded() || getActivity() == null || getActivity().isFinishing()) return;

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        progressDialog = DialogUtils.createDialogLoading(requireContext(), msg);

        try {
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
