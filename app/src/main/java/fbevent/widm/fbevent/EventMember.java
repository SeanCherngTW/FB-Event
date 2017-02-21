package fbevent.widm.fbevent;

import java.io.Serializable;

/**
 * Created by Sean on 2017/2/10.
 */

public class EventMember implements Serializable {
    private String Title, Time, Address, Http, Description;
    private double longitude, latitude;

    public EventMember(String Title, String Time,
                       String Address, String Http,
                       double longitude, double latitude,
                       String Description) {
        this.Title = Title;
        this.Time = Time;
        this.Address = Address;
        this.Http = Http;
        this.longitude = longitude;
        this.latitude = latitude;
        this.Description = Description;
    }

    public String getTitle() {
        return Title;
    }

    public String getTime() {
        return Time;
    }

    public String getAddress() {
        return Address;
    }

    public String getHttp() {
        return Http;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() { return Description; }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setHttp(String Http) {
        this.Http = Http;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDescription(String description) { this.Description = description; }
}
