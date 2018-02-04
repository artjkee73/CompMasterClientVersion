package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Client;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_LOGIN_MAIN_A = "com.androiddev.artemqa.compmasterclientversion.ui.mainactivity.LOGIN_EXTRA";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    Realm realm;
    public static Client currentClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initForm();
    }

    private void initForm() {
        realm = Realm.getDefaultInstance();
        drawerLayout = findViewById(R.id.draw_layout_main_a);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.toggle_open_main_a, R.string.toggle_close_main_a);
        drawerLayout.addDrawerListener(toggle);
        navigationView = findViewById(R.id.nav_view_main_a);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        currentClient = setCurrentClient();
        setHeaderNavigationDrawer();
    }

    private void setHeaderNavigationDrawer() {
        View headerView = navigationView.getHeaderView(0);
        TextView clientEmail = headerView.findViewById(R.id.tv_login_user_nav_header);
        clientEmail.setText(currentClient.getLogin());
    }

    private Client setCurrentClient() {
        String loginClient = getLoginFromIntent();
        return realm.where(Client.class).equalTo("login", loginClient).findFirst();
    }

    private String getLoginFromIntent() {
        Bundle bundle = getIntent().getExtras();
        String login ;
        if (bundle.getString(LoginActivity.EXTRA_LOGIN_LOGIN_A) != null) {
            login = bundle.getString(LoginActivity.EXTRA_LOGIN_LOGIN_A);
        } else if(bundle.getString(RegisterActivity.EXTRA_LOGIN_REGISTER_A) != null) {
            login = bundle.getString(RegisterActivity.EXTRA_LOGIN_REGISTER_A);
        } else {
            login = bundle.getString(EXTRA_LOGIN_MAIN_A);
        }
        return login;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(EXTRA_LOGIN_MAIN_A,currentClient.getLogin());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_draw_profile:
                break;
            case R.id.menu_draw_price_list:
                intent = new Intent(MainActivity.this, PriceListActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_draw_new_order:
                intent = new Intent(MainActivity.this, NewOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_draw_order_list:
                intent = new Intent(MainActivity.this, OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_draw_logout:
                intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
