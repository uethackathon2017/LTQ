package com.example.anvanthinh.lovediary.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

public class StoryProvider extends ContentProvider {
    private StoryHelper database;

    private static final int STORY = 10;
    private static final int STORY_ID = 20;
    private static final String AUTHORITY = "com.example.anvanthinh.lovediary.database";
    private static final String BASE_PATH = "story";
    public static final Uri STORY_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);
    public static final Uri STORY_COUNT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String STORY_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/stories";
    public static final String STORY_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/story";

    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, STORY);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", STORY_ID);

    }

    @Override
    public boolean onCreate() {
        database = new StoryHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(StoryHelper.TABLE_STORY);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case STORY:
                break;
            case STORY_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(StoryHelper.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case STORY:
                id = sqlDB.insert(StoryHelper.TABLE_STORY, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case STORY:
                rowsDeleted = sqlDB.delete(StoryHelper.TABLE_STORY, selection,
                        selectionArgs);
                break;
            case STORY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            StoryHelper.TABLE_STORY,
                            StoryHelper.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            StoryHelper.TABLE_STORY,
                            StoryHelper.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case STORY:
                rowsUpdated = sqlDB.update(StoryHelper.TABLE_STORY,
                        values,
                        selection,
                        selectionArgs);
                break;
            case STORY_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(StoryHelper.TABLE_STORY,
                            values,
                            StoryHelper.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(StoryHelper.TABLE_STORY,
                            values,
                            StoryHelper.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;

    }

    // kiem tra cac cot truy van xem co trong bang khong
    private void checkColumns(String[] projection) {
        String[] available = { StoryHelper.COLUMN_ID,StoryHelper.COLUMN_TITTLE,StoryHelper.COLUMN_CONTENT,
                StoryHelper.COLUMN_DATE,StoryHelper.COLUMN_LIKE,StoryHelper.COLUMN_PAPER_CLIP, StoryHelper.COLUMN_POSTER
                , StoryHelper.COLUMN_SYNC
        };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }

}
