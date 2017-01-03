package com.tukkeendoo.app.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tukkeendoo.app.database.Database;
import com.tukkeendoo.app.models.User;

/**
 * Created by fallou on 26/11/2016.
 */

public class UserDAO  {

    private static final String TABLE_USER = "users";
    private static String LOG_TAG = UserDAO.class.getSimpleName();

    public static String ID = "id";
    public static String FISRT_NAME = "first_name";
    public static String LAST_NAME = "last_name";
    public static String MOBILE = "mobile";
    public static String EMAIL = "email";
    public static String GENDER = "gender";
    public static String DOB = "dob";
    public static String ALLOWED_MAIL = "allowed_mail";
    public static String ABOUT = "about";
    public static String ALLOWED_SHOW_NUMBER = "allowed_show_number";
    public static String ALLOWED_SMS = "allowed_sms";
    public static String ALLOWED_FOOD = "allowed_food";
    public static String ALLOWED_PET = "allowed_pet";
    public static String ALLOWED_SMOKE = "allowed_smoke";
    public static String ALLOWED_CHAT = "allowed_chat";
    public static String ALLOWED_MUSIC = "allowed_music";

    public static void save(final User user){
        Database.insert(TABLE_USER, user.getContentValues());
    }

    public static User getUser(){
        User user = null;

        String sql = "SELECT * FROM users ORDER BY id";

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = Database.getInstance().getDatabase();
            db.beginTransaction();
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            user = User.fromDatabaseCursor(cursor);
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(LOG_TAG, e.getMessage(), e);
        }finally {
            if (cursor != null){
                cursor.close();
            }
            if (db != null) {
                db.endTransaction();
//                db.close();
            }
        }

        return user;
    }

}
