package src.MST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateMSTTest {
    CalculateMST m = new CalculateMST();
    ArrayList<Order> orders;


    @BeforeEach
    void setUp() {
        orders = m.getRandomOrder();
    }

    @Test
    void deliverCities() {
        HashSet<City> cities = m.deliverCities(orders);
//        for (City city : cities) {
//            System.out.println(city.getName());
//        }
        assertNotNull(cities);
    }

    @Test
    void deliverFromOneWareHouse() {
        HashSet<Warehouse> warehouses = m.delWareHouse(orders);
//        for (Warehouse warehouse : warehouses) {
//            System.out.println(warehouse.getCity());
//        }
        assertNotNull(warehouses);
    }

    @Test
    void orderBooks() {
        HashSet<String> books = m.orderBooks(orders);
//        for (String book : books) {
//            System.out.println(book);
//        }
        assertNotNull(books);
    }

    @Test
    void findBestWarehouseForMST() {
        CalculateMST m = new CalculateMST();

        GoogleMapsGenerator gmg = new GoogleMapsGenerator();

        HashSet<Warehouse> warehouses = m.delWareHouse(orders);

        HashSet<City> cities = m.deliverCities(orders);
        ArrayList<City> cityArrayList = new ArrayList<>();
        Warehouse bestWarehouseForMST = m.findBestWarehouseForMST(warehouses, orders);
        ArrayList<Tuple<City, City>> mstEdges = m.getMSTEdges();

        City wareHouseCity = m.getDp().getMyCityMap().get(bestWarehouseForMST.getCity());
        cityArrayList.add(wareHouseCity);
        for (City city1 : cities) {
            cityArrayList.add(city1);
        }
        assertNotNull(mstEdges);
        assertNotNull(cityArrayList);
        gmg.generatePresentation(cityArrayList, mstEdges,"single_deliver_example5");
    }

    @Test
    void findMSTForMultiWareHouse() {
        ArrayList<City> cityArrayList = new ArrayList<>();
        GoogleMapsGenerator gmg = new GoogleMapsGenerator();
        HashSet<Warehouse> warehouses = m.delWareHouse(orders);
        ArrayList<Warehouse> warehouseArrayList =  new ArrayList<>(warehouses);
        List<City> randomWarehousesCities = m.getRandomWarehousesCities(warehouseArrayList, 2);
        cityArrayList.addAll(randomWarehousesCities);

        int mstForMultiWareHouse = m.findMSTForMultiWareHouse(randomWarehousesCities, orders);
        ArrayList<Tuple<City, City>> mstEdges = m.getMSTEdges();

        HashSet<City> cities = m.deliverCities(orders);
        cityArrayList.addAll(cities);

        assertTrue(mstForMultiWareHouse !=0);

        randomWarehousesCities = m.getRandomWarehousesCities(warehouseArrayList, 4);
        cityArrayList.addAll(randomWarehousesCities);

        mstForMultiWareHouse = m.findMSTForMultiWareHouse(randomWarehousesCities, orders);
//        System.out.println(mstForMultiWareHouse);
        assertTrue(mstForMultiWareHouse !=0);
    }

    @Test
    void deliverFromSingleWarehouseRoute(){
        ArrayList<City> cities = m.deliverFromSingleWarehouseRoute();
        GoogleMapsGenerator gmg = new GoogleMapsGenerator();

        gmg.generatePresentation(cities,"single_deliver_example3",true);
        assertNotNull(cities);
    }
}