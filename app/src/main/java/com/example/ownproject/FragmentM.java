package com.example.ownproject;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FragmentM {
    private static final Object mLock = new Object();
    private static FragmentM mFragmentManager;
    public FragmentM() {
    }

    public static FragmentM getInstance() {
        synchronized (mLock) {
            if (mFragmentManager == null) {
                mFragmentManager = new FragmentM();
            }
            return mFragmentManager;
        }
    }

    public void updateFragment(Context context, Fragment fragment) {
        if (context instanceof AppCompatActivity) {
            androidx.fragment.app.FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FrameLayout container = ((AppCompatActivity) context).findViewById(R.id.main_fragment_container);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(container.getId(), fragment);
            transaction.commitAllowingStateLoss();
        }
    }
}
