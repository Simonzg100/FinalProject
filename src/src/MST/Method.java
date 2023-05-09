package src.MST;

import src.*;
import src.DataProcessor.DataProcessor;

import java.util.*;

public class Method implements IMethods {

    private HashSet<City> cities;
    private HashSet<Warehouse> warehouses;
    private HashSet<String> orderedBooks;
    private DataProcessor dp;

    public Method() {
        this.dp = new DataProcessor();
        this.dp.initialization();

        this.cities = new HashSet<>();
        this.warehouses = new HashSet<>();
        this.orderedBooks = new HashSet<>();
    }

    @Override
    public HashSet<City> deliverCities(ArrayList<Order> orders) {
        HashMap<String, City> cityMap = this.dp.getMyCityMap();
        HashMap<String, City> zipMap = new HashMap<>();
        for (City c : cityMap.values()) {
            for (String str : c.getZipcodes()) {
                zipMap.put(str,c);
            }
        }

        for (Order order : orders) {
            City city = zipMap.get(order.getZipcode());
            cities.add(city);
        }
        return cities;
    }

    @Override
    public HashSet<Warehouse> delWareHouse(ArrayList<Order> orders) {
        ArrayList<Warehouse> myWarehouseList = this.dp.getMyWareHouseList();
        for (Warehouse wareHouse : myWarehouseList) {
            HashMap<String, Integer> storageMap = wareHouse.getStorageMap();
            Set<String> strings = storageMap.keySet();
            if (strings.containsAll(orderedBooks)) {
                warehouses.add(wareHouse);
            }
        }
        return warehouses;
    }
    @Override
    public HashSet<String> orderBooks(ArrayList<Order> orders) {
        for (Order order : orders) {
            ArrayList<Book> books = order.getBookList();
            for (Book book : books) {
                this.orderedBooks.add(book.getIsbn());
            }
        }
        return orderedBooks;
    }

    public Warehouse findBestWarehouseForMST(HashSet<Warehouse> warehouseList, ArrayList<Order> orders) {
        Warehouse bestWarehouse = null;
        double minTotalDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouseList) {
            deliverCities(orders);
            City startingCity = this.dp.getMyCityMap().get(warehouse.getCity());
            ArrayList<City> path = new ArrayList<>();
            path.add(startingCity);
            this.cities.add(startingCity);
            ArrayList<Tuple<City, City>> mstEdges = findMST(path, new ArrayList<>());
            double totalDistance = 0;
            for (Tuple<City, City> edge : mstEdges) {
                totalDistance += edge.getLeft().getConnectingCities().get(edge.getRight().getName()).getRight();
            }
            if (totalDistance < minTotalDistance) {
                minTotalDistance = totalDistance;
                bestWarehouse = warehouse;
            }
        }
        return bestWarehouse;
    }

    private ArrayList<Tuple<City, City>> findMST(ArrayList<City> list, ArrayList<Tuple<City, City>> tuples) {
        if (list.size() == this.cities.size()) return tuples;
        City minCity = null;
        City startCity = null;
        double minDis = 0;
        for (City c1 : list) {
            for (City c2 : this.cities) {
                if (list.contains(c2)) continue;
                if (minCity == null) {
                    startCity = c1;
                    minCity = c2;
                    minDis = c1.getConnectingCities().get(c2.getName()).getRight();
                    continue;
                }
                if (c2.countDistance(c1) < minDis) {
                    startCity = c1;
                    minCity = c2;
                    minDis = c1.getConnectingCities().get(c2.getName()).getRight();
                }
            }
        }
        list.add(minCity);
        tuples.add(new Tuple<>(startCity, minCity));
        return findMST(list, tuples);
    }


    public List<City> getRandomWarehousesCities(List<Warehouse> availableWarehouses, int n) {
        List<City> selectedCities = new ArrayList<>();
        Collections.shuffle(availableWarehouses);
        for (int i = 0; i < n; i++) {
            Warehouse warehouse = availableWarehouses.get(i);
            City city = this.dp.getMyCityMap().get(warehouse.getCity());
            selectedCities.add(city);
        }
        return selectedCities;
    }

    @Override
    public int findMSTForMultiWareHouse(ArrayList<Order> orders, int n) {
        // Get the list of available warehouses
        ArrayList<Warehouse> warehouseList = this.dp.getMyWareHouseList();
        HashSet<String> orderedBooks = orderBooks(orders);

        // Select n random warehouses
        List<City> selectedCities = getRandomWarehousesCities(warehouseList, n);

        // Add all cities in the orders to the set of cities
        HashSet<City> allCities = deliverCities(orders);

        // Add all cities in the selected warehouses to the set of cities
        for (City city : selectedCities) {
            allCities.add(city);
        }

        // Create a path that starts from the first city in the list of selected cities
        ArrayList<City> path = new ArrayList<>();
        City startingCity = selectedCities.get(0);
        path.add(startingCity);

        // Add all the remaining cities to the path
        for (City city : allCities) {
            if (!path.contains(city)) {
                path.add(city);
            }
        }

        // Make sure the path ends with the starting city
        if (!path.contains(startingCity)) {
            path.add(startingCity);
        }

        // Generate the MST for the path
        ArrayList<Tuple<City, City>> mstEdges = findMST(path, new ArrayList<>());

        // Calculate the total distance of the MST
        int totalDistance = 0;
        for (Tuple<City, City> edge : mstEdges) {
            totalDistance += edge.getLeft().getConnectingCities().get(edge.getRight().getName()).getRight();
        }

        return totalDistance;
    }


    public ArrayList<Order> getRandomOrder() {
        Warehouse w = this.dp.getRandomWarehouse();
        Random r = new Random();
        return this.dp.generateOrderFromOneWarehouse(w);
    }

}
