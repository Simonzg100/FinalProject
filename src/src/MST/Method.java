package src.MST;

import src.*;
import src.DataProcessor.DataProcessor;

import java.util.*;

public class Method implements IMethods {

    private HashSet<City> cities;
    private HashSet<Warehouse> warehouses;
    private HashSet<String> orderedBooks;
    private DataProcessor dp;

    public DataProcessor getDp() {
        return dp;
    }
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
    ArrayList<Tuple<City, City>> mstEdges;
    public Warehouse findBestWarehouseForMST(HashSet<Warehouse> warehouseList, ArrayList<Order> orders) {
        Warehouse bestWarehouse = null;
        double minTotalDistance = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouseList) {
            deliverCities(orders);
            ArrayList<City> c = new ArrayList<>(this.cities);

            City startingCity = this.dp.getMyCityMap().get(warehouse.getCity());
            if (startingCity == null) {
                System.out.println("Bug !!");
            }
            ArrayList<City> path = new ArrayList<>();
            path.add(startingCity);
            c.add(startingCity);

            this.mstEdges = findMST(path, new ArrayList<>(), c);

            System.out.println(mstEdges.size());
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

    public ArrayList<Tuple<City, City>> getMSTEdges (){
        return this.mstEdges;
    }

    private ArrayList<Tuple<City, City>> findMST(ArrayList<City> list, ArrayList<Tuple<City, City>> tuples,ArrayList<City> cities ) {
        if (list.size() == cities.size()) return tuples;
        City minCity = null;
        City startCity = null;
        double minDis = 0;
        for (City c1 : list) {
            for (City c2 : cities) {
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
        if (startCity != null) {
            tuples.add(new Tuple<>(startCity, minCity));
        }


        return findMST(list, tuples,cities);
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
    public int findMSTForMultiWareHouse(List<City> cities, ArrayList<Order> orders) {
        deliverCities(orders);
        List<City> allCities = new ArrayList<>(cities);
        allCities.addAll(cities);
        City startingCity = cities.get(0);
        ArrayList<City> path = new ArrayList<>();
        path.add(startingCity);

        for (City c : allCities) {
            if (!path.contains(c)) {
                path.add(c);
            }
        }
        if (!path.contains(startingCity)) path.add(startingCity);
        ArrayList<Tuple<City, City>> mstEdges = findMST(path, new ArrayList<>());
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

    @Override
    public ArrayList<City> deliverFromSingleWarehouseRoute() {
        Warehouse w = this.dp.getRandomWarehouse();
        Random r = new Random();
        ArrayList<Order> orders = this.dp.generateOrderFromOneWarehouse(w);
        deliverCities(orders);
        City startingCity = this.dp.getMyCityMap().get(w.getCity());
        ArrayList<City> path = new ArrayList<>();
        path.add(startingCity);
        this.cities.add(startingCity);
        for (City c : this.cities) {
            path.add(c);
        }
        if (!path.contains(startingCity)) path.add(startingCity);
        Collections.sort(path, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                int longitude1 = (int) (o1.getLongitude() * 1000);
                int longitude2 = (int) (o2.getLongitude() * 1000);
                int latitude1 = (int) (o1.getLatitude() * 1000);
                int latitude2 = (int) (o2.getLatitude() * 1000);
                if (longitude1 == longitude2) {
                    return latitude1 - latitude2;
                } else {
                    return longitude1 - longitude2;
                }
            }
        });
        return path;
    }


}
