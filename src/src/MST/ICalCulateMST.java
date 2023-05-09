package src.MST;

import src.City;
import src.Order;
import src.Warehouse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface ICalCulateMST {

    public HashSet<City> deliverCities(ArrayList<Order> orders);

    public HashSet<Warehouse> delWareHouse(ArrayList<Order> orders);


    public HashSet<String> orderBooks(ArrayList<Order> orders);
    /**
     * deliver books from one warehouse to all other cities.
     * return the best warehouse
     *
     */
    public Warehouse findBestWarehouseForMST(HashSet<Warehouse> warehouseList, ArrayList<Order> orders);

    /**
     * books are from more than 1 warehouse
     * return the best collection of these warehouses
     */
    public int findMSTForMultiWareHouse(List<City> cities, ArrayList<Order> orders);

    public ArrayList<City> deliverFromSingleWarehouseRoute();
}
