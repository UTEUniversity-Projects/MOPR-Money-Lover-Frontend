package com.moneylover.di.component;

import com.moneylover.MainActivity;
import com.moneylover.di.module.ActivityModule;
import com.moneylover.di.scope.ActivityScope;
import com.moneylover.ui.main.auth.AuthActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(AuthActivity activity);
}

