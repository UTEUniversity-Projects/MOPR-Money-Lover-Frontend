package com.moneylover.utils;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.moneylover.constants.Constants;

import java.util.Objects;
import java.util.regex.Pattern;

public class FormUtils {
    public static boolean validateEmail(TextInputEditText editText, TextInputLayout textInputLayout, String errorEmpty, String errorInvalid) {
        String email = Objects.requireNonNull(editText.getText()).toString().trim();

        if (email.isEmpty()) {
            textInputLayout.setError(errorEmpty);
            textInputLayout.setErrorEnabled(true);
            return false;
        }

        if (!Pattern.matches(Constants.EMAIL_REGEX, email)) {
            textInputLayout.setError(errorInvalid);
            textInputLayout.setErrorEnabled(true);
            return false;
        }

        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    public static boolean validatePassword(TextInputEditText editText, TextInputLayout textInputLayout, String errorEmpty, String errorLength, String errorInvalid) {
        String password = Objects.requireNonNull(editText.getText()).toString().trim();

        if (password.isEmpty()) {
            textInputLayout.setError(errorEmpty);
            textInputLayout.setErrorEnabled(true);
            return false;
        }

        if (password.length() < 8) {
            textInputLayout.setError(errorLength);
            textInputLayout.setErrorEnabled(true);
            return false;
        }

        if (!Pattern.matches(Constants.PASSWORD_REGEX, password)) {
            textInputLayout.setError(errorInvalid);
            textInputLayout.setErrorEnabled(true);
            return false;
        }

        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
        return true;
    }

    public static boolean validateConfirmPassword(TextInputEditText editTextPassword, TextInputEditText editTextConfirmPassword, TextInputLayout textInputLayout, String errorEmpty, String errorMismatch) {


        String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(editTextConfirmPassword.getText()).toString().trim();

        if (confirmPassword.isEmpty()) {
            textInputLayout.setError(errorEmpty);
            textInputLayout.setErrorEnabled(true);
            return false;
        } else if (!confirmPassword.equals(password)) {
            textInputLayout.setError(errorMismatch);
            textInputLayout.setErrorEnabled(true);
            return false;
        }

        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);

        return true;
    }
}
