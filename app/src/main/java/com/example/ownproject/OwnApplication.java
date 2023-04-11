package com.example.ownproject;

import android.app.Application;
import android.content.Intent;

public class OwnApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        checkUser();
    }

    private void checkUser() {
        if (!getUserInfo()) {
            Intent intent = new Intent();
            intent.setAction(Constant.REGISTERED_ACTION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        }
    }

    private boolean getUserInfo() {
        return false;
    }
}
