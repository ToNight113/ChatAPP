package com.example.ownproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatService extends Service {
    private final OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .build();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                QueryMessage();
                handler.postDelayed(this, 1000);  // 1秒后执行
            }
        };
        handler.postDelayed(runnable, 1000);//1秒执行一次runnable.
    }

    private void QueryMessage() {
        Request.Builder builder = new Request.Builder();
        builder.url(buildHttpUrl("ss","sa"));
        builder.post(RequestBody.create("send", MediaType.get("text/html")));
        Request request = builder.build();
        try (Response response = mOkHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                Toast.makeText(this, "" +
                        response, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HttpUrl buildHttpUrl(String host, String msg) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("demo_war_exploded")
                .addPathSegment("state-manager-servlet")
                .addQueryParameter("msg", msg)
                .addQueryParameter("action", "send")
                .build();
        System.out.println(url);
        return url;
    }
}
