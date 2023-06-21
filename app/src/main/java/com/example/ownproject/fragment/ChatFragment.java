package com.example.ownproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ownproject.R;
import com.example.ownproject.adapter.ChatViewAdapter;
import com.example.ownproject.model.ChatBean;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatFragment extends Fragment {
    private final OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .build();
    private int mState = 0;
    private ChatViewAdapter mChatViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        bindView(root);
        return root;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void bindView(View root) {
        EditText editText = root.findViewById(R.id.chat_input);
        Button send = root.findViewById(R.id.send);
        LinkedList<ChatBean> linkedList = new LinkedList<>();
        mChatViewAdapter = new ChatViewAdapter(linkedList);
        RecyclerView recyclerView = root.findViewById(R.id.chat_rcl);
        recyclerView.setAdapter(mChatViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        String msg = editText.getText().toString();
        send.setOnClickListener(v -> {
            notifyState(mState == 1);
            ChatBean b = new ChatBean();
            b.setState(mState);
            b.setMessage(msg);
            linkedList.add(b);
            layoutManager.scrollToPosition(linkedList.indexOf(b));
            if (mChatViewAdapter != null) {
                mChatViewAdapter.notifyDataSetChanged();
            }
            boolean isSuccess = sendMsgWithHttp(msg);
            if (isSuccess) {
                editText.setText("");
                mState = 1;
            }
        });
    }

    private boolean sendMsgWithHttp(String msg) {
        if (msg.equals("")) return false;
        Request.Builder builder = new Request.Builder();
        builder.url(buildHttpUrl("1c9b8035.r17.cpolar.top", msg));
        builder.post(RequestBody.create("send", MediaType.get("text/html")));
        Request request = builder.build();
        try (Response response = mOkHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                requireActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "" +
                        response, Toast.LENGTH_SHORT).show());
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    private void notifyState(boolean isNotify) {
        if (isNotify) {
            mState = 0;
        } else {
            mState = 1;
        }
    }
}
