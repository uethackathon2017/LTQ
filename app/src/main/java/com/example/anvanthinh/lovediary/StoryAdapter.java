package com.example.anvanthinh.lovediary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anvanthinh.lovediary.database.Story;
import com.example.anvanthinh.lovediary.database.StoryHelper;
import com.example.anvanthinh.lovediary.database.StoryProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * class dung de hien thi danh sach cac story
 */

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {
    public static final String TAG = "tag";
    private CursorAdapter mCursorAdapter;
    public static final String DOC_NOI_DUNG = "doc noi dung story";
    public static final String ID_STORY = "id story";
    private int mPostion;
    private int like, attach, isRead, sex;
    private Activity mContext;
    private boolean isTablet;
    private Cursor cursor;
    private IItemActionbar mInterface;

    public StoryAdapter(Activity c, Cursor cursor) {
        this.mContext = c;
        mInterface = (IItemActionbar) c;
        mCursorAdapter = new CursorAdapter(c, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.story_item_view, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
        cursor = mCursorAdapter.getCursor();
        holder.mTitle.setText(cursor.getString(cursor.getColumnIndex(StoryHelper.COLUMN_TITTLE)));
        holder.mContent.setText(cursor.getString(cursor.getColumnIndex(StoryHelper.COLUMN_CONTENT)));
        int poster = cursor.getInt(cursor.getColumnIndex(StoryHelper.COLUMN_POSTER));
        if (poster == 1) {
            holder.mAvatar.setImageResource(R.drawable.man_avatar);
        } else if (poster == 0) {
            holder.mAvatar.setImageResource(R.drawable.woman_avatar);
        }
        like = cursor.getInt(cursor.getColumnIndex(StoryHelper.COLUMN_LIKE));
        if (like == 1) {
            holder.mStar.setImageResource(R.drawable.ic_btn_star_on);
        } else {
            holder.mStar.setImageResource(R.drawable.ic_btn_star_off);
        }
        attach = cursor.getInt(cursor.getColumnIndex(StoryHelper.COLUMN_PAPER_CLIP));
        if (attach == 1) {
            holder.mPaperClip.setVisibility(View.VISIBLE);
        }
        long time = cursor.getLong(cursor.getColumnIndex(StoryHelper.COLUMN_DATE));
        Date date = new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        holder.mDate.setText(df2.format(date));
        isRead = cursor.getInt(cursor.getColumnIndex(StoryHelper.COLUMN_READ));
        if (isRead == 1) {
            holder.mTitle.setTextColor(R.color.gray_dark);
        } else {
            holder.mTitle.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        sex = cursor.getInt(cursor.getColumnIndex(StoryHelper.COLUMN_POSTER));
        if (sex == 1) {
            holder.mAvatar.setImageResource(R.drawable.man_avatar);
        } else {
            holder.mAvatar.setImageResource(R.drawable.woman_avatar);
        }
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    public CursorAdapter getCursorAdapter() {
        return this.mCursorAdapter;
    }


    public void remove(int position) {
        notifyItemRemoved(position);
    }

    public void add(Story s, int position) {
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle;
        private TextView mContent;
        private TextView mDate;
        private ImageView mAvatar;
        private ImageView mStar;
        private ImageView mPaperClip;
        private LinearLayout mItemView;
        private boolean isCheckAvatar = false;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.title_story);
            mContent = (TextView) v.findViewById(R.id.snippet);
            mDate = (TextView) v.findViewById(R.id.date);
            mAvatar = (ImageView) v.findViewById(R.id.contact_image);
            mStar = (ImageView) v.findViewById(R.id.star);
            mPaperClip = (ImageView) v.findViewById(R.id.paperclip);
            mItemView = (LinearLayout) v.findViewById(R.id.itemview);

            mContent.setOnClickListener(this);
            mTitle.setOnClickListener(this);
            mAvatar.setOnClickListener(this);
            mStar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            isTablet = mContext.getResources().getBoolean(R.bool.isTablet);
            mPostion = this.getAdapterPosition();
            mCursorAdapter.getCursor().moveToPosition(mPostion);
            Cursor cursor = mCursorAdapter.getCursor();
            String id = cursor.getString(cursor.getColumnIndex(StoryHelper.COLUMN_ID));
            ContentValues valuse = new ContentValues();
            switch (v.getId()) {
                case R.id.title_story:
                case R.id.snippet:
                    isRead = cursor.getInt(cursor.getColumnIndex(StoryHelper.COLUMN_READ));
                    if (isRead == 0) {
                        mTitle.setTextColor(R.color.gray_dark);
                        valuse.put(StoryHelper.COLUMN_READ, 1);
                        mContext.getContentResolver().update(StoryProvider.STORY_URI, valuse, StoryHelper.COLUMN_ID + " = ?", new String[]{id});
                    }
                    if (isTablet == false) {
                        StoryViewControllerFragment fragment = new StoryViewControllerFragment();
                        Bundle args = new Bundle();
                        args.putInt(ID_STORY, mPostion);
                        fragment.setArguments(args);
                        ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.list_story, fragment).addToBackStack(TAG).commit();
                    } else {
                        Intent i = new Intent(DOC_NOI_DUNG);
                        i.putExtra(ID_STORY, mPostion);
                        mContext.sendBroadcast(i);
                    }
                    break;
                case R.id.contact_image:
                    if (isCheckAvatar == false) {
                        mAvatar.setImageResource(R.drawable.ic_checked);
                        isCheckAvatar = true;
                    } else {
                        if (sex == 1) {
                            mAvatar.setImageResource(R.drawable.man_avatar);
                        } else {
                            mAvatar.setImageResource(R.drawable.woman_avatar);
                        }
                        isCheckAvatar = false;
                    }
                    break;
                case R.id.star:
                case R.id.date:
                case R.id.paperclip:
                    like = cursor.getInt(cursor.getColumnIndex(StoryHelper.COLUMN_LIKE));
                    if (like == 0) {
                        mStar.setImageResource(R.drawable.ic_btn_star_on);
                        valuse.put(StoryHelper.COLUMN_LIKE, 1);
                        mContext.getContentResolver().update(StoryProvider.STORY_URI, valuse, StoryHelper.COLUMN_ID + " LIKE ?", new String[]{id});
                    } else {
                        valuse.put(StoryHelper.COLUMN_LIKE, 0);
                        mContext.getContentResolver().update(StoryProvider.STORY_URI, valuse, StoryHelper.COLUMN_ID + " LIKE ?", new String[]{id});
                        mStar.setImageResource(R.drawable.ic_btn_star_off);
                    }
                    break;
            }
        }
    }

    public interface IItemActionbar {
        void removeItem(ArrayList<Story> arr);
    }

}















