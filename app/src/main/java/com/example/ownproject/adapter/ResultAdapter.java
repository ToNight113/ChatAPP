package com.example.ownproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ownproject.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private final String[] mDataTitle;
    private final String[] mDataSet;

    public ResultAdapter(String[] dataTitle, String[] dataSet) {
        this.mDataSet = dataSet;
        this.mDataTitle = dataTitle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rcl_star_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTitleTv().setText(mDataTitle[position]);
        holder.getResultTv().setText(mDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return mDataTitle.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitleTv;
        private final TextView mResultTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTv = itemView.findViewById(R.id.rcl_result_title);
            mResultTv = itemView.findViewById(R.id.rcl_result);
        }

        public TextView getTitleTv() {
            return mTitleTv;
        }

        public TextView getResultTv() {
            return mResultTv;
        }
    }
}
