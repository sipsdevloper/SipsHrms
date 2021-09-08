package com.sips.sipshrms.di;

import android.content.Context;

import com.sips.sipshrms.SharedPreferenceUtils;
import com.sips.sipshrms.Url.BaseUrlActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@InstallIn(ApplicationComponent.class)
@Module
public class MyModule {

    @Singleton
    @Provides
    public SharedPreferenceUtils provideAppDatabase(@ApplicationContext Context context) {
        return new SharedPreferenceUtils(context);
    }

 @Singleton
    @Provides
    public BaseUrlActivity provideBaseUrlActivity(@ApplicationContext Context context) {
        return new BaseUrlActivity(context);
    }

}
