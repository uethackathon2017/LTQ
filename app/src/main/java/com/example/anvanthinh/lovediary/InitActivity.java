package com.example.anvanthinh.lovediary;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
/**
 * Created by An Van Thinh on 3/10/2017.
 */

public class InitActivity extends AppCompatActivity{
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        RegisterFragment fragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.init_main, fragment).commit();
        mToolbar.setTitle(R.string.tittle_register);

    }
}


