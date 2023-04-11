package com.example.ownproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ownproject.R;
import com.example.ownproject.adapter.ChatViewAdapter;
import com.example.ownproject.model.ChatBean;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatFragment extends Fragment {
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
        send.setOnClickListener(v -> {
            notifyState(mState == 1);
            ChatBean b = new ChatBean();
            b.setState(mState);
            b.setMessage(editText.getText().toString());
            linkedList.add(b);
            layoutManager.scrollToPosition(linkedList.indexOf(b));
            if (mChatViewAdapter != null) {
                mChatViewAdapter.notifyDataSetChanged();
            }
            editText.setText("");
            mState = 1;
        });
    }

    private void notifyState(boolean isNotify) {
        if (isNotify) {
            mState = 0;
        } else {
            mState = 1;
        }
    }
}
