package com.example.ownproject.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ownproject.FragmentM;
import com.example.ownproject.R;
import com.example.ownproject.activity.RegisteredActivity;
import com.example.ownproject.util.DBHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserInfoFragment extends Fragment {
    private View mRoot;
    private TextView mWelcome;
    private TextView mName;
    private TextView mAge;
    private TextView mArea;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        bindView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout(mRoot);
    }

    private void bindView(View root) {
        mRoot = root;
        mWelcome = root.findViewById(R.id.welcome);
        mName = root.findViewById(R.id.name);
        mAge = root.findViewById(R.id.age);
        mArea = root.findViewById(R.id.area);
        refreshLayout(root);
        RadioButton button = root.findViewById(R.id.registered_btn);
        button.setOnClickListener(v -> startActivity(new Intent(getActivity(), RegisteredActivity.class)));
        Button exit_account = root.findViewById(R.id.exit_account);
        exit_account.setOnClickListener(v -> {
            DBHelper helper = new DBHelper(getActivity());
            SQLiteDatabase database;
            database = helper.getWritableDatabase();
            database.delete(DBHelper.DATABASE_TABLE,
                    "name = '" + mName.getText().toString().trim() + "'" , null);
            FragmentM.getInstance().updateFragment(requireActivity(), new GameFragment());
        });
    }

    private void refreshLayout(View root) {
        LinearLayout linearLayout = root.findViewById(R.id.registered_layout);
        LinearLayout linearLayout1 = root.findViewById(R.id.userinfo_layout);
        if (querySQLite()) {
            linearLayout.setVisibility(View.GONE);
            linearLayout1.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayout1.setVisibility(View.GONE);
        }
    }

    @SuppressLint("Recycle")
    private boolean querySQLite() {
        DBHelper helper = new DBHelper(getActivity());
        SQLiteDatabase database;
        database = helper.getWritableDatabase();
        String[] columns = new String[]{DBHelper.KEY_ROWID, DBHelper.KEY_MODULENAME,
                DBHelper.KEY_MODULEAGE, DBHelper.KEY_MODULEAREA};
        Cursor cursor = database.query(DBHelper.DATABASE_TABLE, columns,
                null, null, null, null, null);
        if (cursor.moveToLast()) {
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_MODULENAME);
            String name = cursor.getString(nameIndex);
            int ageIndex = cursor.getColumnIndex(DBHelper.KEY_MODULEAGE);
            String age = cursor.getString(ageIndex);
            int yearIndex = cursor.getColumnIndex(DBHelper.KEY_MODULEAREA);
            String year = cursor.getString(yearIndex);
            showUserInfo(name, age, year);
            cursor.close();
            return true;
        }
        return false;
    }

    private void showUserInfo(String name, String age, String area) {
        mWelcome.setText(String.format("%s%s", getString(R.string.welcome), name));
        mName.setText(name);
        mAge.setText(String.format("%s%s", getString(R.string.age), age));
        mArea.setText(String.format("%s%s", getString(R.string.area), area));
    }
}
