package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Order;
import com.androiddev.artemqa.compmasterclientversion.ui.adapter.RvAdapterOrderList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class OrderListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initForm();
    }

    private void initForm() {
        realm = Realm.getDefaultInstance();
        recyclerView = findViewById(R.id.rv_order_list_a);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new RvAdapterOrderList(getAdapterData()));
    }

    private RealmResults<Order> getAdapterData() {
        return realm.where(Order.class).equalTo("client.name", MainActivity.currentClient.getName()).sort("dateOrder", Sort.ASCENDING).findAll();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderListActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
