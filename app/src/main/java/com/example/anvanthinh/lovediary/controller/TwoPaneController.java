package com.example.anvanthinh.lovediary.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anvanthinh.lovediary.R;
import com.example.anvanthinh.lovediary.StoryListFragment;
import com.example.anvanthinh.lovediary.StoryViewControllerFragment;


public class TwoPaneController extends ActivityController {

    public TwoPaneController(AppCompatActivity a){
        super(a);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity.setContentView(R.layout.two_pane_activity);
        StoryListFragment listSongFragment = new StoryListFragment();
        StoryViewControllerFragment storyViewPagerFragment = new StoryViewControllerFragment();
        mActivity.getSupportFragmentManager().beginTransaction().add(R.id.list_story_fragment , listSongFragment).commit();
        mActivity.getSupportFragmentManager().beginTransaction().add(R.id.story_content_fragment , storyViewPagerFragment).commit();
    }
}
