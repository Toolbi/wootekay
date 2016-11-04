package com.tukkeendoo.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fallou on 20/05/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{


    private static final String DB_NAME = "tukkeendo";
    private static final int DB_VERSION = 1;


    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
