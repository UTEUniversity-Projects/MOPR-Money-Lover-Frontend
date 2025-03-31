package com.moneylover.di.component;


import com.moneylover.di.module.FragmentModule;
import com.moneylover.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {
}
