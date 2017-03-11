package com.example.anvanthinh.lovediary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StoryHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "love_diary.db";
    private static final int DATABASE_VERSION = 4;
    public static final String TABLE_STORY = "story_table";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITTLE = "ten";
    public static final String COLUMN_CONTENT = "noi_dung";
    public static final String COLUMN_DATE = "ngay_dang";
    public static final String COLUMN_LIKE = "yeu_thich"; // 1= like
    public static final String COLUMN_PAPER_CLIP = "dinh_kem"; // 1 = co
    public static final String COLUMN_POSTER = "nguoi_dang";// 1= nam , 0 = nu
    public static final String COLUMN_READ = "da_xem";// 1= da_xem
    public static final String COLUMN_SYNC = "sync";// 1= da_up
    private static final String initDatabase = "create table "
            + TABLE_STORY
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITTLE + " text, "
            + COLUMN_CONTENT + " text, "
            + COLUMN_DATE + " long, "
            + COLUMN_LIKE + " integer, "
            + COLUMN_PAPER_CLIP + " integer, "
            + COLUMN_POSTER + " integer, "
            + COLUMN_SYNC + " integer, "
            + COLUMN_READ + " integer"
            + ");";

    public StoryHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(initDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("thinhav: urgrade table", "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORY);
        onCreate(db);
    }
}
