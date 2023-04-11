package com.example.ownproject.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String KEY_ROWID = "id";
    public static final String KEY_MODULENAME = "name";
    public static final String KEY_MODULEAGE = "age";
    public static final String KEY_MODULEAREA = "area";
    public static final String DATABASE_TABLE = "userInfo";

    private static final String DATABASE_NAME = "myProject";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MODULENAME + " TEXT NOT NULL, " +
                KEY_MODULEAGE + " TEXT NOT NULL, " +
                KEY_MODULEAREA + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
