package com.moneylover.utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.moneylover.R;

public class NavigationUtils {

    // ============================= Activity Navigation =============================
    public static void navigateToActivityDefault(AppCompatActivity currentActivity, Class<?> targetActivity, Bundle data) {
        Intent intent = new Intent(currentActivity, targetActivity);
        if (data != null) {
            intent.putExtras(data);
        }
        currentActivity.startActivity(intent);
    }

    public static void navigateToActivityDefaultClearStack(AppCompatActivity currentActivity, Class<?> targetActivity, Bundle data) {
        Intent intent = new Intent(currentActivity, targetActivity);
        if (data != null) {
            intent.putExtras(data);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        currentActivity.startActivity(intent);

        currentActivity.finish();
    }


    // Method to navigate to an Activity without data and default animation
    public static void navigateToActivity(AppCompatActivity currentActivity, Class<?> targetActivity) {
        navigateToActivity(currentActivity, targetActivity, null, R.anim.fade_in_animation, R.anim.fade_out_animation);
    }

    // Method to navigate to an Activity with data and default animation
    public static void navigateToActivity(AppCompatActivity currentActivity, Class<?> targetActivity, Bundle data) {
        navigateToActivity(currentActivity, targetActivity, data, R.anim.fade_in_animation, R.anim.fade_out_animation);
    }

    // Method to navigate to an Activity and clear back stack
    public static void navigateToActivityClearStack(AppCompatActivity currentActivity, Class<?> targetActivity) {
        navigateToActivityClearStack(currentActivity, targetActivity, R.anim.fade_in_animation, R.anim.fade_out_animation);
    }

    // Method to navigate to an Activity and clear back stack
    public static void navigateToActivityClearStack(AppCompatActivity currentActivity, Class<?> targetActivity, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        Intent intent = new Intent(currentActivity, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
        currentActivity.overridePendingTransition(enterAnim, exitAnim);
        currentActivity.finish();
    }

    // Method to navigate to an Activity with data and default animation
    public static void navigateToActivityClearStack(AppCompatActivity currentActivity, Class<?> targetActivity, Bundle data) {
        navigateToActivity(currentActivity, targetActivity, data, R.anim.fade_in_animation, R.anim.fade_out_animation);
    }

    // Method to navigate to an Activity with data and custom animations
    public static void navigateToActivity(AppCompatActivity currentActivity, Class<?> targetActivity, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        Intent intent = new Intent(currentActivity, targetActivity);
        currentActivity.startActivity(intent);
        currentActivity.overridePendingTransition(enterAnim, exitAnim);
    }

    // Method to navigate to an Activity with data and custom animations
    public static void navigateToActivity(AppCompatActivity currentActivity, Class<?> targetActivity, Bundle data, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        Intent intent = new Intent(currentActivity, targetActivity);
        if (data != null) {
            intent.putExtras(data);
        }
        currentActivity.startActivity(intent);
        currentActivity.overridePendingTransition(enterAnim, exitAnim);
    }

    // Method to go back to the previous Activity
    public static void goBack(AppCompatActivity activity) {
        activity.onBackPressed();  // Go back to the previous Activity in the stack
    }

    // ================================ Fragment Navigation=============================

    // Method to navigate to a Fragment with container ID, data (Bundle) and custom animations
    public static void navigateToFragment(AppCompatActivity activity, @IdRes int containerId, Class<? extends Fragment> fragmentClass, Bundle data, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        try {
            Fragment fragment = fragmentClass.getDeclaredConstructor().newInstance();  // Create fragment instance from class
            if (data != null) {
                fragment.setArguments(data);  // Pass data to the Fragment
            }
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim);
            transaction.replace(containerId, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate fragment " + fragmentClass.getSimpleName(), e);
        }
    }

    // Method to navigate to a Fragment with container ID and default animation
    public static void navigateToFragment(AppCompatActivity activity, @IdRes int containerId, Class<? extends Fragment> fragmentClass) {
        navigateToFragment(activity, containerId, fragmentClass, new Bundle(), R.anim.fade_in_animation, R.anim.fade_out_animation);
    }

    // Method to navigate to a Fragment with container ID, data (Bundle) and default animation
    public static void navigateToFragment(AppCompatActivity activity, @IdRes int containerId, Class<? extends Fragment> fragmentClass, Bundle data) {
        navigateToFragment(activity, containerId, fragmentClass, data, R.anim.fade_in_animation, R.anim.fade_out_animation);
    }

    // Navigate to a Fragment with a container ID and custom animations
    public static void navigateToFragment(AppCompatActivity activity, @IdRes int containerId, Class<? extends Fragment> fragmentClass, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        navigateToFragment(activity, containerId, fragmentClass, new Bundle(), enterAnim, exitAnim);
    }

    // Navigate to a Fragment with a container ID and a tag
    public static void navigateToFragment(AppCompatActivity activity, int containerId, Class<? extends Fragment> fragmentClass, String tag) {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        activity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                )
                .replace(containerId, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }


    // Navigate to a Fragment with a container ID and a tag
    public static void navigateToFragment(AppCompatActivity activity, int containerId, Class<? extends Fragment> fragmentClass, String tag, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        try {
            Fragment fragment = fragmentClass.getDeclaredConstructor().newInstance();
            activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim).replace(containerId, fragment, tag).addToBackStack(tag).commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate fragment " + fragmentClass.getSimpleName(), e);
        }
    }

    // Navigate to a Fragment and clear the back stack
    public static void navigateToFragmentClearBackStack(AppCompatActivity activity, int containerId, Class<? extends Fragment> fragmentClass) {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        try {
            Fragment fragment = fragmentClass.getDeclaredConstructor().newInstance();
            activity.getSupportFragmentManager().beginTransaction().replace(containerId, fragment).commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate fragment " + fragmentClass.getSimpleName(), e);
        }
    }

    // Navigate to a Fragment with a container ID and a tag, clearing the back stack
    public static void navigateToFragmentClearBackStack(AppCompatActivity activity, int containerId, Class<? extends Fragment> fragmentClass, String tag) {
        navigateToFragmentClearBackStack(activity, containerId, fragmentClass, tag, R.anim.fade_in_animation, R.anim.fade_out_animation);
    }

    // Navigate to a Fragment with a container ID, data (Bundle), and custom animations, clearing the back stack
    public static void navigateToFragmentClearBackStack(AppCompatActivity activity, int containerId, Class<? extends Fragment> fragmentClass, String tag, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        try {
            Fragment fragment = fragmentClass.newInstance();
            activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim).replace(containerId, fragment, tag).commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not instantiate fragment: " + fragmentClass.getName(), e);
        }
    }

    // Navigate to a Fragment with a container ID, data (Bundle), and custom animations, clearing the back stack
    public static void navigateToFragmentClearBackStack(AppCompatActivity activity, int containerId, Class<? extends Fragment> fragmentClass, String tag, Bundle data, @AnimRes int enterAnim, @AnimRes int exitAnim) {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        try {
            Fragment fragment = fragmentClass.newInstance();
            if (data != null) {
                fragment.setArguments(data);
            }

            activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim).replace(containerId, fragment, tag).commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not instantiate fragment: " + fragmentClass.getName(), e);
        }
    }

    // Navigate to a Fragment with a container ID, data (Bundle), and default animation, clearing the back stack
    public static void navigateToFragmentClearBackStack(AppCompatActivity activity, int containerId, Class<? extends Fragment> fragmentClass, String tag, Bundle data) {
        activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        try {
            Fragment fragment = fragmentClass.newInstance();
            if (data != null) {
                fragment.setArguments(data);
            }

            activity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation, R.anim.fade_in_animation, R.anim.fade_out_animation).replace(containerId, fragment, tag).commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not instantiate fragment: " + fragmentClass.getName(), e);
        }
    }

}
