package com.example.anvanthinh.lovediary.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class StoryModel  {
    private Context mContext;

    public StoryModel(Context c){
        this.mContext = c;
    }

    // ham lay toan bo story trong sqlite
    public ArrayList<Story> getAllStory(){
        ArrayList<Story> arrStory = new ArrayList<Story>();
        String[] projection = new String[] {
                StoryHelper.COLUMN_ID, StoryHelper.COLUMN_TITTLE, StoryHelper.COLUMN_CONTENT, StoryHelper.COLUMN_DATE,
                StoryHelper.COLUMN_LIKE, StoryHelper.COLUMN_PAPER_CLIP, StoryHelper.COLUMN_POSTER, StoryHelper.COLUMN_SYNC
        };
        String sortOder = StoryHelper.COLUMN_DATE + " ASC";
        Cursor c = mContext.getContentResolver().query(StoryProvider.STORY_URI , projection ,
                null , null ,  sortOder);
        for(c.moveToFirst() ; !c.isAfterLast() ; c.moveToNext()){
            Story s = new Story();
            s.setId(c.getString(c.getColumnIndex(StoryHelper.COLUMN_ID)));
            s.setTitle(c.getString(c.getColumnIndex(StoryHelper.COLUMN_TITTLE)));
            s.setContent(c.getString(c.getColumnIndex(StoryHelper.COLUMN_CONTENT)));
            s.setDate(c.getLong(c.getColumnIndex(StoryHelper.COLUMN_DATE)));
            s.setLike(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_LIKE)));
            s.setAttach(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_PAPER_CLIP)));
            s.setPoster(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_POSTER)));
            arrStory.add(s);
        }
        return arrStory;
    }

    public void insert_story (Story s){
        ContentValues values = new ContentValues();
        values.put(StoryHelper.COLUMN_TITTLE , s.getTitle());
        values.put(StoryHelper.COLUMN_CONTENT , s.getContent());
        values.put(StoryHelper.COLUMN_DATE , s.getDate());
        values.put(StoryHelper.COLUMN_LIKE , s.getLike());
        values.put(StoryHelper.COLUMN_PAPER_CLIP , s.getAttach());
        values.put(StoryHelper.COLUMN_POSTER , s.getPoster());
        mContext.getContentResolver().insert(StoryProvider.STORY_URI , values);
    }

    public void InsertStorySync (Story s){
        ContentValues values = new ContentValues();
        values.put(StoryHelper.COLUMN_TITTLE , s.getTitle());
        values.put(StoryHelper.COLUMN_CONTENT , s.getContent());
        values.put(StoryHelper.COLUMN_DATE , s.getDate());
        values.put(StoryHelper.COLUMN_LIKE , s.getLike());
        values.put(StoryHelper.COLUMN_PAPER_CLIP , s.getAttach());
        values.put(StoryHelper.COLUMN_POSTER , s.getPoster());
        values.put(StoryHelper.COLUMN_KEY , s.getKey());
        values.put(StoryHelper.COLUMN_SYNC , 1);
        mContext.getContentResolver().insert(StoryProvider.STORY_URI , values);
    }

    public Story getInforStory (Cursor c){
        Story s = new Story();
        s.setId(c.getString(c.getColumnIndex(StoryHelper.COLUMN_ID)));
        s.setTitle(c.getString(c.getColumnIndex(StoryHelper.COLUMN_TITTLE)));
        s.setContent(c.getString(c.getColumnIndex(StoryHelper.COLUMN_CONTENT)));
        s.setDate(c.getLong(c.getColumnIndex(StoryHelper.COLUMN_DATE)));
        s.setLike(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_LIKE)));
        s.setAttach(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_PAPER_CLIP)));
        s.setPoster(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_POSTER)));
        return s;
    }
}
