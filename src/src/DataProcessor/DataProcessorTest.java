package src.DataProcessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.City;
import src.GoogleMapsGenerator;
import src.Order;
import src.Warehouse;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DataProcessorTest {

    @org.junit.jupiter.api.Test
     void parseCities() {
        DataProcessor dp = new DataProcessor();
        dp.parseCities();
        HashMap<String, City> myMap = dp.getMyCityMap();
        assertEquals(85, myMap.size());
        double d = myMap.get("miami").getConnectingCities().get("dallas").getRight();
        assertEquals(1781.8, d, 10);
    }

    @org.junit.jupiter.api.Test
    void initialization() {
        DataProcessor dp = new DataProcessor();
        dp.initialization();
        ArrayList<Warehouse> wl = dp.getMyWareHouseList();
        assertEquals(85, wl.size());
        for (Warehouse w : wl) {
            assertTrue(w.getStorageMap().size() > 0);
        }

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

    @Test
    void generateOrderFromOneWarehouse() {
        DataProcessor dp = new DataProcessor();
        dp.initialization();
        Warehouse w = dp.getRandomWarehouse();
        ArrayList<Order> arr = dp.generateOrderFromOneWarehouse(w);
        assertTrue(arr.size() > 0);
        for (Order order : arr) {
            assertEquals(1, order.getBookList().size());
        }
    }

    @Test
    void generateOrderWithMultipleBooks() {
        DataProcessor dp = new DataProcessor();
        dp.initialization();
        ArrayList<Order> arr = dp.generateOrderWithMultipleBooks();
        assertTrue(arr.size() > 0);
        for (Order order : arr) {
            assertTrue(order.getBookList().size() > 1);
        }
    }
}