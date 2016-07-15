package com.tukkeendoo.app.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fallou on 20/05/2016.
 */
public class Trip {
    private static final String LOG_TAG = Trip.class.getSimpleName();
    @SerializedName("trip_id")
    private int id;
    @SerializedName("trip_driver_id")
    private String driverId;
    @SerializedName("trip_vehicle_id")
    private String vehicleId;
    @SerializedName("trip_from_latlan")
    private String fromLatLan;
    @SerializedName("trip_to_latlan")
    private String toLatLan;
    private String source;
    private String destination;
    private String route;
    @SerializedName("route_full_data")
    private String routeFullData;
    @SerializedName("trip_routes_lat_lan")
    private String routeLat;
    @SerializedName("trip_routes")
    private String routes;
    @SerializedName("trip_return")
    private String returned;
    @SerializedName("trip_departure_time")
    private Date departureTime;
    @SerializedName("trip_return_time")
    private Date returnTime;
    @SerializedName("trip_journey_hours")
    private String journeyHours;
    @SerializedName("trip_amenities")
    private String amenities;
    @SerializedName("trip_frequency")
    private String frequency;
    @SerializedName("trip_available_seat")
    private String availableSeat;
    @SerializedName("passenger_type")
    private String passengerType;
    @SerializedName("trip_rate_details")
    private String rateDetails;
    @SerializedName("contact_person_name")
    private String contactPersonName;
    @SerializedName("contact_person_number")
    private String contactPersonNumber;
    @SerializedName("trip_comments")
    private String comments;
    @SerializedName("trip_user_id")
    private String userId;
    @SerializedName("trip_casual_date")
    private String casualDate;
    @SerializedName("trip_created_date")
    private String createdDate;
    @SerializedName("trip_status")
    private boolean status;
    @SerializedName("trip_public")
    private int publicc;
    @SerializedName("trip_otp_status")
    private boolean otpStatus;
    private int flexibility;
    private int detour;
    @SerializedName("luggage_size")
    private int luggageSize;

    public Trip(int id, String driverId, String vehicleId, String fromLatLan, String toLatLan, String source, String destination, String route, String routeFullData, String routeLat, String routes, String returned, Date departureTime, Date returnTime, String journeyHours, String amenities, String frequency, String availableSeat, String passengerType, String rateDetails, String contactPersonName, String contactPersonNumber, String comments, String userId, String casualDate, String createdDate, boolean status, int publicc, boolean otpStatus, int flexibility, int detour, int luggageSize) {
        this.id = id;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.fromLatLan = fromLatLan;
        this.toLatLan = toLatLan;
        this.source = source;
        this.destination = destination;
        this.route = route;
        this.routeFullData = routeFullData;
        this.routeLat = routeLat;
        this.routes = routes;
        this.returned = returned;
        this.departureTime = departureTime;
        this.returnTime = returnTime;
        this.journeyHours = journeyHours;
        this.amenities = amenities;
        this.frequency = frequency;
        this.availableSeat = availableSeat;
        this.passengerType = passengerType;
        this.rateDetails = rateDetails;
        this.contactPersonName = contactPersonName;
        this.contactPersonNumber = contactPersonNumber;
        this.comments = comments;
        this.userId = userId;
        this.casualDate = casualDate;
        this.createdDate = createdDate;
        this.status = status;
        this.publicc = publicc;
        this.otpStatus = otpStatus;
        this.flexibility = flexibility;
        this.detour = detour;
        this.luggageSize = luggageSize;
    }

    public static List<Trip> tripsFromJsonArray(JSONArray jsonArray){
        List<Trip> trips = null;
        if (jsonArray != null)
        {
            trips = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject tripObject = null;
                Gson gson = new Gson();
                try {
                    tripObject =  jsonArray.getJSONObject(i);
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "", e);
                }
                if (tripObject != null) {
                    Trip trip = gson.fromJson(tripObject.toString(), Trip.class);
                    trips.add(trip);
                }
            }
        }
        else
        {
            Log.e(LOG_TAG, "Parameter jsonArray is nul");
        }

        return trips;
    }

    public static Trip tripFromJsonObject(JSONObject jsonObject){
        return new Gson().fromJson(jsonObject.toString(), Trip.class);
    }

    public static Trip tripFromJsonString(String jsonObject){
        return new Gson().fromJson(jsonObject, Trip.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getFromLatLan() {
        return fromLatLan;
    }

    public void setFromLatLan(String fromLatLan) {
        this.fromLatLan = fromLatLan;
    }

    public String getToLatLan() {
        return toLatLan;
    }

    public void setToLatLan(String toLatLan) {
        this.toLatLan = toLatLan;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRouteFullData() {
        return routeFullData;
    }

    public void setRouteFullData(String routeFullData) {
        this.routeFullData = routeFullData;
    }

    public String getRouteLat() {
        return routeLat;
    }

    public void setRouteLat(String routeLat) {
        this.routeLat = routeLat;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getJourneyHours() {
        return journeyHours;
    }

    public void setJourneyHours(String journeyHours) {
        this.journeyHours = journeyHours;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(String availableSeat) {
        this.availableSeat = availableSeat;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public String getRateDetails() {
        return rateDetails;
    }

    public void setRateDetails(String rateDetails) {
        this.rateDetails = rateDetails;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(String contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCasualDate() {
        return casualDate;
    }

    public void setCasualDate(String casualDate) {
        this.casualDate = casualDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPublicc() {
        return publicc;
    }

    public void setPublicc(int publicc) {
        this.publicc = publicc;
    }

    public boolean isOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(boolean otpStatus) {
        this.otpStatus = otpStatus;
    }

    public int getFlexibility() {
        return flexibility;
    }

    public void setFlexibility(int flexibility) {
        this.flexibility = flexibility;
    }

    public int getDetour() {
        return detour;
    }

    public void setDetour(int detour) {
        this.detour = detour;
    }

    public int getLuggageSize() {
        return luggageSize;
    }

    public void setLuggageSize(int luggageSize) {
        this.luggageSize = luggageSize;
    }
}
