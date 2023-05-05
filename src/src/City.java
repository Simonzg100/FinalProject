package src;

import java.util.HashMap;

public class City {
    private String name;
    private String state;
    private String[] zipcodes;
    private double longitude;
    private double latitude;
    private HashMap<String, Tuple<City, Double> > connectingCities;

    public City() {
    }

    public String[] getZipcodes() {
        return zipcodes;
    }

    public void setZipcodes(String[] zipcodes) {
        this.zipcodes = zipcodes;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public HashMap<String, Tuple<City, Double>> getConnectingCities() {
        return connectingCities;
    }

    public void setConnectingCities(HashMap<String, Tuple<City, Double>> connectingCities) {
        this.connectingCities = connectingCities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
