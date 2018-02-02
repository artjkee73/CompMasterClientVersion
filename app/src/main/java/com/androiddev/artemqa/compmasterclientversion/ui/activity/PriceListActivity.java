package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Price;
import com.androiddev.artemqa.compmasterclientversion.ui.adapter.RvAdapterPriceList;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class PriceListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Realm realm;
    boolean isVisibleCB;
    ArrayList<String> positionIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_list);
        getIntentData();
        initForm();

    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            isVisibleCB = bundle.getBoolean(NewOrderActivity.EXTRA_FLAG_VISIBLE_CHECKED);
        }
    }

    private void initForm() {
        positionIsChecked = new ArrayList<>();
        realm = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.rv_price_list_a);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new RvAdapterPriceList(getAdapterData(),isVisibleCB,positionIsChecked));
    }

    private RealmResults<Price> getAdapterData() {
        return realm.where(Price.class).sort("id", Sort.ASCENDING).findAll();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(NewOrderActivity.EXTRA_FLAG_VISIBLE_CHECKED, positionIsChecked);
        intent.putExtra(NewOrderActivity.EXTRA_FLAG_VISIBLE_CHECKED, positionIsChecked);
        setResult(RESULT_OK, intent);
        finish();
    }
}
