package fbevent.widm.fbevent;

/**
 * Created by Sean on 2017/2/10.
 */

public class EventMember {
    private String Title;
    private String Time;
    private String Address;
    private String Http;
    private double longitude, latitude;

    public EventMember(String Title, String Time, String Address, String Http, double longitude, double latitude){
        this.Title = Title;
        this.Time = Time;
        this.Address = Address;
        this.Http = Http;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTitle(String Title){
        this.Title = Title;
    }

    public void setTime(String Time){
        this.Time = Time;
    }

    public void setAddress(String Address){
        this.Address = Address;
    }

    public void setHttp(String Http) {
        this.Http = Http;
    }
}
