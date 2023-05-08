package src;

import java.util.ArrayList;
import java.util.List;

public interface IMethods {

    public void deliverCities(ArrayList<Order> orders);

    public void deliverFromOneWareHouse(ArrayList<Order> orders);

    public List<List<Warehouse>> deliverFromMultiWareHouse();

    public void orderBooks(ArrayList<Order> orders);
    /**
     * deliver books from one warehouse to all other cities.
     * return the best warehouse
     *
     */
    public String deliverBooksFromOneWareHouse();

    /**
     * books are from more than 1 warehouse
     * return the best collection of these warehouses
     */
    public List<String> deliverBooksFromMultiWareHouse(ArrayList<Order> orders);


}
