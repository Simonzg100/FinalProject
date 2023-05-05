package src;

import java.util.ArrayList;
import java.util.HashSet;

public interface methods {

    public void deliverCities(ArrayList<Order> orders);

    /**
     * deliver books from one warehouse to all other cities.
     * return the miles that the truck drives.
     *
     */
    public String deliverBooksFromOneWareHouse(ArrayList<Order> orders);


    /**
     *
     */
    public int deliverBooksFromMultiWareHouse();
}
