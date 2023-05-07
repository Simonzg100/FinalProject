package src.DataProcessor;

import src.City;
import src.GoogleMapsGenerator;
import src.Warehouse;

import java.util.ArrayList;
import java.util.HashMap;

class DataProcessorTest {

    @org.junit.jupiter.api.Test
    void parseCities() {
        DataProcessor dp = new DataProcessor();
        dp.parseCities();
        HashMap<String, City> myMap = dp.getMyCityMap();
        //System.out.println(myMap.size());
        double d = myMap.get("miami").getConnectingCities().get("dallas").getRight();
        //System.out.println(d);
    }

    @org.junit.jupiter.api.Test
    void initialization() {
        DataProcessor dp = new DataProcessor();
        dp.initialization();
        ArrayList<Warehouse> wl = dp.getMyWareHouseList();
//        for (Warehouse w : wl) {
//            System.out.println(w.getStorageMap().size());
//        }

        GoogleMapsGenerator gmg = new GoogleMapsGenerator();
        gmg.generatePresentation(new ArrayList<>(dp.getMyCityMap().values()));
    }
}