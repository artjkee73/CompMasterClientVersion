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
    public static ArrayList<Price> addPriceList(){
        ArrayList<Price> priceList = new ArrayList<>();
        priceList.add(new Price(1,"Ремонт материнской платы","Описание услуги",48,2500));
        priceList.add(new Price(2,"Ремонт видеокарты","Описание услуги",48,3500));
        priceList.add(new Price(3,"Ремонт блока питания","Описание услуги",48,1500));
        priceList.add(new Price(4,"Замена оперативной памяти","Описание услуги",1,500));
        priceList.add(new Price(5,"Чистка ноутбука","Описание услуги",24,1200));
        priceList.add(new Price(6,"Установка ПО","Описание услуги",6,1500));
        priceList.add(new Price(7,"Установка ОС","Описание услуги",6,2500));
        priceList.add(new Price(8,"Установка антивируса","Описание услуги",1,700));
        priceList.add(new Price(9,"Оптимизация системы","Описание услуги",1,900));
        priceList.add(new Price(10,"Сборка компьютера","Описание услуги",24,1000));
    return priceList;
    }
    public static void updateDefaultConfiguration(SyncConfiguration syncConfiguration) {
        Realm.setDefaultConfiguration(syncConfiguration);
    }
}
