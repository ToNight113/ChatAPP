package com.example.ownproject.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ownproject.R;
import com.example.ownproject.adapter.ResultAdapter;

import org.json.JSONArray;
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

public class WeatherHelper {
    private final String[] mDataTitle = new String[]{"info",
            "wid", "temperature", "humidity", "direct", "power", "aqi"};
    private final OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .build();
    private AlertDialog mAlertDialog;
    private Spinner mWeatherSpinner;
    private RecyclerView mRecycleView;
    private String[] mDataSet;
    private Context mContext;
    private Fragment mFragment;

    public WeatherHelper(Context root, Fragment f) {
        this.mContext = root;
        this.mFragment = f;
    }


    public void help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View contentView = mFragment.getLayoutInflater().inflate(R.layout.dialog_weather,
                null);
        mWeatherSpinner = contentView.findViewById(R.id.city_spinner);
        mRecycleView = contentView.findViewById(R.id.result_rcl_city);
        Thread initSpinner = new Thread(this::initSpinner);
        initSpinner.start();
        builder.setTitle("天气预报");
        builder.setView(contentView);
        builder.setPositiveButton("确定", (dialog, which) -> {
            Thread sendHttpQuest = new Thread(this::sendHttpQuest);
            sendHttpQuest.start();
        });
        builder.setNegativeButton("取消", (dialog, which) -> mAlertDialog.dismiss());
        mAlertDialog = builder.create();
        mAlertDialog.show();
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        String[] city;
        Request.Builder builder = new Request.Builder();
        builder.url("https://apis.juhe.cn/fapigw/globalarea/areas");
        builder.post(RequestBody.create(getCityReqBody(), MediaType.get("application/x-www-form-urlencoded")));
        Request request = builder.build();
        try (Response response = mOkHttpClient.newCall(request).execute()) {
            String body = Objects.requireNonNull(response.body()).string();
            city = parseCityBody(body);
            for (String s : city) {
                adapter.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mFragment.requireActivity().runOnUiThread(() -> {
            mWeatherSpinner.setAdapter(adapter);
        });
    }


    private void sendHttpQuest() {
        Request.Builder builder = new Request.Builder();
        builder.url("http://apis.juhe.cn/simpleWeather/query");
        builder.post(RequestBody.create(getWeatherReqBody(), MediaType.get("application/x-www-form-urlencoded")));
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
            mDataSet = parseResultBody(body);
            ResultAdapter adapter = new ResultAdapter(mDataTitle, mDataSet);
            mRecycleView.setAdapter(adapter);
            mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
            mFragment.requireActivity().runOnUiThread(() -> {
                mAlertDialog.show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] parseResultBody(String body) {
        String[] dataSet = new String[7];
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("realtime");
            for (int i = 0; i < dataSet.length; i++) {
                dataSet[i] = jsonObject2.getString(mDataTitle[i]);
                System.out.println(dataSet[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    private String[] parseCityBody(String body) {
        String[] cityResult = new String[0];
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            cityResult = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                cityResult[i] = jsonObject1.getString("city");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cityResult;
    }

    private String getCityReqBody() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("key=6fb18412442a2cc1a70427ef685bf29e")
                    .append("&country=")
                    .append(URLEncoder.encode("中国", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getWeatherReqBody() {
        StringBuilder sb = new StringBuilder();
        String city = mWeatherSpinner.getSelectedItem().toString();
        try {
            sb.append("city=")
                    .append(URLEncoder.encode(city, "utf-8"))
                    .append("&key=b1c93a78d27184a0c8810101691d8c72");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
