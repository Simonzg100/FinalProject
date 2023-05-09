package src;

import src.DataProcessor.DataProcessor;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
    public void deliverCities(ArrayList<Order> orders) {
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
    }

    @Override
    public void deliverFromOneWareHouse(ArrayList<Order> orders) {
        ArrayList<Warehouse> myWarehouseList = this.dp.getMyWareHouseList();
        for (Warehouse wareHouse : myWarehouseList) {
            HashMap<String, Integer> storageMap = wareHouse.getStorageMap();
            Set<String> strings = storageMap.keySet();
            if (strings.containsAll(orderedBooks)) {
                warehouses.add(wareHouse);
            }
        }
    }

    @Override
    public void orderBooks(ArrayList<Order> orders) {
        for (Order order : orders) {
            ArrayList<Book> books = order.getBookList();
            for (Book book : books) {
                this.orderedBooks.add(book.getIsbn());
            }
        }
    }

    public ArrayList<Tuple<City, City>> deliverFromSingleWarehouse() {
        Warehouse w = this.dp.getRandomWarehouse();
        Random r = new Random();
        ArrayList<Order> orders = this.dp.generateOrderFromOneWarehouse(w);
        deliverCities(orders);

        City startingCity = this.dp.getMyCityMap().get(w.getCity());


        ArrayList<City> path = new ArrayList<>();


        path.add(startingCity);
        this.cities.add(startingCity);

        ArrayList<Tuple<City, City>> tuples = this.findMST(path, new ArrayList<>());
        return tuples;

//        for (City c : this.cities) {
//            path.add(c);
//        }
//        if (!path.contains(startingCity)) path.add(startingCity);
//
//
//
//
//
//        Collections.sort(path, new Comparator<City>() {
//            @Override
//            public int compare(City o1, City o2) {
//                int longitude1 = (int) (o1.getLongitude() * 1000);
//                int longitude2 = (int) (o2.getLongitude() * 1000);
//                int latitude1 = (int) (o1.getLatitude() * 1000);
//                int latitude2 = (int) (o2.getLatitude() * 1000);
//                if (longitude1 == longitude2) {
//                    return latitude1 - latitude2;
//                } else {
//                    return longitude1 - longitude2;
//                }
//            }
//        });
//
//        double avgLong = 0;
//        double avgLat = 0;
//        for (City city : path) {
//            avgLong += city.getLongitude();
//            avgLat += city.getLatitude();
//        }
//        avgLat = avgLat / path.size();
//        avgLong = avgLong / path.size();
//
    }

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
            City.AVG_LONGITUDE += c.getLongitude();
            City.AVG_LATITUDE += c.getLatitude();
            path.add(c);
        }
        City.AVG_LONGITUDE = City.AVG_LONGITUDE / cities.size();
        City.AVG_LATITUDE = City.AVG_LATITUDE / cities.size();
        if (!path.contains(startingCity)) path.add(startingCity);


        Collections.sort(path, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                double angle1 = Math.atan2(o1.getLatitude() - City.AVG_LATITUDE, o1.getLongitude() - City.AVG_LONGITUDE);
                double angle2 = Math.atan2(o2.getLatitude() - City.AVG_LATITUDE, o2.getLongitude() - City.AVG_LONGITUDE);
                return Double.compare(angle2, angle1);
            }
        });

        return path;

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



    @Override
    public String deliverBooksFromOneWareHouse() {
        Warehouse w = this.dp.getRandomWarehouse();
        Random r = new Random();
        ArrayList<Order> orders = this.dp.generateOrderFromOneWarehouse(w);
        deliverCities(orders);
        deliverFromOneWareHouse(orders);

        List<String[]> edges = new ArrayList<>();

        for (City city : cities) {
            HashMap<String, Tuple<City, Double>> connectingCities = city.getConnectingCities();
            for (Map.Entry<String, Tuple<City, Double>> map : connectingCities.entrySet()) {
                String name = map.getKey();
                Tuple<City, Double> value = map.getValue();
                Double right = value.getRight(); // distance
                // create the graph.
                edges.add(new String[] {city.getName(),name,Double.toString(right)});
            }
        }

        String bestWarehouse = null;
        double minTotalWeight = Double.MAX_VALUE;

        for (Warehouse warehouse : warehouses) {
            List<String[]> warehouseEdges = new ArrayList<>(edges); // Clone the edges list
            String warehouseCity = warehouse.getCity();
            for (City city : cities) {
                HashMap<String, Tuple<City, Double>> connectingCities = city.getConnectingCities();
                Tuple<City, Double> cityDoubleTuple = connectingCities.get(warehouseCity);
                Double distance = cityDoubleTuple.getRight();
                warehouseEdges.add(new String[]{warehouseCity, city.getName(), Double.toString(distance)});
            }
            warehouseEdges.sort((a, b) ->
                    (int) (Double.parseDouble(a[2]) - Double.parseDouble(b[2]))
            );
            int citySizes = cities.size();
            UnionFind uf = new UnionFind(citySizes + 1);
            double totalWeight = 0;
            int edgeCount = 0;

            for (String[] edge : warehouseEdges) {
                if (uf.union(edge[0], edge[1])) {
                    totalWeight += Double.parseDouble(edge[2]);
                    edgeCount++;

                    if (edgeCount == citySizes) {
                        break; // All cities are connected
                    }
                }
            }
            if (totalWeight < minTotalWeight) {
                minTotalWeight = totalWeight;
                bestWarehouse = warehouseCity;
            }
        }

        return bestWarehouse;
    }


    @Override
    public List<String> deliverBooksFromMultiWareHouse(ArrayList<Order> orders) {
        List<List<Warehouse>> warehouseCombinations = deliverFromMultiWareHouse();
        List<String> bestWarehouses = new ArrayList<>();
        double bestTotalDistance = Double.MAX_VALUE;

        for (List<Warehouse> combination : warehouseCombinations) {
            List<String[]> edges = createEdges(combination);
            double totalDistance = calculateMST(combination.size(), edges);

            if (totalDistance < bestTotalDistance) {
                bestTotalDistance = totalDistance;
                bestWarehouses = combination.stream().map(Warehouse::getCity).collect(Collectors.toList());
            }
        }

        return bestWarehouses;
    }

    private List<String[]> createEdges(List<Warehouse> combination) {
        List<String[]> edges = new ArrayList<>();

        for (Warehouse warehouse : combination) {
            String warehouseCity = warehouse.getCity();
            for (City city : cities) {
                HashMap<String, Tuple<City, Double>> connectingCities = city.getConnectingCities();
                Tuple<City, Double> cityDoubleTuple = connectingCities.get(warehouseCity);
                Double distance = cityDoubleTuple.getRight();
                edges.add(new String[]{warehouseCity, city.getName(), Double.toString(distance)});
            }
        }

        return edges;
    }

    private double calculateMST(int warehouseCount, List<String[]> edges) {
        edges.sort((a, b) -> (int) (Double.parseDouble(a[2]) - Double.parseDouble(b[2])));
        UnionFind uf = new UnionFind(warehouseCount + cities.size());

        // Add cities and warehouses to the UnionFind
        for (City city : cities) {
            uf.add(city.getName());
        }
        for (String[] edge : edges) {
            uf.add(edge[0]);
        }
        double totalWeight = 0;
        int edgeCount = 0;

        for (String[] edge : edges) {
            if (uf.union(edge[0], edge[1])) {
                totalWeight += Double.parseDouble(edge[2]);
                edgeCount++;

                if (edgeCount == cities.size()) {
                    break;
                }
            }
        }
        return totalWeight;
    }


    @Override
    public List<List<Warehouse>> deliverFromMultiWareHouse() {
        ArrayList<Order> orders = this.dp.generateOrderWithMultipleBooks();
        orderBooks(orders);
        ArrayList<Warehouse> myWarehouseList = dp.getMyWareHouseList();
        List<List<Warehouse>> warehouseCombinations = new ArrayList<>();
        List<Warehouse> currentCombination = new ArrayList<>();
        findCombinations(warehouseCombinations, currentCombination, myWarehouseList, orderedBooks, 0);
        return warehouseCombinations;
    }

    private void findCombinations(List<List<Warehouse>> warehouseCombinations, List<Warehouse> currentCombination,
                                  ArrayList<Warehouse> myWarehouseList, HashSet<String> orderBooks, int index) {
        if (isSufficient(currentCombination, orderBooks)) {
            warehouseCombinations.add(new ArrayList<>(currentCombination));
            return;
        }

        if (index >= myWarehouseList.size()) {
            return;
        }

        // With the current warehouse
        Warehouse warehouse = myWarehouseList.get(index);
        currentCombination.add(warehouse);
        findCombinations(warehouseCombinations, currentCombination, myWarehouseList, orderBooks, index + 1);

        // Without the current warehouse
        currentCombination.remove(currentCombination.size() - 1);
        findCombinations(warehouseCombinations, currentCombination, myWarehouseList, orderBooks, index + 1);
    }

    private boolean isSufficient(List<Warehouse> currentCombination, HashSet<String> orderBooks) {
        HashSet<String> coveredBooks = new HashSet<>();
        for (Warehouse warehouse : currentCombination) {
            coveredBooks.addAll(warehouse.getStorageMap().keySet());
        }
        return coveredBooks.containsAll(orderBooks);
    }

    public HashSet<City> getCities() {
        return cities;
    }
}
