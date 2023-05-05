package src;

import src.DataProcessor.DataProcessor;

import java.util.*;
import java.util.stream.Collectors;

public class method implements methods{

    private HashSet<City> cities;
    private HashSet <WareHouse> wareHouses;

    private  HashSet<String> orderBooks;

    @Override
    public void deliverCities(ArrayList<Order> orders) {
        this.cities = new HashSet<>();
        for (Order order : orders) {
            City city = order.getCityByZipCode();
            cities.add(city);
        }
    }

    @Override
    public void deliverFromOneWareHouse(ArrayList<Order> orders) {
        wareHouses = new HashSet<>();
        DataProcessor dp =  new DataProcessor();


        ArrayList<WareHouse> myWareHouseList = dp.getMyWareHouseList();
        for (WareHouse wareHouse : myWareHouseList) {
            HashMap<String, Integer> storageMap = wareHouse.getStorageMap();
            Set<String> strings = storageMap.keySet();
            if (strings.containsAll(orderBooks)) {
                wareHouses.add(wareHouse);
            }
        }
    }

    @Override
    public void orderBooks(ArrayList<Order> orders) {
        this.orderBooks = new HashSet<>();
        HashSet<String> orderBooks =  new HashSet<>();
        for (Order order : orders) {
            ArrayList<Book> books = order.getBookList();
            for (Book book : books) {
                orderBooks.add(book.getIsbn());
            }
        }
    }


    @Override
    public String deliverBooksFromOneWareHouse(ArrayList<Order> orders) {
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
                edges.add(new String[] {city.getName(),name,Double.toString(right) });
            }
        }

        String bestWarehouse = null;
        double minTotalWeight = Double.MAX_VALUE;

        for (WareHouse warehouse : wareHouses) {
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
        List<List<WareHouse>> warehouseCombinations = deliverFromMultiWareHouse(orders);
        List<String> bestWarehouses = new ArrayList<>();
        double bestTotalDistance = Double.MAX_VALUE;

        for (List<WareHouse> combination : warehouseCombinations) {
            List<String[]> edges = createEdges(combination);
            double totalDistance = calculateMST(combination.size(), edges);

            if (totalDistance < bestTotalDistance) {
                bestTotalDistance = totalDistance;
                bestWarehouses = combination.stream().map(WareHouse::getCity).collect(Collectors.toList());
            }
        }

        return bestWarehouses;
    }

    private List<String[]> createEdges(List<WareHouse> combination) {
        List<String[]> edges = new ArrayList<>();

        for (WareHouse warehouse : combination) {
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
    public List<List<WareHouse>> deliverFromMultiWareHouse(ArrayList<Order> orders) {
        orderBooks(orders);
        DataProcessor dp = new DataProcessor();
        ArrayList<WareHouse> myWareHouseList = dp.getMyWareHouseList();
        List<List<WareHouse>> warehouseCombinations = new ArrayList<>();
        List<WareHouse> currentCombination = new ArrayList<>();
        findCombinations(warehouseCombinations, currentCombination, myWareHouseList, orderBooks, 0);
        return warehouseCombinations;
    }

    private void findCombinations(List<List<WareHouse>> warehouseCombinations, List<WareHouse> currentCombination,
                                  ArrayList<WareHouse> myWareHouseList, HashSet<String> orderBooks, int index) {
        if (isSufficient(currentCombination, orderBooks)) {
            warehouseCombinations.add(new ArrayList<>(currentCombination));
            return;
        }

        if (index >= myWareHouseList.size()) {
            return;
        }

        // With the current warehouse
        WareHouse warehouse = myWareHouseList.get(index);
        currentCombination.add(warehouse);
        findCombinations(warehouseCombinations, currentCombination, myWareHouseList, orderBooks, index + 1);

        // Without the current warehouse
        currentCombination.remove(currentCombination.size() - 1);
        findCombinations(warehouseCombinations, currentCombination, myWareHouseList, orderBooks, index + 1);
    }

    private boolean isSufficient(List<WareHouse> currentCombination, HashSet<String> orderBooks) {
        HashSet<String> coveredBooks = new HashSet<>();
        for (WareHouse warehouse : currentCombination) {
            coveredBooks.addAll(warehouse.getStorageMap().keySet());
        }
        return coveredBooks.containsAll(orderBooks);
    }



}
