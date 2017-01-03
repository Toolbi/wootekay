package com.tukkeendoo.app.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.tukkeendoo.app.config.TukkeeConfig;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPRequestListener;
import com.tukkeendoo.app.network.Webservice;

import java.util.HashMap;
import java.util.Map;

import static com.tukkeendoo.app.database.dao.UserDAO.ABOUT;
import static com.tukkeendoo.app.database.dao.UserDAO.ALLOWED_CHAT;
import static com.tukkeendoo.app.database.dao.UserDAO.ALLOWED_FOOD;
import static com.tukkeendoo.app.database.dao.UserDAO.ALLOWED_MAIL;
import static com.tukkeendoo.app.database.dao.UserDAO.ALLOWED_MUSIC;
import static com.tukkeendoo.app.database.dao.UserDAO.ALLOWED_SHOW_NUMBER;
import static com.tukkeendoo.app.database.dao.UserDAO.ALLOWED_SMOKE;
import static com.tukkeendoo.app.database.dao.UserDAO.ALLOWED_SMS;
import static com.tukkeendoo.app.database.dao.UserDAO.DOB;
import static com.tukkeendoo.app.database.dao.UserDAO.EMAIL;
import static com.tukkeendoo.app.database.dao.UserDAO.FISRT_NAME;
import static com.tukkeendoo.app.database.dao.UserDAO.GENDER;
import static com.tukkeendoo.app.database.dao.UserDAO.ID;
import static com.tukkeendoo.app.database.dao.UserDAO.LAST_NAME;
import static com.tukkeendoo.app.database.dao.UserDAO.MOBILE;

/**
 * Created by fallou on 11/05/2016.
 */
public class User extends Model{
    @SerializedName("id")
    private int id;
    @SerializedName("firstname")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("phone")
    private String mobile;
    @SerializedName("email")
    private String email;
    @SerializedName("gender")
    private boolean gender;//F = true
    @SerializedName("dob")
    private String dayOfBirth;
    @SerializedName("mail_flg")
    private boolean allowedEmail;
    @SerializedName("aboutus")
    private String about;
    @SerializedName("show_number")
    private boolean allowedShowNumber;
    @SerializedName("sms")
    private boolean allowedSMS;
    @SerializedName("food")
    private boolean allowedFood;
    @SerializedName("pet")
    private boolean allowedPet;
    @SerializedName("allowed_smoke")
    private boolean allowedSmoke;
    @SerializedName("allowed_chat")
    private boolean allowedChat;
    @SerializedName("allowed_music")
    private boolean allowedMusic;
//    @SerializedName("profile_img")
//    private String profileImage;
//    @SerializedName("url")
//    private String url;

    public User(){}

    public User(int id, String firstName, String lastName, String mobile, String email, boolean gender, String dayOfBirth, boolean allowedEmail, String about, boolean allowedShowNumber, boolean allowedSMS, boolean allowedFood, boolean allowedPet, boolean allowedSmoke, boolean allowedChat, boolean allowedMusic) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.gender = gender;
        this.dayOfBirth = dayOfBirth;
        this.allowedEmail = allowedEmail;
        this.about = about;
        this.allowedShowNumber = allowedShowNumber;
        this.allowedSMS = allowedSMS;
        this.allowedFood = allowedFood;
        this.allowedPet = allowedPet;
        this.allowedSmoke = allowedSmoke;
        this.allowedChat = allowedChat;
        this.allowedMusic = allowedMusic;
    }

    public static void registerUser(HTTPRequestListener listener, String firstName,
                                    String lastName, String phone, String email, String password){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("first_name", firstName);
        parameters.put("last_name", lastName);
        parameters.put("phone", phone);
        parameters.put("email", email);
        parameters.put("password", password);
        parameters.put("submitted", "submitted");

        HTTPRequest request = new HTTPRequest(TukkeeConfig.REGISTER, HTTPRequest.POST, parameters);
        Webservice.executeRequestWithListener(request, listener);
    }

    public static void loginUser(HTTPRequestListener listener, String userName, String password){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("email", userName);
        parameters.put("password", password);
        parameters.put("remember", true);
        parameters.put("redirect", "home");
        parameters.put("submitted", true);
        HTTPRequest request = new HTTPRequest(TukkeeConfig.LOGIN, HTTPRequest.POST, parameters);
        Webservice.executeRequestWithListener(request, listener);
    }

    public static void loginUserByToken(HTTPRequestListener listener, String token){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("token", token);
        parameters.put("remember", true);
        parameters.put("redirect", "home");
        parameters.put("submitted", true);
        HTTPRequest request = new HTTPRequest(TukkeeConfig.LOGIN_BY_TOKEN, HTTPRequest.POST, parameters);
        Webservice.executeRequestWithListener(request, listener);
    }

    public static void logoutUser(HTTPRequestListener listener){
        HTTPRequest request = new HTTPRequest(TukkeeConfig.LOGOUT, HTTPRequest.POST, null);
        Webservice.executeRequestWithListener(request, listener);
        //TODO destroy session cookies and token
        Preferences.saveCookies(null);
        Preferences.saveUserPreference(Preferences.USER_TOKEN, null);
        Preferences.saveBooleanPreference(Preferences.ALREADY_LOGGED_IN, false);
    }

    public static boolean isUserAlreadyLogin(){
        return Preferences.retrieveBooleanPreference(Preferences.ALREADY_LOGGED_IN, false);
    }

    @Override
    public ContentValues getContentValues(){
        final ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(FISRT_NAME, firstName);
        values.put(LAST_NAME, lastName);
        values.put(MOBILE, mobile);
        values.put(EMAIL, email);
        values.put(GENDER, gender);
        values.put(DOB, dayOfBirth);
        values.put(ALLOWED_MAIL, allowedEmail);
        values.put(ABOUT, about);
        values.put(ALLOWED_SHOW_NUMBER, allowedShowNumber);
        values.put(ALLOWED_SMS, allowedSMS);
        values.put(ALLOWED_FOOD, allowedFood);
        values.put(ALLOWED_SMOKE, allowedSmoke);
        values.put(ALLOWED_CHAT, allowedChat);
        values.put(ALLOWED_MUSIC, allowedMusic);

        return values;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFemale() {
        return gender;
    }

    public void setGender(boolean female) {
        this.gender = female;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public boolean isAllowedEmail() {
        return allowedEmail;
    }

    public void setAllowedEmail(boolean allowedEmail) {
        this.allowedEmail = allowedEmail;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isAllowedShowNumber() {
        return allowedShowNumber;
    }

    public void setAllowedShowNumber(boolean allowedShowNumber) {
        this.allowedShowNumber = allowedShowNumber;
    }

    public boolean isAllowedSMS() {
        return allowedSMS;
    }

    public void setAllowedSMS(boolean allowedSMS) {
        this.allowedSMS = allowedSMS;
    }

    public boolean isAllowedFood() {
        return allowedFood;
    }

    public void setAllowedFood(boolean allowedFood) {
        this.allowedFood = allowedFood;
    }

    public boolean isAllowedPet() {
        return allowedPet;
    }

    public void setAllowedPet(boolean allowedPet) {
        this.allowedPet = allowedPet;
    }

    public boolean isAllowedSmoke() {
        return allowedSmoke;
    }

    public void setAllowedSmoke(boolean allowedSmoke) {
        this.allowedSmoke = allowedSmoke;
    }

    public boolean isAllowedChat() {
        return allowedChat;
    }

    public void setAllowedChat(boolean allowedChat) {
        this.allowedChat = allowedChat;
    }

    public boolean isAllowedMusic() {
        return allowedMusic;
    }

    public void setAllowedMusic(boolean allowedMusic) {
        this.allowedMusic = allowedMusic;
    }

    public static User fromDatabaseCursor(Cursor cursor) {
        User user = new User();
        user.id = cursor.getInt(cursor.getColumnIndex(ID));
        user.firstName = cursor.getString(cursor.getColumnIndex(FISRT_NAME));
        user.lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME));
        user.mobile = cursor.getString(cursor.getColumnIndex(MOBILE));
        user.email = cursor.getString(cursor.getColumnIndex(EMAIL));
        user.gender = cursor.getInt(cursor.getColumnIndex(GENDER)) > 0;
        user.dayOfBirth = cursor.getString(cursor.getColumnIndex(DOB));
        user.allowedEmail = cursor.getInt(cursor.getColumnIndex(ALLOWED_MAIL)) > 0;
        user.about = cursor.getString(cursor.getColumnIndex(ABOUT));
        user.allowedShowNumber = cursor.getInt(cursor.getColumnIndex(ALLOWED_SHOW_NUMBER)) > 0;
        user.allowedSMS = cursor.getInt(cursor.getColumnIndex(ALLOWED_SMS)) > 0;
        user.allowedFood = cursor.getInt(cursor.getColumnIndex(ALLOWED_FOOD)) > 0;
        user.allowedPet = cursor.getInt(cursor.getColumnIndex(ALLOWED_CHAT)) > 0;
        user.allowedSmoke = cursor.getInt(cursor.getColumnIndex(ALLOWED_SMOKE)) > 0;
        user.allowedChat = cursor.getInt(cursor.getColumnIndex(ALLOWED_CHAT)) > 0;
        user.allowedMusic = cursor.getInt(cursor.getColumnIndex(ALLOWED_MUSIC)) > 0;

        return user;
    }

//    public String getProfileImage() {
//        return profileImage;
//    }
//
//    public void setProfileImage(String profileImage) {
//        this.profileImage = profileImage;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
}
