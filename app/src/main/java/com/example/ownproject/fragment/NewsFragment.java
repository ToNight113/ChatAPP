package com.example.ownproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;

import com.example.ownproject.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        bindView(root);
        return root;
    }

    private void bindView(View root) {
        SearchView searchView = root.findViewById(R.id.search);
        WebView webView = root.findViewById(R.id.web);
        EditText editText = root.findViewById(R.id.input);
        searchView.setOnClickListener(v -> {

        });

        searchView.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);

        WebView view = initWebView(webView);
        view.loadUrl("https://www.baidu.com");
    }

    private WebView initWebView(WebView view) {
        view.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSetting = view.getSettings();

        webSetting.setDatabaseEnabled(true);
        webSetting.setTextZoom(100);

        // ===设置JS可用
        webSetting.setJavaScriptEnabled(true);
        // JS打开窗口
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        // ===设置JS可用
        // 可以访问文件
        webSetting.setAllowFileAccess(true);
        // ===缩放可用
        webSetting.setSupportZoom(true);
        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS); //设置缩放功能   //能不能缩放 取决于网页设置
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setBuiltInZoomControls(true);
        // ===缩放可用
        // 支持多窗口
        webSetting.setSupportMultipleWindows(true);
        // ===============缓存
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);// 决定是否从网络上取数据。
        webSetting.setAppCacheEnabled(true);
        // ===============缓存
        webSetting.setUseWideViewPort(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // ==定位
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        // ==定位
        // 适配图片加载不出来的问题
        webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        return view;
    }
}
