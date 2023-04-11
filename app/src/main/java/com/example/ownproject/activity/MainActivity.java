package com.example.ownproject.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.ownproject.FragmentM;
import com.example.ownproject.R;
import com.example.ownproject.fragment.ChatFragment;
import com.example.ownproject.fragment.GameFragment;
import com.example.ownproject.fragment.MoreInfoFragment;
import com.example.ownproject.fragment.NewsFragment;
import com.example.ownproject.fragment.UserInfoFragment;
import com.example.ownproject.util.TabView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private FrameLayout mFrameLayout;
    private ViewPager mViewPager;
    private String[] mTabTitles;

    private final List<TabView> mTabViews = new ArrayList<>();
    private FragmentM mFragmentManager;

    private static final int INDEX_NEWS = 0;
    private static final int INDEX_CHAT = 1;
    private static final int INDEX_GAME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        init();
        //bindView();

        mFrameLayout = findViewById(R.id.main_fragment_container);
        mViewPager = findViewById(R.id.viewpager);
        mTabTitles = getResources().getStringArray(R.array.tab_array);
        TabView tabNews = findViewById(R.id.btn_new);
        TabView tabChat = findViewById(R.id.btn_chat);
        TabView tabGame = findViewById(R.id.btn_game);

        mTabViews.add(tabNews);
        mTabViews.add(tabChat);
        mTabViews.add(tabGame);

        mViewPager.setOffscreenPageLimit(mTabTitles.length - 1);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            /**
             * @param position 滑动的时候，position总是代表左边的View， position+1总是代表右边的View
             * @param positionOffset 左边View位移的比例
             * @param positionOffsetPixels 左边View位移的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 左边View进行动画
                mTabViews.get(position).setXPercentage(1 - positionOffset);
                // 如果positionOffset非0，那么就代表右边的View可见，也就说明需要对右边的View进行动画
                if (positionOffset > 0) {
                    mTabViews.get(position + 1).setXPercentage(positionOffset);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person:
                if (mFragmentManager != null) {
                    notifyLayout();
                    mFragmentManager.updateFragment(MainActivity.this,
                            new UserInfoFragment());
                }
                return true;
            case R.id.more:
                if (mFragmentManager != null) {
                    notifyLayout();
                    mFragmentManager.updateFragment(MainActivity.this,
                            new MoreInfoFragment());
                }
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void notifyLayout() {
        if (mViewPager.getVisibility() == View.VISIBLE) {
            mViewPager.setVisibility(View.GONE);
        }
        if (mFrameLayout.getVisibility() == View.GONE) {
            mFrameLayout.setVisibility(View.VISIBLE);
        }
    }

    private void removeLayout() {
        if (mViewPager.getVisibility() == View.GONE) {
            mViewPager.setVisibility(View.VISIBLE);
        }
        if (mFrameLayout.getVisibility() == View.VISIBLE) {
            mFrameLayout.setVisibility(View.GONE);
        }
    }

    private void updateCurrentTab(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (index == i) {
                mTabViews.get(i).setXPercentage(1);
            } else {
                mTabViews.get(i).setXPercentage(0);
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickTab(View v) {
        switch (v.getId()) {
            case R.id.btn_new:
                removeLayout();
                mViewPager.setCurrentItem(INDEX_NEWS, false);
                updateCurrentTab(INDEX_NEWS);
                break;
            case R.id.btn_chat:
                removeLayout();
                mViewPager.setCurrentItem(INDEX_CHAT, false);
                updateCurrentTab(INDEX_CHAT);
                break;

            case R.id.btn_game:
                removeLayout();
                mViewPager.setCurrentItem(INDEX_GAME, false);
                updateCurrentTab(INDEX_GAME);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int i) {
            return getTabFragment(i);
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }
    }

    private Fragment getTabFragment(int index) {
        Fragment fragment = null;
        switch (index) {
            case INDEX_NEWS:
                fragment = new NewsFragment();
                break;

            case INDEX_CHAT:
                fragment = new ChatFragment();
                break;

            case INDEX_GAME:
                fragment = new GameFragment();
                break;
        }
        return fragment;
    }

    private void init() {
        mFragmentManager = FragmentM.getInstance();
    }

    private void bindView() {
        TabView btn_new = findViewById(R.id.btn_new);
        btn_new.setOnClickListener(v -> mFragmentManager.updateFragment(MainActivity.this, new NewsFragment()));

        TabView btn_chat = findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(v -> mFragmentManager.updateFragment(MainActivity.this, new ChatFragment()));

        TabView btn_game = findViewById(R.id.btn_game);
        btn_game.setOnClickListener(v -> mFragmentManager.updateFragment(MainActivity.this, new GameFragment()));
    }
}