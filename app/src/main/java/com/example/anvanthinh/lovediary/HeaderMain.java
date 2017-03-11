package com.example.anvanthinh.lovediary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by An Van Thinh on 3/11/2017.
 */

public class HeaderMain extends LinearLayout {
    private TextView mText;
    private ImageView mAvatar;


    public HeaderMain(Context context) {
        super(context);
    }

    public HeaderMain(Context context, AttributeSet attrs) {
        super(context, attrs, -1);
    }

    public HeaderMain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mText = (TextView)findViewById(R.id.account);
        mText.setText("ahihi");
        mAvatar = (ImageView)findViewById(R.id.avatar);

    }

}
