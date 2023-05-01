package src.DataProcessor;

import src.City;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataProcessor {

    private static final double EARTH_R = 6371;
    public void parseCities() {
        try {
            File f = new File("uscities.csv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<City> cities = new ArrayList<>();
            String str = br.readLine();
            while ((str = br.readLine()) != null) {
                str.replace("\"", "");
                System.out.println(str);
                String[] strArray = str.strip().split(",");
                if (strArray.length != 17) continue;
                if (Integer.parseInt(strArray[8].strip()) <= 500000) break;
                City c = new City();
                c.setName(strArray[1].strip().toLowerCase());
                c.setState(strArray[2].strip().toUpperCase());
                c.setLatitude(Double.parseDouble(strArray[6].strip()));
                c.setLongitude(Double.parseDouble(strArray[7].strip()));
                c.setZipcodes(strArray[15].strip().split("\\s+"));
                cities.add(c);
            }

            for (City city : cities) {
                HashMap<City, Double> myMap = new HashMap<>();
                for (City city1 : cities) {
                    if (city.equals(city1)) continue;
                    myMap.put(city1, countDistance(city, city1));
                }
                city.setConnectingCities(myMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private double countDistance(City c1, City c2) {
        double lat1 = Math.toRadians(c1.getLatitude());
        double lat2 = Math.toRadians(c2.getLatitude());
        double lng1 = Math.toRadians(c1.getLongitude());
        double lng2 = Math.toRadians(c2.getLongitude());

        double latDistance = lat2 - lat1;
        double lngDistance = lng2 - lng1;

        double a = Math.pow(Math.sin(latDistance / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(lngDistance / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (EARTH_R * c > 0) ? a : -a;
    }
}
