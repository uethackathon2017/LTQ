package com.example.anvanthinh.lovediary;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.anvanthinh.lovediary.database.Story;
import com.example.anvanthinh.lovediary.database.StoryModel;

public class StoryPagerAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private CursorAdapter mCursorAdapter;
    private Cursor mCursor;
    private BroadcastReceiver mReceiver = null;

    public StoryPagerAdapter(FragmentManager fm, Activity activity, Cursor cursor) {
        super(fm);
        this.mCursor = cursor;
        this.mContext = activity;
        mCursorAdapter = new CursorAdapter(activity, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return null;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

            }
        };
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public StoryViewFragment getItem(int position) {
        mCursorAdapter.getCursor().moveToPosition(position);
        mCursor = mCursorAdapter.getCursor();
        Story s = new Story();
        StoryModel storyModel = new StoryModel(mContext);
        s = storyModel.getInforStory(mCursor);
        return StoryViewFragment.newInstant(s);
    }

}


























