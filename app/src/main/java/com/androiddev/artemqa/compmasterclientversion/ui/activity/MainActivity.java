package com.androiddev.artemqa.compmasterclientversion.ui.activity;

import android.content.Intent;
import android.net.Uri;
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


import com.androiddev.artemqa.compmasterclientversion.R;
import com.androiddev.artemqa.compmasterclientversion.models.Client;
import com.androiddev.artemqa.compmasterclientversion.util.Helper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import io.realm.Realm;

import static android.content.Intent.ACTION_VIEW;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,View.OnClickListener{
    public static final String EXTRA_LOGIN_MAIN_A = "com.androiddev.artemqa.compmasterclientversion.ui.mainactivity.LOGIN_EXTRA";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView tvUrl,tvHoursWork,tvEmail,tvPhone,tvSite,tvVk;
    Realm realm;
    public static Client currentClient;
    private String loginClient;

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
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tvEmail = findViewById(R.id.tv_email_main_a);
        tvEmail.setOnClickListener(this);
        tvUrl = findViewById(R.id.tv_url_main_a);
        tvUrl.setOnClickListener(this);
        tvPhone = findViewById(R.id.tv_phone_main_a);
        tvPhone.setOnClickListener(this);
        tvVk = findViewById(R.id.tv_vk_main_a);
        tvVk.setOnClickListener(this);

    }

    private void setHeaderNavigationDrawer() {
        View headerView = navigationView.getHeaderView(0);
        TextView clientEmail = headerView.findViewById(R.id.tv_login_user_nav_header);
        clientEmail.setText(currentClient.getLogin());
    }

    private Client setCurrentClient() {
            loginClient = getLoginClient();
        return realm.where(Client.class).equalTo("login", loginClient).findFirst();
    }

    private String getLoginClient() {
        String login;
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();

            if (bundle.getString(LoginActivity.EXTRA_LOGIN_LOGIN_A) != null) {
                login = bundle.getString(LoginActivity.EXTRA_LOGIN_LOGIN_A);
            } else
                login = bundle.getString(RegisterActivity.EXTRA_LOGIN_REGISTER_A);
        }else {

            login = getSharedPreferences(Helper.NAME_LOGIN_SHARED_PREFERENCES,MODE_PRIVATE).getString(Helper.LOGIN_LOGIN_SHARED_PREFERENCES,"");
        }

        return login;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        loginClient = savedInstanceState.getString(EXTRA_LOGIN_MAIN_A);
        super.onRestoreInstanceState(savedInstanceState);
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
        outState.putString(EXTRA_LOGIN_MAIN_A, currentClient.getLogin());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_draw_profile:
                intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
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
                intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(54.332036, 48.387552))
                .title("CompMaster24")
                .snippet("Гагарина, 28"));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Uri uri;
        switch (view.getId()){
            case R.id.tv_url_main_a:
                intent= new Intent(Intent.ACTION_VIEW,Uri.parse(getResources().getString(R.string.tv_url_main_a)));
                startActivity(intent);
                break;
            case R.id.tv_vk_main_a:
                uri = Uri.parse("https://" +getResources().getString(R.string.tv_vk_main_a));
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;
            case R.id.tv_phone_main_a:
                uri = Uri.parse("tel:" + getResources().getString(R.string.tv_phone_main_a));
                intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                break;
            case R.id.tv_email_main_a:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.tv_email_main_a)});
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "message");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Choose an Email :"));
                break;

        }
    }
}
