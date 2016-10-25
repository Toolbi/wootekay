package com.tukkeendoo.app.models;

import com.google.gson.annotations.SerializedName;
import com.tukkeendoo.app.config.Tukkeendoo;
import com.tukkeendoo.app.network.HTTPRequest;
import com.tukkeendoo.app.network.HTTPRequestListener;
import com.tukkeendoo.app.network.Webservice;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fallou on 11/05/2016.
 */
public class User {
    @SerializedName("user_id")
    private int id;
    @SerializedName("user_email")
    private String email;
    @SerializedName("user_password")
    private String password;
    @SerializedName("user_token")
    private String token;
    @SerializedName("user_type")
    private String type;
    @SerializedName("user_company_name")
    private String companyName;
    @SerializedName("user_first_name")
    private String firstName;
    @SerializedName("user_last_name")
    private String lastName;
    @SerializedName("user_about_us")
    private String about;
    @SerializedName("user_profile_img")
    private String profileImage;
    @SerializedName("user_mobile")
    private String mobile;
    @SerializedName("user_secondary_phone")
    private String secondaryPhone;
    @SerializedName("user_secondary_email")
    private String secondaryEmail;
    @SerializedName("user_company_id")
    private String companyId;
    @SerializedName("user_url")
    private String url;
    @SerializedName("user_street")
    private String street;
    @SerializedName("user_city")
    private String city;
    @SerializedName("postal_code")
    private int postalCode;
    @SerializedName("user_occupation")
    private String occupation;
    @SerializedName("marital_status")
    private String marital_status;
    @SerializedName("isverified")
    private String verified;
    @SerializedName("show_number")
    private String showNumber;
    @SerializedName("send_sms")
    private String sendSMS;
    @SerializedName("allowed_food")
    private String allowedFood;
    @SerializedName("allowed_pet")
    private String allowedPet;
    @SerializedName("allowed_smoke")
    private String allowedSmoke;
    @SerializedName("allowed_chat")
    private String allowedChat;
    @SerializedName("allowed_music")
    private String allowedMusic;
    @SerializedName("user_gender")
    private String gender;
    @SerializedName("user_contry")
    private String country;
    @SerializedName("user_dob")
    private String dayOfBirth;
    @SerializedName("communation_mobile")
    private String communicationMobile;
    @SerializedName("communication_email")
    private String communicationEmail;
    @SerializedName("login_type")
    private String loginType;
    @SerializedName("isactive")
    private boolean active;
    @SerializedName("user_admin_status")
    private int adminStatus;
    @SerializedName("user_created_date")
    private Date createdDate;
    @SerializedName("user_last_login")
    private Date lastLogin;

    public User(int id, String email, String password, String token, String type, String companyName, String firstName, String lastName, String about, String profileImage, String mobile, String secondaryPhone, String secondaryEmail, String companyId, String url, String street, String city, int postalCode, String occupation, String marital_status, String verified, String showNumber, String sendSMS, String allowedFood, String allowedPet, String allowedSmoke, String allowedChat, String allowedMusic, String gender, String country, String dayOfBirth, String communicationMobile, String communicationEmail, String loginType, boolean active, int adminStatus, Date createdDate, Date lastLogin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.token = token;
        this.type = type;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.about = about;
        this.profileImage = profileImage;
        this.mobile = mobile;
        this.secondaryPhone = secondaryPhone;
        this.secondaryEmail = secondaryEmail;
        this.companyId = companyId;
        this.url = url;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.occupation = occupation;
        this.marital_status = marital_status;
        this.verified = verified;
        this.showNumber = showNumber;
        this.sendSMS = sendSMS;
        this.allowedFood = allowedFood;
        this.allowedPet = allowedPet;
        this.allowedSmoke = allowedSmoke;
        this.allowedChat = allowedChat;
        this.allowedMusic = allowedMusic;
        this.gender = gender;
        this.country = country;
        this.dayOfBirth = dayOfBirth;
        this.communicationMobile = communicationMobile;
        this.communicationEmail = communicationEmail;
        this.loginType = loginType;
        this.active = active;
        this.adminStatus = adminStatus;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
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

        HTTPRequest request = new HTTPRequest(Tukkeendoo.REGISTER, HTTPRequest.POST, parameters);
        Webservice.executeRequestWithListener(request, listener);
    }

    public static void loginUser(HTTPRequestListener listener, String userName, String password){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("email", userName);
        parameters.put("password", password);
        parameters.put("remember", true);
        parameters.put("redirect", "home");
        parameters.put("submitted", true);
        HTTPRequest request = new HTTPRequest(Tukkeendoo.LOGIN, HTTPRequest.POST, parameters);
        Webservice.executeRequestWithListener(request, listener);
    }

    public static void loginUserByToken(HTTPRequestListener listener, String token){
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("token", token);
        parameters.put("remember", true);
        parameters.put("redirect", "home");
        parameters.put("submitted", true);
        HTTPRequest request = new HTTPRequest(Tukkeendoo.LOGIN_BY_TOKEN, HTTPRequest.POST, parameters);
        Webservice.executeRequestWithListener(request, listener);
    }

    public static void logoutUser(HTTPRequestListener listener){
        HTTPRequest request = new HTTPRequest(Tukkeendoo.LOGOUT, HTTPRequest.POST, null);
        Webservice.executeRequestWithListener(request, listener);
        //TODO destroy session cookies and token
        Preferences.saveCookies(null);
        Preferences.savePreference(Preferences.USER_TOKEN, Preferences.USER_TOKEN, null);
        Preferences.saveBooleanPreference(Preferences.ALREADY_LOGGED_IN, Preferences.ALREADY_LOGGED_IN, false);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getSecondaryEmail() {
        return secondaryEmail;
    }

    public void setSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(String showNumber) {
        this.showNumber = showNumber;
    }

    public String getSendSMS() {
        return sendSMS;
    }

    public void setSendSMS(String sendSMS) {
        this.sendSMS = sendSMS;
    }

    public String getAllowedFood() {
        return allowedFood;
    }

    public void setAllowedFood(String allowedFood) {
        this.allowedFood = allowedFood;
    }

    public String getAllowedPet() {
        return allowedPet;
    }

    public void setAllowedPet(String allowedPet) {
        this.allowedPet = allowedPet;
    }

    public String getAllowedSmoke() {
        return allowedSmoke;
    }

    public void setAllowedSmoke(String allowedSmoke) {
        this.allowedSmoke = allowedSmoke;
    }

    public String getAllowedChat() {
        return allowedChat;
    }

    public void setAllowedChat(String allowedChat) {
        this.allowedChat = allowedChat;
    }

    public String getAllowedMusic() {
        return allowedMusic;
    }

    public void setAllowedMusic(String allowedMusic) {
        this.allowedMusic = allowedMusic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getCommunicationMobile() {
        return communicationMobile;
    }

    public void setCommunicationMobile(String communicationMobile) {
        this.communicationMobile = communicationMobile;
    }

    public String getCommunicationEmail() {
        return communicationEmail;
    }

    public void setCommunicationEmail(String communicationEmail) {
        this.communicationEmail = communicationEmail;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(int adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

}
