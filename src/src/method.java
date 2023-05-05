package src;

import java.util.*;

public class method implements methods{

    private HashSet<City> cities;

    @Override
    public void deliverCities(ArrayList<Order> orders) {
        this.cities = new HashSet<>();
        for (Order order : orders) {
            City city = order.getCityByZipCode();
            cities.add(city);
        }
    }


    @Override
    public String deliverBooksFromOneWareHouse(ArrayList<Order> orders) {
        deliverCities(orders);
        List<String[]> edges = new ArrayList<>();
        for (City city : cities) {
            HashMap<String, Tuple<City, Double>> connectingCities = city.getConnectingCities();
            for (Map.Entry<String, Tuple<City, Double>> map : connectingCities.entrySet()) {
                String name = map.getKey();
                Tuple<City, Double> value = map.getValue();
                Double right = value.getRight(); // distance
                // create the graph.
                edges.add(new String[] {city.getName(),name,Double.toString(right) });
            }
        }

        edges.sort((a, b) ->
          (int) (Double.parseDouble(a[2]) - Double.parseDouble(b[2]))
        );



        return new String();
    }



    @Override
    public int deliverBooksFromMultiWareHouse() {
        return 0;
    }


}
