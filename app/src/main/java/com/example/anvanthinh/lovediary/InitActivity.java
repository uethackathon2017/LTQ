package com.example.anvanthinh.lovediary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by An Van Thinh on 3/10/2017.
 */

public class InitActivity extends AppCompatActivity implements IChangeToolbar {
    public final static  String ACCOUNT = "account";
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.tittle_sign);
        SignInFragment fragment = new SignInFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.init_main, fragment).commit();
        mSharedpreferences = getSharedPreferences(ACCOUNT, Context.MODE_PRIVATE);
    }

    @Override
    public void changeTitle(String title) {
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    private void onBack(){
        getSupportActionBar().setTitle(R.string.tittle_sign);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            moveTaskToBack(true);
        }
    }
}


