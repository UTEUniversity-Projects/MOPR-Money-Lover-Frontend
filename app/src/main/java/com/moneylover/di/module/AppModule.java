package com.moneylover.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moneylover.constants.Constants;
import com.moneylover.data.AppRepository;
import com.moneylover.data.Repository;
import com.moneylover.data.local.prefs.AppPreferencesService;
import com.moneylover.data.local.prefs.PreferencesService;
import com.moneylover.data.remote.ApiService;
import com.moneylover.data.remote.AuthInterceptor;
import com.moneylover.di.qualifier.ApiInfo;
import com.moneylover.di.qualifier.PreferenceInfo;
import com.moneylover.utils.SSLUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.hdodenhof.circleimageview.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @ApiInfo
    @Singleton
    String provideBaseUrl() {
        return com.moneylover.BuildConfig.BASE_URL;
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }


    @Provides
    @PreferenceInfo
    @Singleton
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }


    @Provides
    @Singleton
    PreferencesService providePreferencesService(AppPreferencesService appPreferencesService) {
        return appPreferencesService;
    }

    @Provides
    @Singleton
    AuthInterceptor proviceAuthInterceptor(PreferencesService appPreferencesService, Application application) {
        return new AuthInterceptor(appPreferencesService, application);
    }

    // Config OK HTTP
//    @Provides
//    @Singleton
//    public OkHttpClient getClient(AuthInterceptor authInterceptor) {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
//            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
//        }
//        return new OkHttpClient.Builder()
//                .addInterceptor(authInterceptor)
//                .addInterceptor(loggingInterceptor)
//                .build();

    // Config OK HTTP
    @Provides
    @Singleton
    public OkHttpClient getClient(AuthInterceptor authInterceptor) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient client = SSLUtil.getUnsafeOkHttpClient();

        return client.newBuilder()
                .addInterceptor(authInterceptor)  // Thêm AuthInterceptor
                .addInterceptor(loggingInterceptor)  // Thêm LoggingInterceptor
                .build();
    }


    // Create Retrofit
    @Provides
    @Singleton
    public Retrofit retrofitBuild(OkHttpClient client, @ApiInfo String url) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Create api service
    @Provides
    @Singleton
    public ApiService apiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }


    @Provides
    @Singleton
    public Repository provideDataManager(AppRepository appRepository) {
        return appRepository;
    }


}
