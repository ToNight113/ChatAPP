package com.example.ownproject.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Spinner;

import com.example.ownproject.R;
import com.example.ownproject.adapter.ResultAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CharacterHelper {
    private final String[] mDataTitle = new String[]{"date", "name",
            "QFriend", "color", "datetime", "health", "love", "work", "money",
            "number", "summary"};
    private final OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .build();
    private AlertDialog mAlertDialog;
    private Spinner mCharacterSpinner;
    private RecyclerView mRecycleView;
    private final Context mContext;
    private final Fragment mFragment;

    public CharacterHelper(Context root, Fragment f) {
        this.mContext = root;
        this.mFragment = f;
    }

    public void help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View contentView = mFragment.getLayoutInflater().inflate(R.layout.dialog_character,
                null);
        mCharacterSpinner = contentView.findViewById(R.id.character_spinner);
        mRecycleView = contentView.findViewById(R.id.result_rcl_character);
        builder.setTitle("性格测试");
        builder.setView(contentView);
        builder.setPositiveButton("确定", (dialog, which) -> {
//            Thread thread = new Thread(this::sendHttpQuest);
//            thread.start();
        });
        builder.setNegativeButton("取消", (dialog, which) -> mAlertDialog.dismiss());
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    private void sendHttpQuest() {
        Request.Builder builder = new Request.Builder();
        builder.url("http://web.juhe.cn/constellation/getAll");
        builder.post(RequestBody.create(getReqBody(), MediaType.get("application/x-www-form-urlencoded")));
        Request request = builder.build();
        try (Response response = mOkHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                mFragment.requireActivity().runOnUiThread(() -> {
                    mAlertDialog.show();
                });
                return;
            }
            String body = Objects.requireNonNull(response.body()).string();
            System.out.println(body);
            String[] mDataSet = parseBody(body);
            ResultAdapter adapter = new ResultAdapter(mDataTitle, mDataSet);
            mRecycleView.setAdapter(adapter);
            mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
            mFragment.requireActivity().runOnUiThread(() -> mAlertDialog.show());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] parseBody(String body) {
        String[] dataSet = new String[11];
        try {
            JSONObject jsonObject = new JSONObject(body);
            for (int i = 0; i < dataSet.length; i++) {
                dataSet[i] = jsonObject.getString(mDataTitle[i]);
                System.out.println(dataSet[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    private String getReqBody() {
        StringBuilder sb = new StringBuilder();
        String consName = mCharacterSpinner.getSelectedItem().toString();
        try {
            sb.append("consName=")
                    .append(URLEncoder.encode(consName, "utf-8"))
                    .append("&type=")
                    .append("&key=5249533c29fa2fa15f9552e2af456450");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
