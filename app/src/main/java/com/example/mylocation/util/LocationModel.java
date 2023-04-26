package com.example.mylocation.util;

public class LocationModel {
    private double latitude, longitude;
    String addressLine, date, time;




    public LocationModel(double latitude, double longitude, String addressLine) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressLine = addressLine;
    }

    public LocationModel() {

    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocationModel(double latitude, double longitude, String addressLine, String date, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.addressLine = addressLine;
        this.date = date;
        this.time = time;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
