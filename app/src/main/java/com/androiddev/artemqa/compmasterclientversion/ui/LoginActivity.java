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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_LOGIN_LOGIN_A = "com.androiddev.artemqa.compmasterclientversion.ui.loginactivity.LOGIN_EXTRA";
    TextInputEditText etLogin,etPassword;
    CardView cvLogin;
    Realm realm;
    TextView tvRegister;
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
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_register_login_a:
                intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.cv_login_login_a:
                Client client = realm.where(Client.class).equalTo("login",etLogin.getText().toString()).equalTo("password",etPassword.getText().toString()).findFirst();
                if(client != null){
                    intent = new Intent(LoginActivity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_LOGIN_LOGIN_A,etLogin.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(this,R.string.error_not_contains_user_login_a,Toast.LENGTH_SHORT).show();
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
