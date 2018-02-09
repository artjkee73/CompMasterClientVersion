package com.androiddev.artemqa.compmasterclientversion.util;

import com.androiddev.artemqa.compmasterclientversion.models.Price;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.SyncConfiguration;

/**
 * Created by artemqa on 31.01.2018.
 */

public class Helper {
    public static final String REALM_URL = "http://94.250.253.154:9080/~/compmaster";
    public static final String REALM_AUTH = "http://94.250.253.154:9080/auth";
    public static final String REALM_LOGIN = "compMaster";
    public static final String REALM_PASS = "12345678";
    public static final String NAME_LOGIN_SHARED_PREFERENCES = "com.androiddev.artemqa.compmasterclientversion.loginShaderPreferences";
    public static final String LOGIN_LOGIN_SHARED_PREFERENCES = "loginLoginSharedPreferences";
    public static final String PASSWORD_LOGIN_SHARED_PREFERENCES = "passwordLoginSharedPreferences";
    public static final String CHECKBOX_LOGIN_SHARED_PREFERENCES = "checkboxLoginSharedPreferences";

    public static void updateDefaultConfiguration(SyncConfiguration syncConfiguration) {
        Realm.setDefaultConfiguration(syncConfiguration);
    }
}
