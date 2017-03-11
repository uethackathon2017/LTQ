package com.example.anvanthinh.lovediary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anvanthinh.lovediary.database.Story;

import java.util.ArrayList;

/**
 * class dung de hien thi noi dung chi tiet 1 story
 */

public class StoryViewFragment extends Fragment implements  View.OnClickListener{
    private ArrayList<Story> mArr = new ArrayList<Story>();
    // private BroadcastReceiver mReceiver = null;
    private int mPosition;
    private TextView mSnippet;
    private TextView mTitle;
    private boolean isTablet = false;
    private ImageView mStar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.story_view_pager, container, false);
        mSnippet = (TextView) v.findViewById(R.id.story_content);
        mTitle = (TextView) v.findViewById(R.id.title);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        mStar = (ImageView)v.findViewById(R.id.star);
        mStar.setOnClickListener(this);
        Story s = (Story) getArguments().getSerializable(StoryAdapter.ID_STORY);
        mSnippet.setText(s.getContent());
        mTitle.setText(s.getTitle());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public static StoryViewFragment newInstant(Story s) {
        StoryViewFragment storyView = new StoryViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(StoryAdapter.ID_STORY, s);
        storyView.setArguments(args);
        return storyView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.star:

                break;
        }
    }
}
