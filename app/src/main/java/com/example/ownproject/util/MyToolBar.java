package com.example.ownproject.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ownproject.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

public class MyToolBar extends Toolbar {
    private LayoutInflater mInflater;
    private TextView mTextTitle;

    private View view;

    public MyToolBar(@NonNull Context context) {
        super(context);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        if (view == null) {
            mInflater = LayoutInflater.from(getContext());
            view = mInflater.inflate(R.layout.title_layout, null);
            mTextTitle = view.findViewById(R.id.title);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(view, layoutParams);
        }
    }
}
