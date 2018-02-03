package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Client;
import com.androiddev.artemqa.compmasterclientversion.util.Helper;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_LOGIN_LOGIN_A = "com.androiddev.artemqa.compmasterclientversion.ui.loginactivity.LOGIN_EXTRA";
    TextInputEditText etLogin, etPassword;
    CardView cvLogin;
    CheckBox cbRememberMe;
    Realm realm;
    TextView tvRegister;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initForm();


    }

    private void initForm() {
        realm = Realm.getDefaultInstance();
        etLogin = findViewById(R.id.et_login_login_a);
        etPassword = findViewById(R.id.et_password_login_a);
        cvLogin = findViewById(R.id.cv_login_login_a);
        cvLogin.setOnClickListener(this);
        tvRegister = findViewById(R.id.tv_register_login_a);
        tvRegister.setOnClickListener(this);
        cbRememberMe = findViewById(R.id.cb_remember_me_user_login_a);

        sharedPreferences = getSharedPreferences(Helper.NAME_LOGIN_SHARED_PREFERENCES, MODE_PRIVATE);
        if (sharedPreferences.contains(Helper.LOGIN_LOGIN_SHARED_PREFERENCES)) {
            etLogin.setText(sharedPreferences.getString(Helper.LOGIN_LOGIN_SHARED_PREFERENCES, ""));
            etPassword.setText(sharedPreferences.getString(Helper.PASSWORD_LOGIN_SHARED_PREFERENCES, ""));
            cbRememberMe.setChecked(sharedPreferences.getBoolean(Helper.CHECKBOX_LOGIN_SHARED_PREFERENCES, false));
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_register_login_a:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.cv_login_login_a:
                Client client = realm.where(Client.class).equalTo("login", etLogin.getText().toString()).equalTo("password", etPassword.getText().toString()).findFirst();
                if (client != null) {

                    if(cbRememberMe.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Helper.LOGIN_LOGIN_SHARED_PREFERENCES,etLogin.getText().toString());
                        editor.putString(Helper.PASSWORD_LOGIN_SHARED_PREFERENCES,etPassword.getText().toString());
                        editor.putBoolean(Helper.CHECKBOX_LOGIN_SHARED_PREFERENCES,cbRememberMe.isChecked());
                        editor.apply();
                    }
                    else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                    }

                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_LOGIN_LOGIN_A, etLogin.getText().toString());
                    intent.putExtras(bundle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.error_not_contains_user_login_a, Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
