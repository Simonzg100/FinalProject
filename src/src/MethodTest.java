package src;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.DataProcessor.DataProcessor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {
    Method m = new Method();
    ArrayList<Order> orders;


    @BeforeEach
    void setUp() {
        orders = m.getRandomOrder();
    }

    @Test
    void deliverCities() {
        HashSet<City> cities = m.deliverCities(orders);
        for (City city : cities) {
            System.out.println(city.getName());
        }
        assertNotNull(cities);
    }

    @Test
    void deliverFromOneWareHouse() {
        HashSet<Warehouse> warehouses = m.delWareHouse(orders);
        for (Warehouse warehouse : warehouses) {
            System.out.println(warehouse.getCity());
        }
        assertNotNull(warehouses);
    }

    @Test
    void orderBooks() {
        HashSet<String> books = m.orderBooks(orders);
        for (String book : books) {
            System.out.println(book);
        }
        assertEquals(23,books.size());
    }

    @Test
    void deliverBooksFromOneWareHouse() {
        Method m = new Method();

        GoogleMapsGenerator gmg = new GoogleMapsGenerator();

        HashSet<Warehouse> warehouses = m.delWareHouse(orders);
        HashSet<City> cities = m.deliverCities(orders);
        ArrayList<City> cityArrayList = new ArrayList<>();
                    System.out.println(cities.size());
        Warehouse bestWarehouseForMST = m.findBestWarehouseForMST(warehouses, orders);
        City city = new City();
        city.setName(bestWarehouseForMST.getCity());
        cityArrayList.add(city);
//        gmg.generatePresentation(cityArrayList, list,"single_deliver_example");

                    System.out.println(bestWarehouseForMST.getCity());

        gmg.generatePresentation(cityArrayList,"single_deliver_example_route2", true);
    }

    @Test
    void findMSTForMultiWareHouse() {
        HashSet<Warehouse> warehouses = m.delWareHouse(orders);
        ArrayList<Warehouse> warehouseArrayList =  new ArrayList<>(warehouses);
        List<City> randomWarehousesCities = m.getRandomWarehousesCities(warehouseArrayList, 2);
        System.out.println(randomWarehousesCities.size());

        int mstForMultiWareHouse = m.findMSTForMultiWareHouse(randomWarehousesCities, orders);
        System.out.println(mstForMultiWareHouse);

        assertTrue(mstForMultiWareHouse !=0);
    }
}