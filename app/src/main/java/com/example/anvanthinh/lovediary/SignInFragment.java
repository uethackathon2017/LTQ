package com.example.anvanthinh.lovediary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by An Van Thinh on 3/10/2017.
 */

public class SignInFragment extends Fragment implements View.OnClickListener, IChangeToolbar{
    public static final String TAG = "tag";
    private EditText mName;
    private EditText mPass;
    private Button mLogIn;
    private Button mRegister;
    private IChangeToolbar mCallback;
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;
    private String mAccount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_in_fragment, container, false);
        mName = (EditText)v.findViewById(R.id.name);
        mPass = (EditText)v.findViewById(R.id.pass);
        mLogIn = (Button) v.findViewById(R.id.sign_in);
        mRegister = (Button) v.findViewById(R.id.register);
        mRegister.setOnClickListener(this);
        mLogIn.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                mAccount = mName.getText()+"";
                if("".equals(mAccount) == true){
                    Toast.makeText(getActivity(), R.string.name_empty, Toast.LENGTH_SHORT).show();
                    mName.requestFocus();
                    return;
                }
                mEditor = getActivity().getSharedPreferences(InitActivity.ACCOUNT, Context.MODE_PRIVATE).edit();
                mEditor.putString(mAccount, InitActivity.ACCOUNT);
                break;
            case R.id.register:
                RegisterFragment fragment = new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.init_main, fragment)
                .addToBackStack(TAG).commit();
                String title = getActivity().getResources().getString(R.string.tittle_register);
                mCallback.changeTitle(title);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (IChangeToolbar) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IChangeToolbar");
        }
    }

    @Override
    public void changeTitle(String nameFragment) {
    }
}
