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
        gmg.generatePresentation(new ArrayList<>(dp.getMyCityMap().values()),"all_cities_parsed", false);
        ArrayList<City> tree = new ArrayList<>();
        HashMap<String,City> map = dp.getMyCityMap();
        tree.add(map.get("new york"));
        tree.add(map.get("minneapolis"));
        tree.add(map.get("colorado springs"));
        tree.add(map.get("salt lake city"));
        tree.add(map.get("portland"));
        tree.add(map.get("san jose"));
        gmg.generatePresentation(tree,"tree_example", true);
    }
}