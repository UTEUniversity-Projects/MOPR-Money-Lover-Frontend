package com.moneylover.data;


import com.moneylover.data.local.prefs.PreferencesService;
import com.moneylover.data.remote.ApiService;

public interface Repository {

    /**
     * ################################## Preference section ##################################
     */
    String getToken();
    void setToken(String token);

    PreferencesService getSharedPreferences();

    /**
     *  ################################## Remote api ##################################
     */
    ApiService getApiService();

}
