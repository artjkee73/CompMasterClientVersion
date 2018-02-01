
package com.androiddev.artemqa.compmasterclientversion.ui;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Client;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_LOGIN_REGISTER_A = "com.androiddev.artemqa.compmasterclientversion.ui.registeractivity.LOGIN_EXTRA";
    TextInputEditText etLogin, etPassword, etRepeatPassword;
    CardView cvRegistration;
    TextView tvLogin;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initForm();
    }

    private void initForm() {
        realm = Realm.getDefaultInstance();
        etLogin = findViewById(R.id.et_login_register_a);
        etPassword = findViewById(R.id.et_password_register_a);
        etRepeatPassword = findViewById(R.id.et_rep_password_login_a);
        cvRegistration = findViewById(R.id.cv_button_register);
        cvRegistration.setOnClickListener(this);
        tvLogin = findViewById(R.id.tv_login_register_a);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.cv_button_register:
                if (validateForm()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Client client = new Client(etLogin.getText().toString(), etPassword.getText().toString());
                            realm.copyToRealm(client);
                        }
                    });
                    intent = new Intent(RegisterActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_LOGIN_REGISTER_A,etLogin.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.tv_login_register_a:
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean validateForm() {
        if ((etLogin.getText().toString().matches("^\\w+\\@\\w+\\.\\w+$")) &&
                (etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) &&
                (etPassword.getText().toString().matches("^\\w{5,15}$"))) {
            Client client = realm.where(Client.class).equalTo("login", etLogin.getText().toString()).findFirst();
            if (client == null) {
                return true;
            } else {
                Toast.makeText(this, R.string.error_contains_user__register_a, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, R.string.error_validate_form_register_a, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
