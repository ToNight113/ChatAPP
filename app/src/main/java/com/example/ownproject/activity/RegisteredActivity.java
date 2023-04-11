package com.example.ownproject.activity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import com.example.ownproject.R;
import com.example.ownproject.adapter.UserInfoAdapter;
import com.example.ownproject.model.UserInfo;
import com.example.ownproject.util.DBHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RegisteredActivity extends AppCompatActivity {
    private UserInfoAdapter mUserInfoAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        init();
        bindView();
    }

    private void init() {
        String[] dataSet = new String[]{"姓名", "年龄", "地域"};
        mUserInfoAdapter = new UserInfoAdapter(dataSet);
    }

    private DBHelper mDbHelper;
    private SQLiteDatabase mSqLiteDatabase;

    private void bindView() {
        RecyclerView recyclerView = findViewById(R.id.rcl);
        recyclerView.setAdapter(mUserInfoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button allow = findViewById(R.id.allow);
        mDbHelper = new DBHelper(this);
        ContentValues values = new ContentValues();
        allow.setOnClickListener(v -> {
            /*showWaitDialog("" + UserInfo.getInstance().getName() + ", "
            + UserInfo.getInstance().getAge() + ","
            + UserInfo.getInstance().getArea());*/

            mSqLiteDatabase = mDbHelper.getWritableDatabase();
            values.put(DBHelper.KEY_MODULENAME, UserInfo.getInstance().getName());
            values.put(DBHelper.KEY_MODULEAGE, UserInfo.getInstance().getAge());
            values.put(DBHelper.KEY_MODULEAREA, UserInfo.getInstance().getArea());
            mSqLiteDatabase.insert(DBHelper.DATABASE_TABLE, "", values);
            finish();
        });
    }

    private void showWaitDialog(CharSequence msg) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("NFC");
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "测试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.setMessage("1111111111");
                }
            }
        });
        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });
        mProgressDialog.show();
    }
}
