package com.example.anvanthinh.lovediary;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anvanthinh.lovediary.database.Story;
import com.example.anvanthinh.lovediary.database.StoryHelper;
import com.example.anvanthinh.lovediary.database.StoryProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * class dung de hien thi noi dung chi tiet 1 story
 */

public class StoryViewFragment extends Fragment implements View.OnClickListener {
    private ArrayList<Story> mArr = new ArrayList<Story>();
    // private BroadcastReceiver mReceiver = null;
    private int mPosition;
    private TextView mSnippet;
    private TextView mTitle;
    private TextView mDate;
    private ImageView mAvatar;
    private ImageView mPaperClip;
    private ImageView mStar;
    private Story mStory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.story_view_pager, container, false);
        mSnippet = (TextView) v.findViewById(R.id.story_content);
        mTitle = (TextView) v.findViewById(R.id.title_story);
        mStar = (ImageView) v.findViewById(R.id.star);
        mDate = (TextView) v.findViewById(R.id.date);
        mAvatar = (ImageView) v.findViewById(R.id.contact_image);
        mPaperClip = (ImageView) v.findViewById(R.id.paperclip);
        mStar.setOnClickListener(this);
        mStory = (Story) getArguments().getSerializable(StoryAdapter.ID_STORY);
        if (mStory.getLike() == 1) {
            mStar.setImageResource(R.drawable.ic_btn_star_on);
        }
        Date date = new Date(mStory.getDate());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        mDate.setText(df2.format(date));
        mSnippet.setText(mStory.getContent());
        mTitle.setText(mStory.getTitle());
        mTitle.setTextColor(R.color.black);
        int poster = mStory.getPoster();
        if (poster == 1) {
            mAvatar.setImageResource(R.drawable.man_avatar);
        } else if (poster == 0) {
            mAvatar.setImageResource(R.drawable.woman_avatar);
        }
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
        switch (v.getId()) {
            case R.id.star:
                ContentValues values = new ContentValues();
                String id = mStory.getId();
                if (mStory.getLike() == 1) {
                    mStar.setImageResource(R.drawable.ic_btn_star_off);
                    values.put(StoryHelper.COLUMN_LIKE, 0);
                } else {
                    values.put(StoryHelper.COLUMN_LIKE, 1);
                    mStar.setImageResource(R.drawable.ic_btn_star_on);
                }
                getActivity().getContentResolver().update(StoryProvider.STORY_URI, values, StoryHelper.COLUMN_ID + " LIKE ?", new String[]{id});
                break;
        }
    }
}
