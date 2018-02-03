package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Order;

import io.realm.Realm;

public class OrderActivity extends AppCompatActivity {
    CardView cvDeleteOrder;
    TextView tvNumber,tvCost,tvStatus,tvEmployee,tvDateCreate,tvTimeCreate,tvTimeExecuting,tvTypeRepair,tvDescription;
    Realm realm;
    Order currentOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initForm();
        currentOrder = getIntentData();


    }

    private void initForm() {
        realm = Realm.getDefaultInstance();
        cvDeleteOrder = findViewById(R.id.cv_delete_order_order_a);
        tvCost = findViewById(R.id.tv_cost_order_a);
        tvStatus = findViewById(R.id.tv_status_order_a);
        tvEmployee = findViewById(R.id.tv_employee_order_a);
        tvDateCreate = findViewById(R.id.tv_date_creating_order_a);
        tvTimeCreate = findViewById(R.id.tv_time_creating_order_a);
        tvTimeExecuting = findViewById(R.id.tv_time_executing_order_a);
        tvTypeRepair = findViewById(R.id.tv_type_order_a);
        tvDescription = findViewById(R.id.tv_description_order_a);

    }
    private Order getIntentData(){
        Bundle bundle = getIntent().getExtras();
        int orderId = bundle.getInt(NewOrderActivity.EXTRA_ORDER_ID_NEW_ORDER_A);
        return realm.where(Order.class).equalTo("id",orderId).findFirst();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
