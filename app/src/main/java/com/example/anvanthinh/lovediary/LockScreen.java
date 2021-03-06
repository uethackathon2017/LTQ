package com.example.anvanthinh.lovediary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

public class LockScreen extends AppCompatActivity implements View.OnClickListener {
    protected static final String SET_PASSWORD = "set password";
    protected static final String SAVE_PASS = "save_pass";
    protected static final String FIRST_IN = "vao _lan_dau";
    public static final String PASS_SUCCESS = "pass_success";
    private String mPassword = "";
    private String mTempPassword = "";
    private PinLockView mPinLock;
    private IndicatorDots mIndicatorDots;
    private FloatingActionButton mSave;
    private TextView mText;
    private boolean isSetPass = false; // xet xem la dang nhap hay thay doi mat khau
    private boolean isRepeatPass = false; // xet trang thai nhap lai mat khau de xac nhan
    private boolean isNewPass = false; // chuyen sang trang thai nhap mk moi
    private boolean isSave = true; // xet xem la trang thai luu mat khau  moi hay reset lai mk cu
    private SharedPreferences mSharedpreferences; // luu password
    private SharedPreferences.Editor mEditor;
    private boolean isFirst;
    private int mStatePass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);
        mSharedpreferences = getSharedPreferences(SAVE_PASS, Context.MODE_PRIVATE);
        mPassword = mSharedpreferences.getString(SAVE_PASS, "1111");
        mSharedpreferences = getSharedPreferences(FIRST_IN, Context.MODE_PRIVATE);
        isFirst = mSharedpreferences.getBoolean(FIRST_IN, true);
        if(isFirst == true){
            mStatePass = getIntent().getIntExtra(MainActivity.CONDITION_PASS, MainActivity.SET_PASS);
        }else{
            mStatePass = getIntent().getIntExtra(MainActivity.CONDITION_PASS, MainActivity.ENTER_PASS);
        }
        mSave = (FloatingActionButton) findViewById(R.id.bt_save_pass);
        mSave.setOnClickListener(this);
        mSave.setVisibility(View.INVISIBLE);
        mText = (TextView) findViewById(R.id.text_pass);
        mPinLock = (PinLockView) findViewById(R.id.pin_lock_view);
        mPinLock.setPinLockListener(mPinLockListener);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);
        mPinLock.attachIndicatorDots(mIndicatorDots);
        mPinLock.setPinLockListener(mPinLockListener);
        mPinLock.setPinLength(4);
        if (mStatePass == MainActivity.SET_PASS) { // vao set password lan dau
            initView();
            mText.setText(R.string.set_pass_first);
        } else if (mStatePass == MainActivity.CHANGE_PASS) { // vao de thay doi  password
            initView();
            mText.setText(R.string.enter_old_password);
        } else { // vao de nhap pass

        }
    }

    private void initView() {
        mText.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
        params.addRule(RelativeLayout.ABOVE, R.id.bt_save_pass);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mPinLock.setLayoutParams(params);
        isSetPass = true;
    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            if (mStatePass == MainActivity.SET_PASS) { // vao set password lan dau
                mText.setText(R.string.enter_repeat_pass);
                mPinLock.resetPinLockView();
                if (pin.equalsIgnoreCase(mTempPassword) && isRepeatPass == true) {
                    isRepeatPass = true;
                    isSave = true;
                    mSave.setVisibility(View.VISIBLE);
                    isFirst = true;
                } else if (isRepeatPass == true && pin.equals(mTempPassword) == false) {
                    mSave.setImageResource(R.drawable.reload);
                    mSave.setVisibility(View.VISIBLE);
                    isSave = false;
                    Toast.makeText(LockScreen.this, R.string.error_pass, Toast.LENGTH_SHORT).show();
                }
                mTempPassword = pin;
                isRepeatPass = true;
            } else if (mStatePass == MainActivity.CHANGE_PASS) { // vao de thay doi  password
                if (mPassword.equalsIgnoreCase(pin) == false && isNewPass == false) { // mk cu nhap sai
                    mPinLock.resetPinLockView();
                    Toast.makeText(LockScreen.this, R.string.error_pass, Toast.LENGTH_SHORT).show();
                    return;
                } else { // mat khau cu nhap dung
                    if (isNewPass == false) {
                        isNewPass = true;
                        mText.setText(R.string.enter_new_password);
                        mPinLock.resetPinLockView();
                    } else if (mTempPassword.equalsIgnoreCase(pin)) {
                        mSave.setVisibility(View.VISIBLE);
                        isSave = true;
                        mSave.setVisibility(View.VISIBLE);
                    } else if (mTempPassword.equalsIgnoreCase(pin) && isRepeatPass == true) {
                        isSave = false;
                        mSave.setImageResource(R.drawable.reload);
                        mSave.setVisibility(View.VISIBLE);
                        Toast.makeText(LockScreen.this, R.string.error_pass, Toast.LENGTH_SHORT).show();
                    } else { // chuyen sang man hinh nhap mk moi
                        mTempPassword = pin;
                        isRepeatPass = true;
                        mText.setText(R.string.enter_repeat_pass);
                        mPinLock.resetPinLockView();
                    }
                }
            } else { // vao de nhap pass
                mSharedpreferences = getSharedPreferences(SAVE_PASS, Context.MODE_PRIVATE);
                String password = mSharedpreferences.getString(SAVE_PASS, "");
                if(pin.equals(password) == true){ // pass nhap dung
                    SharedPreferences.Editor editor = getSharedPreferences(PASS_SUCCESS, Context.MODE_PRIVATE).edit();
                    editor.putBoolean(PASS_SUCCESS, true);
                    editor.commit();
                    Intent i = new Intent(LockScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    mPinLock.resetPinLockView();
                    Toast.makeText(LockScreen.this, R.string.error_pass, Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onEmpty() {
            Log.d("thinhav", "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d("thinhav", "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save_pass:
                isNewPass = false;
                if (isSave == true) {
                    String toast = getResources().getString(R.string.set_pass_succes);
                    mPassword = mTempPassword;
                    mEditor = getSharedPreferences(SAVE_PASS, MODE_PRIVATE).edit();
                    mEditor.putString(SAVE_PASS, mPassword);
                    mEditor.commit();
                    mPinLock.resetPinLockView();
                    mText.setText(R.string.enter_old_password);
                    if (isFirst == true) {
                        mEditor = getSharedPreferences(FIRST_IN, Context.MODE_PRIVATE).edit();
                        mEditor.putBoolean(FIRST_IN, false);
                        mEditor.commit();
                        mEditor = getSharedPreferences(MainActivity.GET_PASS, Context.MODE_PRIVATE).edit();
                        mEditor.putBoolean(MainActivity.LOCKSCREEN, true);
                        mEditor.commit();
                        isFirst = false;
                        toast = getResources().getString(R.string.set_pass);
                        Intent i = new Intent(LockScreen.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    Toast.makeText(LockScreen.this, toast, Toast.LENGTH_SHORT).show();
                } else {
                    mTempPassword = "";
                    mPinLock.resetPinLockView();
                    mText.setText(R.string.enter_old_password);
                }
                break;
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}