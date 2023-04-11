package com.example.ownproject.util;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ChatLayout extends RelativeLayout {

    public ChatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setBackgroundColor(Color.parseColor("#00000000"));
    }
}
