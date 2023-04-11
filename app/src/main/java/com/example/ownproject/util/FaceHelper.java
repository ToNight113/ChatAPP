package com.example.ownproject.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.example.ownproject.R;
import com.example.ownproject.adapter.ResultAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class FaceHelper {
    private final String[] mDataTitle = new String[20];
    private final OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .build();
    private AlertDialog mAlertDialog;
    private RecyclerView mRecycleView;
    private String[] mDataSet;
    private Context mContext;
    private Fragment mFragment;

    public FaceHelper(Context root, Fragment f) {
        this.mContext = root;
        this.mFragment = f;
    }

    public void help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View contentView = mFragment.getLayoutInflater().inflate(R.layout.dialog_face,
                null);
        mRecycleView = contentView.findViewById(R.id.result_rcl_face);
        builder.setTitle("笑话大全");
        builder.setView(contentView);
        builder.setNegativeButton("取消", (dialog, which) -> mAlertDialog.dismiss());
        mAlertDialog = builder.create();
        Thread thread = new Thread(this::sendHttpQuest);
        thread.start();
    }

    private void sendHttpQuest() {
        Request.Builder builder = new Request.Builder();
        builder.url("http://v.juhe.cn/joke/content/list.php");
        builder.post(RequestBody.create(getReqBody(), MediaType.get("application/x-www-form-urlencoded")));
        Request request = builder.build();
        try (Response response = mOkHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                mFragment.requireActivity().runOnUiThread(() -> mAlertDialog.show());
                return;
            }
            String body = Objects.requireNonNull(response.body()).string();
            System.out.println(body);
            mDataSet = parseBody(body);
            ResultAdapter adapter = new ResultAdapter(mDataTitle, mDataSet);
            mRecycleView.setAdapter(adapter);
            mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
            mFragment.requireActivity().runOnUiThread(() -> mAlertDialog.show());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] parseBody(String body) {
        String[] dataSet = new String[20];
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonObject1.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                dataSet[i] = jsonArray.getJSONObject(i).getString("content");
                mDataTitle[i] = "content";
                System.out.println(dataSet[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    private String getReqBody() {
        return "&key=1aad0cf42aafd52b66fec024467f34ad" +
                "&page=20&pagesize=20&sort=asc&time=1418745237";
    }
}

