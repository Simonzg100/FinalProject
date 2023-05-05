package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface methods {

    public void deliverCities(ArrayList<Order> orders);

    public void deliverWareHouse(ArrayList<Order> orders);

    public void orderBooks(ArrayList<Order> orders);
    /**
     * deliver books from one warehouse to all other cities.
     * return the miles that the truck drives.
     *
     */
    public String deliverBooksFromOneWareHouse(ArrayList<Order> orders);


    /**
     *
     */
    public List<String> deliverBooksFromMultiWareHouse(ArrayList<Order> orders);
}
