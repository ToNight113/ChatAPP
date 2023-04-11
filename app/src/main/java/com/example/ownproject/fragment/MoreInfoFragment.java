package com.example.ownproject.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ownproject.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

public class MoreInfoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_more_info, container, false);
        bindView(root);
        return root;
    }

    @SuppressLint("IntentReset")
    private void bindView(View root) {
        Button log = root.findViewById(R.id.log);
        log.setOnClickListener(v -> {
            String logFilePath = getLog();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setType("text/plain");
            Uri uri = FileProvider.getUriForFile(requireActivity()
                    , "cn.own.guanhe",new File(logFilePath));
            intent.setData(uri);
            startActivity(intent);
        });
    }

    private String getLog() {
        String logFilePath = Environment.getExternalStorageDirectory().getPath() + "/log.txt";
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder log = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line).append("\n");
            }

            File logFile = new File(logFilePath);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(logFile);
                fos.write(log.toString().getBytes(StandardCharsets.UTF_8));
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logFilePath;
    }
}
