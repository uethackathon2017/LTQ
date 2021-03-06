package com.example.anvanthinh.lovediary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anvanthinh.lovediary.database.StoryHelper;
import com.example.anvanthinh.lovediary.database.StoryProvider;

/**
 * class hien thi ra viewpager rong de chua noi dung cac story ben trong
 */

public class StoryViewControllerFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = "tag";
    private ViewPager mViewPager;
    private BroadcastReceiver mReceiver ;
    private int mPosition;
    private boolean isTablet;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.story_pager_controller, container, false);
        mViewPager = (ViewPager)v.findViewById(R.id.story_pager_cotroller);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet == false) {
            mPosition = getArguments().getInt(StoryAdapter.ID_STORY);
            mViewPager.setCurrentItem(mPosition);
        } else if(isTablet == true) {

        }

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(StoryListFragment.STORY_LOADER, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter mIntentFilter = new IntentFilter(StoryAdapter.DOC_NOI_DUNG);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mPosition = intent.getIntExtra(StoryAdapter.ID_STORY , 0);
                mViewPager.setCurrentItem(mPosition);
            }
        };
        getContext().registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isTablet == true) {
            getContext().unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOder = StoryHelper.COLUMN_DATE + " DESC";
        if (id == StoryListFragment.STORY_LOADER) {
            CursorLoader cursor= new CursorLoader(getActivity(), StoryProvider.STORY_URI, null, null, null, sortOder);
            return cursor;
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null){
            StoryPagerAdapter mAdapter = new StoryPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), cursor);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(mPosition);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
