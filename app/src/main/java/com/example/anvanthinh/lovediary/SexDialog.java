package com.example.anvanthinh.lovediary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by An Van Thinh on 3/11/2017.
 */
public class SexDialog extends Dialog implements View.OnClickListener{
    private Button mMan;
    private Button mWoman;
    private Context mContext;
    private Dialog d;
    private String name;
    private String pass;

    public SexDialog(Context a) {
        super(a);
        this.mContext = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sex_dialog);
        mMan = (Button)findViewById(R.id.man);
        mWoman = (Button)findViewById(R.id.woman);
        mWoman.setOnClickListener(this);
        mMan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.man:
                saveInfor(1);
                break;
            case R.id.woman:
                saveInfor(0);
                break;
        }
        dismiss();
    }

    private void saveInfor(int i) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(SignInFragment.ACCOUNT, Context.MODE_PRIVATE).edit();
        editor.putString(SignInFragment.ACCOUNT_NAME, name);
        editor.putString(SignInFragment.ACCOUNT_PASS, pass);
        editor.commit();
        SharedPreferences.Editor editor_sex = mContext.getSharedPreferences(SignInFragment.ACCOUNT, Context.MODE_PRIVATE).edit();
        if (i == 1){
            editor_sex.putInt(SignInFragment.SEX, 1);
        }else{
            editor_sex.putInt(SignInFragment.SEX, 0);
        }
        editor_sex.commit();
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
