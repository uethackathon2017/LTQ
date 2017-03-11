package com.example.anvanthinh.lovediary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.anvanthinh.lovediary.controller.ActivityController;
import com.example.anvanthinh.lovediary.controller.OnePaneController;
import com.example.anvanthinh.lovediary.controller.TwoPaneController;
import com.example.anvanthinh.lovediary.database.Story;
import com.example.anvanthinh.lovediary.database.StoryHelper;
import com.example.anvanthinh.lovediary.database.StoryProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
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
        mSharedpreferences = getSharedPreferences(SignInFragment.ACCOUNT, Context.MODE_PRIVATE);
        mNameAccount = mSharedpreferences.getString(SignInFragment.ACCOUNT_NAME, "");
        if( "".equals(mNameAccount) == true){
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
                new PushStory().execute();
                break;
            case R.id.set_time_write:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class PushStory extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String selection = StoryHelper.COLUMN_SYNC + " =?";
            String[] selectionArgs = new String[] {"0"};
            String[] projection = new String[] {
                    StoryHelper.COLUMN_ID, StoryHelper.COLUMN_TITTLE, StoryHelper.COLUMN_CONTENT, StoryHelper.COLUMN_DATE,
                    StoryHelper.COLUMN_LIKE, StoryHelper.COLUMN_PAPER_CLIP, StoryHelper.COLUMN_POSTER, StoryHelper.COLUMN_SYNC
            };
            Cursor c = getContentResolver().query(StoryProvider.STORY_URI, projection, null, null, null);
            int a = c.getCount();
            mReference = FirebaseDatabase.getInstance().getReference();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                if(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_SYNC)) == 0){
                    Story s = new Story();
                    s.setId(c.getString(c.getColumnIndex(StoryHelper.COLUMN_ID)));
                    s.setTitle(c.getString(c.getColumnIndex(StoryHelper.COLUMN_TITTLE)));
                    s.setContent(c.getString(c.getColumnIndex(StoryHelper.COLUMN_CONTENT)));
                    s.setDate(c.getLong(c.getColumnIndex(StoryHelper.COLUMN_DATE)));
                    s.setAttach(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_PAPER_CLIP)));
                    s.setPoster(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_POSTER)));
                    mReference.child(STORY).child(mNameAccount).push().setValue(s);
                    ContentValues valuse = new ContentValues();
                    valuse.put(StoryHelper.COLUMN_SYNC , 1);
                    String id = c.getString(c.getColumnIndex(StoryHelper.COLUMN_ID));
                    getContentResolver().update(StoryProvider.STORY_URI, valuse, StoryHelper.COLUMN_ID  +  " =? ", new String[] {id});
                }

            }
            return null;
        }
    }


}















