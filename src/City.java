package src;

import java.util.HashMap;

public class City {
    private int[] zipcodes;
    private int longitude;
    private int latitude;
    private HashMap<City, Double> connectingCities;
}
