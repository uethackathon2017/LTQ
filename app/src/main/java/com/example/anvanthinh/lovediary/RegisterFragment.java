package com.example.anvanthinh.lovediary;

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
    private EditText mName;
    private EditText mPass;
    private EditText mRePass;
    private EditText mPhone;
    private EditText mSubPhone;
    private Button mRemoveButton;
    private Button mRegisterButton;
    private ArrayList<Account> mArrAccount;
    FirebaseDatabase mFireDatabasec;
    DatabaseReference mReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment, container, false);
        mName = (EditText) v.findViewById(R.id.name);
        mPass = (EditText) v.findViewById(R.id.pass);
        mRePass = (EditText) v.findViewById(R.id.repass);
        mPhone = (EditText) v.findViewById(R.id.phone);
        mSubPhone = (EditText) v.findViewById(R.id.subphone);
        mRemoveButton = (Button) v.findViewById(R.id.remove);
        mRegisterButton = (Button) v.findViewById(R.id.register);
        mRemoveButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);

        mReference = FirebaseDatabase.getInstance().getReference();
        Account account = new Account("test2","123", "0912345678", "0912345678");
        mReference.child("Account").child("test2").setValue(account);

        mReference = FirebaseDatabase.getInstance().getReference();
        mReference.child("Account").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot i: dataSnapshot.getChildren()){
                    Account value = dataSnapshot.getValue(Account.class);
                    Toast.makeText(getActivity(), value.getName(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        // get phone number
//        TelephonyManager telemamanger = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
//        String getSimSerialNumber = telemamanger.getSimSerialNumber();
//        mSubPhone.setText(getSimSerialNumber);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove:
                removeInfor();
                break;
            case R.id.register:
                if ((mPass.getText() + "").equalsIgnoreCase(mRePass.getText() + "") == false) {
                    Toast.makeText(getActivity(), R.string.error_pass, Toast.LENGTH_SHORT).show();
                } else {

                }
                break;
        }
    }

    public void removeInfor() {
        mName.setText("");
        mPass.setText("");
        mRePass.setText("");
        mPhone.setText("");
        mSubPhone.setText("");
        mName.requestFocus();
    }

    private class GetAccount extends AsyncTask<Void, Void, ArrayList<Account>> {

        @Override
        protected ArrayList<Account> doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Account> accounts) {
            super.onPostExecute(accounts);
        }
    }
}
