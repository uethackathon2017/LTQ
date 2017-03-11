package com.example.anvanthinh.lovediary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SignInFragment extends Fragment implements View.OnClickListener, IChangeToolbar {
    public static final String TAG = "tag";
    public final static String ACCOUNT = "account";
    public final static String ACCOUNT_NAME = "account_name";
    public final static String ACCOUNT_PASS = "account_pass";
    public final static String ACCOUNT_PHONE = "account_phone";
    public static final String SEX = "sex";
    private EditText mName;
    private EditText mPass;
    private Button mLogIn;
    private Button mRegister;
    private IChangeToolbar mCallback;
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;
    private String mNameAccount;
    private DatabaseReference mReference;
    private ArrayList<Account> mAccounts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_in_fragment, container, false);
        mName = (EditText) v.findViewById(R.id.name);
        mPass = (EditText) v.findViewById(R.id.pass);
        mLogIn = (Button) v.findViewById(R.id.sign_in);
        mRegister = (Button) v.findViewById(R.id.register);
        mRegister.setOnClickListener(this);
        mLogIn.setOnClickListener(this);
        mAccounts = new ArrayList<Account>();
        mReference = FirebaseDatabase.getInstance().getReference();
        new GetAccount().execute();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                mNameAccount = mName.getText() + "";
                if ("".equals(mNameAccount) == true) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.name_empty), Toast.LENGTH_SHORT).show();
                    mName.requestFocus();
                    return;
                }
                if ("".equals(mPass.getText() + "") == true) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.pass_empty), Toast.LENGTH_SHORT).show();
                    mPass.requestFocus();
                    return;
                } else {
                    boolean isAccount = false;
                    boolean isPass = false;
                    for (int i = 0; i < mAccounts.size(); i++) {
                        if (mNameAccount.equals(mAccounts.get(i).getName()) == true) {
                            if ((mPass.getText() + "").equals(mAccounts.get(i).getPass()) == true) {
                                isAccount = true;
                                isPass = true;
                                SexDialog dialog = new SexDialog(getActivity());
                                dialog.setName(mNameAccount);
                                dialog.setPass(mPass.getText() + "");
                                dialog.show();
                                return;
                            }
                        }
                    }
                    if (isPass == false){
                        mPass.requestFocus();
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_pass), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(isAccount == false){
                        mName.requestFocus();
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.no_account), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
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

    private class GetAccount extends AsyncTask<Void, Void, ArrayList<Account>> {
        @Override
        protected ArrayList<Account> doInBackground(Void... params) {
            mReference.child("Account").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Account name = postSnapshot.getValue(Account.class);
                        mAccounts.add(name);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
            return mAccounts;
        }

        @Override
        protected void onPostExecute(ArrayList<Account> accounts) {
            super.onPostExecute(accounts);
        }
    }

}
