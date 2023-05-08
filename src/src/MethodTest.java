package src;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {

    @Test
    void deliverCities() {

    }

    @Test
    void deliverFromOneWareHouse() {
    }

    @Test
    void orderBooks() {
    }

    @Test
    void deliverBooksFromOneWareHouse() {
        Method m = new Method();

        GoogleMapsGenerator gmg = new GoogleMapsGenerator();
        ArrayList<Tuple<City, City>> list = m.deliverFromSingleWarehouse();
        ArrayList<City> cityArrayList = new ArrayList<>();
        for (City c: m.getCities()) cityArrayList.add(c);
        gmg.generatePresentation(cityArrayList, list,"single_deliver_example");


        gmg.generatePresentation(m.deliverFromSingleWarehouseRoute(),"single_deliver_example_route", true);
    }

    @Test
    void deliverBooksFromMultiWareHouse() {
    }

    @Test
    void deliverFromMultiWareHouse() {
    }
}