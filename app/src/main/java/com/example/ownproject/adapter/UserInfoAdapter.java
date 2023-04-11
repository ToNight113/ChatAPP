package com.example.ownproject.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ownproject.R;
import com.example.ownproject.model.UserInfo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.ViewHolder> {
    private final String[] mDataSet;

    public UserInfoAdapter(String[] dataSet) {
        this.mDataSet = dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcl_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(mDataSet[position]);
        holder.getEditText().addTextChangedListener(new EditTextListener(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;
        private final EditText mEditText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv);
            mEditText = itemView.findViewById(R.id.edit);
        }

        public EditText getEditText() {
            return mEditText;
        }

        public TextView getTextView() {
            return mTextView;
        }
    }

    public static class EditTextListener implements TextWatcher {
        private final int mPosition;

        public EditTextListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            UserInfo userInfo = UserInfo.getInstance();
            if (mPosition == 0) {
                userInfo.setName(s.toString());
            } else if (mPosition == 1){
                userInfo.setAge(s.toString());
            } else if (mPosition == 2) {
                userInfo.setArea(s.toString());
            }
        }
    }
}
