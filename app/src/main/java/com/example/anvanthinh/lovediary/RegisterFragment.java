package com.example.anvanthinh.lovediary;

import android.content.Context;
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

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private IChangeToolbar mCallback;
    private EditText mName;
    private EditText mPass;
    private EditText mRePass;
    private Button mRemoveButton;
    private Button mRegisterButton;
    private DatabaseReference mReference;
    private ArrayList<String> mNameAccounts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);
        mName = (EditText) v.findViewById(R.id.name);
        mPass = (EditText) v.findViewById(R.id.pass);
        mRePass = (EditText) v.findViewById(R.id.repass);
        mRemoveButton = (Button) v.findViewById(R.id.remove);
        mRegisterButton = (Button) v.findViewById(R.id.register);
        mRemoveButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        mNameAccounts = new ArrayList<String>();
        mReference = FirebaseDatabase.getInstance().getReference();
//        mReference.child("Account").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    String name = postSnapshot.getValue(Account.class).getName();
//                    mNameAccounts.add(name);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//            }
//        });
        new GetAccount().execute();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        mCallback = (IChangeToolbar) context;
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove:
                removeInfor();
                break;
            case R.id.register:
                registerAccount();
                break;
        }
    }



    private void registerAccount() {
        if ("".equals(mName.getText() + "") == false) {
            for (int i = 0; i < mNameAccounts.size(); i++) {
                if ((mName.getText() + "").equals(mNameAccounts.get(i)) == true) {
                    Toast.makeText(getActivity(), R.string.name_duplicate, Toast.LENGTH_SHORT).show();
                    mName.requestFocus();
                    return;
                }
            }
        } else if ("".equals(mName.getText() + "") == true) {
            Toast.makeText(getActivity(), R.string.name_empty, Toast.LENGTH_SHORT).show();
            mName.requestFocus();
            return;
        } else if (4 >= (mPass.getText() + "").length()) {
            mPass.requestFocus();
            Toast.makeText(getActivity(), R.string.little_pass, Toast.LENGTH_SHORT).show();
            return;
        } else if ((mPass.getText() + "").equalsIgnoreCase(mRePass.getText() + "") == false) {
            Toast.makeText(getActivity(), R.string.error_repass, Toast.LENGTH_SHORT).show();
            mRePass.requestFocus();
            return;
        }else{
            Toast.makeText(getActivity(), R.string.register_success, Toast.LENGTH_SHORT).show();
        }
    }

    public void removeInfor() {
        mName.setText("");
        mPass.setText("");
        mRePass.setText("");
        mName.requestFocus();
    }

    private class GetAccount extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            mReference.child("Account").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String name = postSnapshot.getValue(Account.class).getName();
                        mNameAccounts.add(name);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
            return mNameAccounts;
        }

        @Override
        protected void onPostExecute(ArrayList<String> accounts) {
            super.onPostExecute(accounts);
        }
    }
}
