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
import com.example.anvanthinh.lovediary.database.Story;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, StoryAdapter.IItemActionbar {
    protected static final int STORY_LOADER = 0;
    protected static  final String LOCKSCREEN = "lock_screen" ;
    protected static  final int SET_PASS = 0 ;
    protected static  final int CHANGE_PASS = 1 ;
    protected static  final int ENTER_PASS = 2 ;
    protected static  final String CONDITION_PASS = "lock_screen" ;
    protected static final String GET_PASS = "get_password";
    private static final String ACCOUNT = "account";
    private static final String SEX = "sex";
    private static final String STORY = "Story";
    private ActivityController mController;
    private boolean isTablet = false;
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;
    private  DatabaseReference mReference;
    private  String mNameAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet == false) {
            mController = new OnePaneController(this);
        } else {
            mController = new TwoPaneController(this);
        }
        mController.onCreate(savedInstanceState);

        mSharedpreferences = getSharedPreferences(SignInFragment.ACCOUNT, Context.MODE_PRIVATE);
        mNameAccount = mSharedpreferences.getString(SignInFragment.ACCOUNT_NAME, "");
        if( "".equals(mNameAccount) == true){
            Intent i = new Intent(MainActivity.this, InitActivity.class);
            startActivity(i);
        }else{
            Intent intent2 = new Intent(this, SyncService.class);
            intent2.setAction(SyncService.DOWNLOAD);
            intent2.putExtra(SyncService.NAME_ACCOUNT, mNameAccount);
            startService(intent2);
        }
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
            case R.id.up:
                Intent intent1 = new Intent(this, SyncService.class);
                intent1.setAction(SyncService.UPLOAD);
                intent1.putExtra(SyncService.NAME_ACCOUNT, mNameAccount);
                startService(intent1);
                break;
            case R.id.down:
                Intent intent2 = new Intent(this, SyncService.class);
                intent2.setAction(SyncService.DOWNLOAD);
                intent2.putExtra(SyncService.NAME_ACCOUNT, mNameAccount);
                startService(intent2);
                break;
            case R.id.set_time_write:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void removeItem(ArrayList<Story> arr) {

    }



}















