package com.example.ownproject.model;
public class UserInfo {
    private static final Object mLock = new Object();
    private static UserInfo mUserInfo;

    private String mName;
    private String mAge;
    private String mArea;
    public UserInfo(){
    }

    public static UserInfo getInstance() {
        synchronized (mLock) {
            if (mUserInfo == null) {
                mUserInfo = new UserInfo();
            }
            return mUserInfo;
        }
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setAge(String age) {
        this.mAge = age;
    }

    public void setArea(String area) {
        this.mArea = area;
    }

    public String getName() {
        return this.mName;
    }

    public String getAge() {
        return this.mAge;
    }

    public String getArea() {
        return this.mArea;
    }
}
