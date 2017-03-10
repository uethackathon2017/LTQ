package com.example.anvanthinh.lovediary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.anvanthinh.lovediary.controller.ActivityController;
import com.example.anvanthinh.lovediary.controller.OnePaneController;
import com.example.anvanthinh.lovediary.controller.TwoPaneController;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected static final int STORY_LOADER = 0;
    protected static  final String LOCKSCREEN = "lock_screen" ;
    protected static final String GET_PASS = "get_password";
    private static final String ACCOUNT = "account";
    private static final String SEX = "sex";
    private ActivityController mController;
    private boolean isTablet = false;
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedpreferences = getSharedPreferences(SignInFragment.ACCOUNT, Context.MODE_PRIVATE);
        Account account = new Account();
        String name = mSharedpreferences.getString(SignInFragment.ACCOUNT_NAME, "");
        String pass = mSharedpreferences.getString(SignInFragment.ACCOUNT_PASS, "");
        String phone = mSharedpreferences.getString(SignInFragment.ACCOUNT_PHONE, "");
        if( "".equals(name) == true){
            Intent i = new Intent(MainActivity.this, InitActivity.class);
            startActivity(i);
        }
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet == false) {
            mController = new OnePaneController(this);
        } else {
            mController = new TwoPaneController(this);
        }
        mController.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.change_pass:
                Intent i = new Intent(this, LockScreen.class);
                i.putExtra(LockScreen.SET_PASSWORD, LockScreen.SET_PASSWORD);
                startActivity(i);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // kiem tra xem tai khoan da dang nhap chua
    protected void checkAccount(){
        mSharedpreferences = getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
        boolean account = mSharedpreferences.getBoolean(ACCOUNT, false);
        if(account == true){

        }else{

        }
    }

    // kiem tra nguoi dang nhap la nam hay nu
    protected  void checkSex(){
        mSharedpreferences = getSharedPreferences(SEX, Context.MODE_PRIVATE);
        boolean sex = mSharedpreferences.getBoolean(SEX, false);
        if(sex == true){

        }else{

        }
    }


}















