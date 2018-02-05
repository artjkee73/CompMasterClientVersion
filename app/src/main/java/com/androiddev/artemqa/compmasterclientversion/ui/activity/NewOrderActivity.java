package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Client;
import com.androiddev.artemqa.compmasterclientversion.models.Order;
import com.androiddev.artemqa.compmasterclientversion.models.Price;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NewOrderActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_ORDER_ID_NEW_ORDER_A = "com.androiddev.artemqa.compmasterclientversion.ui.activity.neworderactivity.ExtraOrderId";
    public static final String EXTRA_FLAG_VISIBLE_CHECKED = "com.androiddev.artemqa.compmasterclientversion.ui.activity.neworderactivity.ExtraFlagVisibleChecked";
    public static final int REQUEST_CODE_PRICE_LIST = 1;
    Realm realm;
    TextInputEditText etName, etPhone, etAddress, etDescription;
    CardView cvNewOrder, cvChoosePosition;
    TextView tvTotalSum;
    CheckBox cbSaveClientData;

    ArrayList<String> positionIsChecked;
    RealmList<Price> priceListIsChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        initForm();
        putFieldsFromDB();
    }

    private void initForm() {
        realm = Realm.getDefaultInstance();
        etName = findViewById(R.id.et_name_new_order_a);
        etPhone = findViewById(R.id.et_phone_new_order_a);
        etAddress = findViewById(R.id.et_address_new_order_a);
        etDescription = findViewById(R.id.et_description_new_order_a);
        cvNewOrder = findViewById(R.id.cv_new_order_a);
        cvNewOrder.setOnClickListener(this);
        cvChoosePosition = findViewById(R.id.cv_choose_position_a);
        cvChoosePosition.setOnClickListener(this);
        tvTotalSum = findViewById(R.id.tv_about_oder_new_order_a);
        cbSaveClientData = findViewById(R.id.cb_remember_data_new_order_a);

    }

    private void putFieldsFromDB() {
        if (MainActivity.currentClient.getName() != null && MainActivity.currentClient.getPhoneNumber() != null && MainActivity.currentClient.getAddress() != null) {
            etName.setText(MainActivity.currentClient.getName());
            etPhone.setText(MainActivity.currentClient.getPhoneNumber());
            etAddress.setText(MainActivity.currentClient.getAddress());
        }
    }

    private void addPositionInList() {
        priceListIsChecked = new RealmList<>();
        if (positionIsChecked != null) {
            for (int i = 0; i < positionIsChecked.size(); i++) {
                priceListIsChecked.add(realm.where(Price.class).equalTo("namePosition", positionIsChecked.get(i)).findFirst());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_PRICE_LIST) {
                Bundle bundle = data.getExtras();
                positionIsChecked = bundle.getStringArrayList(EXTRA_FLAG_VISIBLE_CHECKED);
                if (positionIsChecked.size() > 0) {
                    addPositionInList();
                    int totalSum = 0;
                    for (int i = 0; i < priceListIsChecked.size(); i++) {
                        totalSum += priceListIsChecked.get(i).getCostPosition();
                    }
                    String typeRepairs = "";
                    for (int i = 0; i < positionIsChecked.size(); i++) {
                        typeRepairs += positionIsChecked.get(i);
                        if (i<positionIsChecked.size()-1) {
                            typeRepairs+=",";
                        }
                    }
                    cvNewOrder.setVisibility(View.VISIBLE);
                    cvChoosePosition.setVisibility(View.GONE);
                    tvTotalSum.setVisibility(View.VISIBLE);
                    tvTotalSum.setText(getResources().getString(R.string.tv_total_sum_data_new_order_a, totalSum, typeRepairs));
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.cv_choose_position_a:
                intent = new Intent(NewOrderActivity.this, PriceListActivity.class);
                bundle = new Bundle();
                bundle.putBoolean(EXTRA_FLAG_VISIBLE_CHECKED, true);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_PRICE_LIST);
                break;
            case R.id.cv_new_order_a:
                if (validateForm()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Order order = new Order();
                            order.setClient(MainActivity.currentClient);
                            order.setDateOrder(Calendar.getInstance().getTime());
                            order.setDescriptionOrder(etDescription.getText().toString());
                            if (realm.where(Order.class).max("id") == null) {
                                order.setId(1);
                            } else {
                                order.setId((realm.where(Order.class).max("id").intValue()) + 1);
                            }
                            order.setStatus("в обработке");
                            order.setPriceListPosition(priceListIsChecked);
                            realm.copyToRealmOrUpdate(order);

                            Intent intent = new Intent(NewOrderActivity.this, OrderActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt(EXTRA_ORDER_ID_NEW_ORDER_A, order.getId());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });


                    if (cbSaveClientData.isChecked()) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Client currentClient = MainActivity.currentClient;
                                currentClient.setAddress(etAddress.getText().toString());
                                currentClient.setName(etName.getText().toString());
                                currentClient.setPhoneNumber(etPhone.getText().toString());
                                realm.copyToRealmOrUpdate(currentClient);
                            }
                        });
                    }
                }
                break;
        }
    }

    private boolean validateForm() {
        if (!(etPhone.getText().toString().equals("")
                && etAddress.getText().toString().equals("")
                && etName.getText().toString().equals(""))) {

            if ((etName.getText().toString().matches("^[\\w\\s]{2,35}$")
                    && (etAddress.getText().toString().matches("^[\\w\\s\\.\\,]{5,49}$"))
                    && (etPhone.getText().toString().matches("^\\+?[0-9]{5,16}$")))) {
                return true;

            } else {
                Toast.makeText(this, R.string.error_validation_new_order_a, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, R.string.error_fill_fields_new_order_a, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
