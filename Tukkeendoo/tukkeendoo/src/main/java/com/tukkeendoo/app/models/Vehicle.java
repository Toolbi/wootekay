package com.tukkeendoo.app.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static com.tukkeendoo.app.database.dao.VehicleDAO.ACTIVE;
import static com.tukkeendoo.app.database.dao.VehicleDAO.CATEGORY_ID;
import static com.tukkeendoo.app.database.dao.VehicleDAO.CATEGORY_NAME;
import static com.tukkeendoo.app.database.dao.VehicleDAO.COMFORT;
import static com.tukkeendoo.app.database.dao.VehicleDAO.ID;
import static com.tukkeendoo.app.database.dao.VehicleDAO.IMAGE;
import static com.tukkeendoo.app.database.dao.VehicleDAO.LOGO;
import static com.tukkeendoo.app.database.dao.VehicleDAO.NUMBER;
import static com.tukkeendoo.app.database.dao.VehicleDAO.TYPE_ID;
import static com.tukkeendoo.app.database.dao.VehicleDAO.TYPE_NAME;
import static com.tukkeendoo.app.database.dao.VehicleDAO.USER_ID;

/**
 * Created by fallou on 23/10/2016.
 */
public class Vehicle extends Model{
    @SerializedName("vechicle_id")
    private int id;
    @SerializedName("vechicle_type_id")
    private int typeId;
    @SerializedName("vechicle_number")
    private String number;
    @SerializedName("vechicle_logo")
    private String logo;
    @SerializedName("vechiclecomfort")
    private int comfort;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("vechicle_type_name")
    private String typeName;
    @SerializedName("vechicle_image")
    private String image;
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("isactive")
    private boolean active;

    public Vehicle(){}

    public Vehicle(int id, int typeId, String number, String logo, int comfort, int userId, String typeName, String image, int categoryId, String categoryName, boolean active) {
        this.id = id;
        this.typeId = typeId;
        this.number = number;
        this.logo = logo;
        this.comfort = comfort;
        this.userId = userId;
        this.typeName = typeName;
        this.image = image;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.active = active;
    }


    public static Vehicle fromDatabaseCursor(Cursor cursor) {

        Vehicle vehicle = new Vehicle();
        vehicle.id = cursor.getInt(cursor.getColumnIndex(ID));
        vehicle.typeId = cursor.getInt(cursor.getColumnIndexOrThrow(TYPE_ID));
        vehicle.number = cursor.getString(cursor.getColumnIndex(NUMBER));
        vehicle.logo = cursor.getString(cursor.getColumnIndex(LOGO));
        vehicle.comfort = cursor.getInt(cursor.getColumnIndex(COMFORT));
        vehicle.userId = cursor.getInt(cursor.getColumnIndex(USER_ID));
        vehicle.typeName = cursor.getString(cursor.getColumnIndex(TYPE_NAME));
        vehicle.image = cursor.getString(cursor.getColumnIndex(IMAGE));
        vehicle.categoryId = cursor.getInt(cursor.getColumnIndex(CATEGORY_ID));
        vehicle.categoryName = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
        vehicle.active = cursor.getInt(cursor.getColumnIndex(ACTIVE)) > 0;

        return vehicle;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(TYPE_ID, typeId);
        values.put(NUMBER, number);
        values.put(LOGO, logo);
        values.put(COMFORT, comfort);
        values.put(USER_ID, userId);
        values.put(TYPE_NAME, typeName);
        values.put(IMAGE, image);
        values.put(CATEGORY_ID, categoryId);
        values.put(CATEGORY_NAME, categoryName);
        values.put(ACTIVE, active);
        return values;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getComfort() {
        return comfort;
    }

    public void setComfort(int comfort) {
        this.comfort = comfort;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}