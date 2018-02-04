package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class OrderActivity extends AppCompatActivity {
    CardView cvDeleteOrder;
    TextView tvNumber, tvCost, tvStatus, tvEmployee, tvDateCreate, tvTimeCreate, tvTimeExecuting, tvTypeRepair, tvDescription;
    Realm realm;
    Order currentOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initForm();
        currentOrder = getIntentData();
        setFieldsFromDb();
        setVisibleDeleteButton();


    }


    private void initForm() {
        realm = Realm.getDefaultInstance();
        tvCost = findViewById(R.id.tv_cost_order_a);
        tvStatus = findViewById(R.id.tv_status_order_a);
        tvEmployee = findViewById(R.id.tv_employee_order_a);
        tvDateCreate = findViewById(R.id.tv_date_creating_order_a);
        tvTimeCreate = findViewById(R.id.tv_time_creating_order_a);
        tvTimeExecuting = findViewById(R.id.tv_time_executing_order_a);
        tvTypeRepair = findViewById(R.id.tv_type_order_a);
        tvDescription = findViewById(R.id.tv_description_order_a);
        tvNumber = findViewById(R.id.tv_number_order_a);
        cvDeleteOrder = findViewById(R.id.cv_delete_order_order_a);
        cvDeleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedOrderId = currentOrder.getId();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.where(Order.class).equalTo("id",currentOrder.getId()).findFirst().deleteFromRealm();
                    }
                });
                Toast.makeText(OrderActivity.this,"Заказ № "+ deletedOrderId + " успешно отменён",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OrderActivity.this,OrderListActivity.class);
                startActivity(intent);
            }
        });

    }

    private Order getIntentData() {
        Bundle bundle = getIntent().getExtras();
        int orderId = bundle.getInt(NewOrderActivity.EXTRA_ORDER_ID_NEW_ORDER_A);
        return realm.where(Order.class).equalTo("id", orderId).findFirst();
    }

    private void setFieldsFromDb() {
        tvNumber.setText(String.valueOf(currentOrder.getId()));
        tvCost.setText(String.valueOf(currentOrder.getId()));
        tvStatus.setText(currentOrder.getStatus());
        if (currentOrder.getEmployee() != null) {
            tvEmployee.setText(currentOrder.getEmployee().getName());
        } else {
            tvEmployee.setText("не назначен");
        }
        tvCost.setText(String.valueOf(currentOrder.getPriceListPosition().sum("costPosition")));
        tvTimeExecuting.setText(String.valueOf(currentOrder.getPriceListPosition().sum("timeExecuting")));
        Date fullDateCreatingOrder = currentOrder.getDateOrder();
        tvDateCreate.setText(String.valueOf(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fullDateCreatingOrder)));
        tvTimeCreate.setText(String.valueOf(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(fullDateCreatingOrder)));
        String typeRepairs = "";
        for (int i = 0; i < currentOrder.getPriceListPosition().size(); i++) {
            typeRepairs += currentOrder.getPriceListPosition().get(i).getNamePosition().concat("\n");
        }
        tvTypeRepair.setText(typeRepairs);
        if (currentOrder.getDescriptionOrder() != null) {
            tvDescription.setText(currentOrder.getDescriptionOrder());
        } else {
            tvDescription.setText("отсутвует");
        }
    }

    private void setVisibleDeleteButton() {
        if(!currentOrder.getStatus().equals("в обработке")){
            cvDeleteOrder.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
