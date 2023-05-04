package src.DataProcessor;

import src.City;
import src.WareHouse;

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
        ArrayList<WareHouse> wl = dp.getMyWareHouseList();
        for (WareHouse w : wl) {
            System.out.println(w.getStorageMap().size());
        }
    }
}