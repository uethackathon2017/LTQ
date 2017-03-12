package com.example.anvanthinh.lovediary;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.example.anvanthinh.lovediary.database.Story;
import com.example.anvanthinh.lovediary.database.StoryHelper;
import com.example.anvanthinh.lovediary.database.StoryModel;
import com.example.anvanthinh.lovediary.database.StoryProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by An Van Thinh on 3/12/2017.
 */

public class SyncService extends IntentService {
    protected final static String UPLOAD = "upload";
    protected final static String DOWNLOAD = "download";
    private static final String STORY = "Story";
    protected static final String NAME_ACCOUNT = "name_account";
    private DatabaseReference mReference;
    private String mNameAccount;
    private String[] projection;

    public SyncService() {
        super("SyncService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        projection = new String[]{
                StoryHelper.COLUMN_ID, StoryHelper.COLUMN_TITTLE, StoryHelper.COLUMN_CONTENT, StoryHelper.COLUMN_DATE,
                StoryHelper.COLUMN_LIKE, StoryHelper.COLUMN_PAPER_CLIP, StoryHelper.COLUMN_POSTER, StoryHelper.COLUMN_SYNC,
                StoryHelper.COLUMN_KEY
        };
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        mNameAccount = intent.getStringExtra(NAME_ACCOUNT);
        if (UPLOAD.equals(action) == true) {
            upload(mNameAccount);
        } else if (DOWNLOAD.equals(action) == true) {
            download(mNameAccount);
        }
    }

    private void upload(String mNameAccount) {
        mReference = FirebaseDatabase.getInstance().getReference();
        String selection = StoryHelper.COLUMN_SYNC + " like ?";
        String[] selectionArgs = new String[]{"0"};
        Cursor c = getContentResolver().query(StoryProvider.STORY_URI, projection, null, null, null);

        mReference = FirebaseDatabase.getInstance().getReference();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Log.d("thinhavb", "sync = " + c.getInt(c.getColumnIndex(StoryHelper.COLUMN_SYNC)));
            if (c.getInt(c.getColumnIndex(StoryHelper.COLUMN_SYNC)) == 0) {
                Story s = new Story();
                s.setId(c.getString(c.getColumnIndex(StoryHelper.COLUMN_ID)));
                s.setKey(c.getString(c.getColumnIndex(StoryHelper.COLUMN_KEY)));
                s.setTitle(c.getString(c.getColumnIndex(StoryHelper.COLUMN_TITTLE)));
                s.setContent(c.getString(c.getColumnIndex(StoryHelper.COLUMN_CONTENT)));
                s.setDate(c.getLong(c.getColumnIndex(StoryHelper.COLUMN_DATE)));
                s.setAttach(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_PAPER_CLIP)));
                s.setPoster(c.getInt(c.getColumnIndex(StoryHelper.COLUMN_POSTER)));
                mReference.child(STORY).child(mNameAccount).push().setValue(s);
                ContentValues valuse = new ContentValues();
                valuse.put(StoryHelper.COLUMN_SYNC, 1);
                String id = c.getString(c.getColumnIndex(StoryHelper.COLUMN_ID));
                getContentResolver().update(StoryProvider.STORY_URI, valuse, StoryHelper.COLUMN_ID + " LIKE ? ", new String[]{id});
            }
        }
    }

    private void download(String mNameAccount) {
        mReference = FirebaseDatabase.getInstance().getReference();
        projection = new String[] {StoryHelper.COLUMN_KEY};
        final Cursor c = getContentResolver().query(StoryProvider.STORY_URI, projection, null, null, null);
        mReference.child(STORY).child(mNameAccount).orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getContentResolver().delete(StoryProvider.STORY_URI, null, null);
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Story s = data.getValue(Story.class);
                        StoryModel model = new StoryModel(getApplication());
                        model.InsertStorySync(s);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
