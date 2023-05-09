package src;

import java.util.HashMap;

public class City {

    private static final double EARTH_R = 6371;
//    public static final double AVG_LONGITUDE = -98.5833;
//    public static final double AVG_LATITUDE = 37.8333;

    public static double AVG_LONGITUDE;
    public static double AVG_LATITUDE;
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

    public double countDistance(City city) {
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(city.getLatitude());
        double lng1 = Math.toRadians(this.longitude);
        double lng2 = Math.toRadians(city.getLongitude());

        double latDistance = lat2 - lat1;
        double lngDistance = lng2 - lng1;

        double a = Math.pow(Math.sin(latDistance / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(lngDistance / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_R * c;

        return (distance > 0) ? distance : -distance;
    }


}
