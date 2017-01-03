package com.tukkeendoo.app.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tukkeendoo.app.database.Database;
import com.tukkeendoo.app.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fallou on 20/12/2016.
 */

public class VehicleDAO {
    private static final String TABLE_VEHICLES = "vehicles";
    private static String LOG_TAG = VehicleDAO.class.getSimpleName();

    public static String ID = "id";
    public static String TYPE_ID = "type_id";
    public static String NUMBER = "v_number";
    public static String LOGO = "logo";
    public static String COMFORT = "comfort";
    public static String USER_ID = "user_id";
    public static String TYPE_NAME = "type_name";
    public static String IMAGE = "image";
    public static String CATEGORY_ID = "category_id";
    public static String CATEGORY_NAME = "category_name";
    public static String ACTIVE = "is_active";

    public static void save(Vehicle vehicle){
        Database.insert(TABLE_VEHICLES, vehicle.getContentValues());
    }

    public static Vehicle getVehicle(int id){
        Vehicle vehicle = null;

        String sql = "SELECT * FROM users WHERE id = " + id;

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = Database.getInstance().getDatabase();
            db.beginTransaction();
            cursor = db.rawQuery(sql, null);
            cursor.moveToFirst();
            vehicle = Vehicle.fromDatabaseCursor(cursor);
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

        return vehicle;
    }

    public static List<Vehicle> getVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();

        String sql = "SELECT * FROM vehicles ORDER BY id";

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = Database.getInstance().getDatabase();
            db.beginTransaction();
            cursor = db.rawQuery(sql, null);
            while (cursor != null && cursor.moveToNext()){
                Vehicle vehicle = Vehicle.fromDatabaseCursor(cursor);
                if (vehicle != null) {
                    vehicles.add(vehicle);
                }
            }

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

        return vehicles;
    }
}
