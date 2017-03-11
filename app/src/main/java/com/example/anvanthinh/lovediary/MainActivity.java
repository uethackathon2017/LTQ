package com.example.anvanthinh.lovediary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.anvanthinh.lovediary.controller.ActivityController;
import com.example.anvanthinh.lovediary.controller.OnePaneController;
import com.example.anvanthinh.lovediary.controller.TwoPaneController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected static final int STORY_LOADER = 0;
    protected static  final String LOCKSCREEN = "lock_screen" ;
    protected static  final int SET_PASS = 0 ;
    protected static  final int CHANGE_PASS = 1 ;
    protected static  final int ENTER_PASS = 2 ;
    protected static  final String CONDITION_PASS = "lock_screen" ;
    protected static final String GET_PASS = "get_password";
    private static final String ACCOUNT = "account";
    private static final String SEX = "sex";
    private ActivityController mController;
    private boolean isTablet = false;
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;
    private  DatabaseReference mReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedpreferences = getSharedPreferences(SignInFragment.ACCOUNT, Context.MODE_PRIVATE);
        String name = mSharedpreferences.getString(SignInFragment.ACCOUNT_NAME, "");
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
//        mSharedpreferences = getSharedPreferences(LockScreen.SAVE_PASS, Context.MODE_PRIVATE );
//        String password = mSharedpreferences.getString(LockScreen.SAVE_PASS, "");
//        mSharedpreferences = getSharedPreferences(LockScreen.PASS_SUCCESS, Context.MODE_PRIVATE );
//        Boolean passSuccess = mSharedpreferences.getBoolean(LockScreen.PASS_SUCCESS, false);
//        if( "".equals(password) == true){
//            Intent i = new Intent(MainActivity.this, LockScreen.class);
//            i.putExtra(CONDITION_PASS, SET_PASS);
//            startActivity(i);
//        }else if(passSuccess == false){
//            Intent i = new Intent(MainActivity.this, LockScreen.class);
//            i.putExtra(CONDITION_PASS, ENTER_PASS);
//            startActivity(i);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                i.putExtra(CONDITION_PASS, CHANGE_PASS);
                startActivity(i);
                break;
            case R.id.sync:
                new PushData().execute();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child(ACCOUNT).orderByChild("pass").equalTo("12345").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot i : dataSnapshot.getChildren()){
                            Account a = i.getValue(Account.class);
                            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                            Log.d("thinhavb", a.getName() + " - " + calendar.getTimeInMillis());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case R.id.set_time_write:
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

    // lop day du lieu len server
    private class PushData extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            mReference = FirebaseDatabase.getInstance().getReference();

            return null;
        }
    }


}















