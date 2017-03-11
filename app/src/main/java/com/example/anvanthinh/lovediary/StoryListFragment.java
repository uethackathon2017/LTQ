package com.example.anvanthinh.lovediary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anvanthinh.lovediary.database.StoryHelper;
import com.example.anvanthinh.lovediary.database.StoryProvider;

/**
 * Class hien thi danh sach cac story.
 */

public class StoryListFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    protected static final int STORY_LOADER = 0;
    private FloatingActionButton mButtonCompose;
    private RecyclerView mListStories ;
    private RecyclerView.LayoutManager mLayoutManager;
    private StoryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.story_list_fragment, container, false);
        mButtonCompose = (FloatingActionButton)v.findViewById(R.id.bt_compose);
        mListStories = (RecyclerView)v.findViewById(R.id.story_listView);
        mListStories.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mListStories.setLayoutManager(mLayoutManager);
        return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mButtonCompose.setOnClickListener(this);
        getActivity().getSupportLoaderManager().initLoader(STORY_LOADER, null, this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.bt_compose:
                Intent i = new Intent(getActivity() , ComposeActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOder = StoryHelper.COLUMN_DATE + " DESC";
        if (id == STORY_LOADER){
            CursorLoader cursor = new CursorLoader( getActivity(), StoryProvider.STORY_URI, null, null, null, sortOder );
            return  cursor;
        }else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter = new StoryAdapter(getActivity(), cursor);
        mListStories.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
