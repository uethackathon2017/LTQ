package com.example.anvanthinh.lovediary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anvanthinh.lovediary.database.Story;
import com.example.anvanthinh.lovediary.database.StoryModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * class dung de tao moi 1 story
 */

public class ComposeActivity extends AppCompatActivity implements View.OnClickListener {
    private static  final  String TITLE = "title";
    private static  final  String CONTENT = "title";
    private static  final  String SAVE_DATA = "save data";
    private TextView mDate;
    private TextView mMonth;
    private TextView mYear;
    private TextView mHour;
    private EditText mTittle;
    private EditText mContent;
    private Calendar mCalendar;
    private SharedPreferences mSharedpreferences;
    private SharedPreferences.Editor mEditor;
    private boolean isSave;

    //   private DatabaseReference mFirebaseDatabase;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.compose_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mDate = (TextView) findViewById(R.id.date);
        mMonth = (TextView) findViewById(R.id.month);
        mYear = (TextView) findViewById(R.id.year);
        mHour = (TextView) findViewById(R.id.hour);
        mTittle = (EditText) findViewById(R.id.title);

        mContent = (EditText) findViewById(R.id.content);
        mCalendar = Calendar.getInstance(TimeZone.getDefault());
        int date = mCalendar.get(Calendar.DATE);
        int year = mCalendar.get(Calendar.YEAR);
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(mCalendar.getTime());
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minutes = mCalendar.get(Calendar.MINUTE);

        mDate.setText(date + "");
        mMonth.setText(month_name);
        mYear.setText(year + "");
        mHour.setText(hour + " : " + minutes);

    }

    @Override
    protected void onResume() {
        //lay du lieu da luu tu truoc vao
        mSharedpreferences = getSharedPreferences(SAVE_DATA, Context.MODE_PRIVATE);
        mTittle.setText(mSharedpreferences.getString(TITLE,""));
        mContent.setText(mSharedpreferences.getString(CONTENT,""));
        super.onResume();
    }

    @Override
    protected void onPause() {
        mEditor = getSharedPreferences(SAVE_DATA, Context.MODE_PRIVATE).edit();
        mEditor.putString(TITLE, mTittle.getText()+"");
        mEditor.putString(CONTENT, mContent.getText()+"");
        mEditor.commit();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.save:
                SaveFile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ham luu file vao csdl
    private void SaveFile() {
        StoryModel storyModel = new StoryModel(this);
        if("".equals(mTittle.getText()) == true){
            Toast.makeText(this, R.string.empty_title, Toast.LENGTH_SHORT).show();
            mTittle.requestFocus();
            return;
        }
        if("".equals(mContent.getText()) == true){
            Toast.makeText(this, R.string.empty_content, Toast.LENGTH_SHORT).show();
            mTittle.requestFocus();
            return;
        }
        mSharedpreferences = getSharedPreferences(SignInFragment.ACCOUNT, Context.MODE_PRIVATE);
        String name = mSharedpreferences.getString(SignInFragment.ACCOUNT_NAME, "");
        int sex = mSharedpreferences.getInt(SignInFragment.SEX, 0);
        Story s = new Story();
        s.setTitle(mTittle.getText()+"");
        s.setContent(mContent.getText()+"");
        long date = mCalendar.getTimeInMillis();
        s.setDate(date);
        s.setPoster(sex);
        storyModel.insert_story(s);
        mTittle.setText("");
        mContent.setText("");
        //thinhav: test
//        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference();
//        mFirebaseDatabase.child("user_1").child("ho_ten1").setValue("ten 1");
//        mFirebaseDatabase.child("user_1").child("ho_ten2").setValue("ten 2");
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}
