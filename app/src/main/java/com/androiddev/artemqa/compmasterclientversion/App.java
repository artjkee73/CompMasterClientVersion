package com.androiddev.artemqa.compmasterclientversion;

import android.app.Application;
import android.util.Log;

import com.androiddev.artemqa.compmasterclientversion.models.Client;
import com.androiddev.artemqa.compmasterclientversion.models.Employee;
import com.androiddev.artemqa.compmasterclientversion.models.Order;
import com.androiddev.artemqa.compmasterclientversion.models.Price;
import com.androiddev.artemqa.compmasterclientversion.util.Helper;

import java.util.ArrayList;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

/**
 * Created by artemqa on 30.01.2018.
 */

public class App extends Application {
    Realm realm;
    private static SyncConfiguration syncConfiguration;
    private static final String TAG = "LOG";
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realm = Realm.getDefaultInstance();
//        setPrice();
        loginToRealm(Helper.REALM_LOGIN , Helper.REALM_PASS);
    }

    private void setPrice() {
        RealmResults<Price> priceList = realm.where(Price.class).findAll();
        if (priceList.size() == 0) {
            final ArrayList<Price> prices = Helper.addPriceList();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(prices);
                }
            });
        }
        realm.close();
    }

    private void loginToRealm(final String userName, String password) {
        SyncUser.Callback<SyncUser> callback = new SyncUser.Callback<SyncUser>() {
            @Override
            public void onSuccess(SyncUser user) {
                Log.d(TAG,"SyncUser OK");
                syncConfiguration = getSyncConfiguration(user, Helper.REALM_URL);
                loadAllData();
            }

            @Override
            public void onError(ObjectServerError error) {
                Log.d(TAG,"SyncUser NOT OK");
            }
        };
        SyncCredentials syncCredentials;

            syncCredentials = SyncCredentials.usernamePassword(userName, password, false);


        SyncUser.loginAsync(syncCredentials,Helper.REALM_AUTH, callback);
    }
    private SyncConfiguration getSyncConfiguration(SyncUser syncUser, String realmURL) {
        SyncConfiguration syncConfiguration = new SyncConfiguration.Builder(syncUser, realmURL)
                .waitForInitialRemoteData()
                .build();
        return syncConfiguration;
    }

    private void loadAllData() {
        Realm.getInstanceAsync(syncConfiguration, new Realm.Callback() {
            @Override
            public void onSuccess(Realm realm) {
                Log.d(TAG,"Данные загружены");
                Realm localRealm = Realm.getDefaultInstance();
                localRealm.beginTransaction();
                localRealm.copyToRealmOrUpdate(realm.where(Price.class).findAll());
                localRealm.copyToRealmOrUpdate(realm.where(Order.class).findAll());
                localRealm.copyToRealmOrUpdate(realm.where(Client.class).findAll());
                localRealm.copyToRealmOrUpdate(realm.where(Employee.class).findAll());
                localRealm.commitTransaction();
                localRealm.close();
                realm.close();
                Helper.updateDefaultConfiguration(syncConfiguration);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG,"Данные не загружены");
            }
        });
    }
}
