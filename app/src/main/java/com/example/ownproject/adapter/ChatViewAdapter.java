package com.example.ownproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ownproject.R;
import com.example.ownproject.model.ChatBean;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatViewAdapter extends RecyclerView.Adapter<ChatViewAdapter.ChatViewHolder> {
    private final LinkedList<ChatBean> mChatBeanList;

    public ChatViewAdapter(LinkedList<ChatBean> mChatBeanList) {
        this.mChatBeanList = mChatBeanList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_view_layout, parent, false);
        return new ChatViewAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        if (mChatBeanList.get(position).getState() == ChatBean.RECEIVE) {
            holder.mRightChatView.setVisibility(View.GONE);
            holder.mLeftChatView.setVisibility(View.VISIBLE);
            holder.mLeftTextView.setText(mChatBeanList.get(position).getMessage());
        } else {
            holder.mRightChatView.setVisibility(View.VISIBLE);
            holder.mLeftChatView.setVisibility(View.GONE);
            holder.mRightTextView.setText(mChatBeanList.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mChatBeanList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout mLeftChatView;
        private final LinearLayout mRightChatView;
        private final TextView mRightTextView;
        private final TextView mLeftTextView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mLeftChatView = itemView.findViewById(R.id.chat_left_view);
            mRightChatView = itemView.findViewById(R.id.chat_right_view);
            mRightTextView = itemView.findViewById(R.id.right_text);
            mLeftTextView = itemView.findViewById(R.id.left_text);
        }
    }
}
