package com.example.anvanthinh.lovediary.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anvanthinh.lovediary.R;
import com.example.anvanthinh.lovediary.StoryListFragment;


public class OnePaneController extends ActivityController {

    public OnePaneController(AppCompatActivity a){
        super(a);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity.setContentView(R.layout.one_pane_activity);
        StoryListFragment storyListFragment = new StoryListFragment();
        mActivity.getSupportFragmentManager().beginTransaction().add(R.id.list_story , storyListFragment).commit();
    }
}
