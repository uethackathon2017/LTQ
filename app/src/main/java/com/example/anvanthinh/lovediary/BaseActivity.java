package com.example.anvanthinh.lovediary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by An Van Thinh on 3/11/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.d("thinhavb", "Pause BaseActivity");
//        SharedPreferences.Editor editor = getSharedPreferences(LockScreen.PASS_SUCCESS, Context.MODE_PRIVATE).edit();
//        editor.putBoolean(LockScreen.PASS_SUCCESS, false);
//        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("thinhavb", "Stop BaseActivity");
    }
}
