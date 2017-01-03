package com.tukkeendoo.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tukkeendoo.app.application.Tukkeendoo;
import com.tukkeendoo.app.utils.thread_manager.Task;
import com.tukkeendoo.app.utils.thread_manager.TaskManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fallou on 20/05/2016.
 */
public class Database extends SQLiteOpenHelper{

    private static final String LOG_TAG = Database.class.getSimpleName();
    private static final String DB_NAME = "tukkeendoo.db";
    private static final int DB_VERSION = 2;
    private static String DB_SQL_FILE_NAME = "tukkeendoo.sql";
    private static Database instance;
    private SQLiteDatabase database;


    private Database(){
        super(Tukkeendoo.getInstance(), DB_NAME, null, DB_VERSION);
    }

    public static Database getInstance() {
        if (instance == null){
            instance = new Database();
        }
        return instance;
    }

    public synchronized SQLiteDatabase getDatabase(){
        if (database == null){
            database = getWritableDatabase();
        }
       return database;
    }

    private List<String> readFile(){
        List<String> statements = new ArrayList<>();

        try {
            InputStream in = Tukkeendoo.getInstance().getAssets().open(DB_SQL_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;

            StringBuilder statement = new StringBuilder();
            while ((line = reader.readLine()) != null){
                line = line.replaceAll("\r", " ").replaceAll("\n", " ").replaceAll("\\\\", " ");
                statement.append(line);
                if (line.contains(";")) {
                    statements.add(statement.toString());
                    statement = new StringBuilder();
                }
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error reading file", e);
        }
        return statements;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<String> statements = readFile();
        db.beginTransaction();
        try {
            for (String sql : statements){
                db.execSQL(sql);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(LOG_TAG, "Error creating database", e);
        }finally {
            db.endTransaction();
            close();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            onCreate(db);
            Log.v(LOG_TAG, "Upgraded database from version " + oldVersion + "to " + newVersion);
        }
    }

    public static void execSQL(String sql){
        getInstance().getDatabase().execSQL(sql);
    }

    public static void insert(final String tableName, final ContentValues values){
        TaskManager.execute(new Task() {
            @Override
            protected Object doInBackground(Object[] objects) {

                SQLiteDatabase db = null;

                try {
                    db = getInstance().getDatabase();
                    db.beginTransaction();
                    db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                    db.setTransactionSuccessful();
                }catch (Exception e){
                    Log.e(LOG_TAG, "Error saving data", e);
                }finally {
                    db.endTransaction();
//                    db.close();
                }

                return null;
            }

        });
    }

    public static Cursor query(String sql){
        SQLiteDatabase db = getInstance().getDatabase();
        return null;
    }

    public void close(){
        if (database != null) {
//            database.close();
        }
    }
}
