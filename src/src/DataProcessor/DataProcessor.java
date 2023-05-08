package src.DataProcessor;

import src.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataProcessor {

    private HashMap<String, City> myCityMap;
    private ArrayList<Warehouse> myWarehouseList;
    private ArrayList<Book> booksList;

    private static final double EARTH_R = 6371;
    public void parseCities() {
        try {
            File f = new File("src/src/DataProcessor/uscities.csv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<City> cities = new ArrayList<>();
            String str = br.readLine();
            while ((str = br.readLine()) != null) {
                str.replace("\"", "");
                String[] strArray = str.strip().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                for(int i = 0; i < strArray.length; i++){
                    if(strArray[i].startsWith("\"")) strArray[i] = strArray[i].substring(1);
                    if(strArray[i].endsWith("\"")) strArray[i] = strArray[i].substring(0, strArray[i].length() - 1);
                    strArray[i] = strArray[i].strip().replaceAll("\"\"", "\"");
                }
                if (strArray.length != 17) continue;
                if (Integer.parseInt(strArray[8].strip()) <= 500000) break;
                City c = new City();
                c.setName(strArray[1].strip().toLowerCase());
                c.setState(strArray[2].strip().toUpperCase());
                c.setLatitude(Double.parseDouble(strArray[6].strip()));
                c.setLongitude(Double.parseDouble(strArray[7].strip()));
                c.setZipcodes(strArray[15].strip().split("\\s+"));
                if (c.getName().equals("honolulu")) continue;
                cities.add(c);

            }

            this.myCityMap = new HashMap<>();

            for (City city : cities) {
                HashMap<String, Tuple<City, Double>> myMap = new HashMap<>();
                for (City city1 : cities) {
                    if (city.equals(city1)) continue;
                    myMap.put(city1.getName(), new Tuple<>(city1, countDistance(city, city1)));
                }
                city.setConnectingCities(myMap);
                this.myCityMap.put(city.getName(), city);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private double countDistance(City c1, City c2) {
        double lat1 = Math.toRadians(c1.getLatitude());
        double lat2 = Math.toRadians(c2.getLatitude());
        double lng1 = Math.toRadians(c1.getLongitude());
        double lng2 = Math.toRadians(c2.getLongitude());

        double latDistance = lat2 - lat1;
        double lngDistance = lng2 - lng1;

        double a = Math.pow(Math.sin(latDistance / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(lngDistance / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_R * c;

        return (distance > 0) ? distance : -distance;
    }

    public void parseBooks() {
        try {
            File f = new File("src/src/DataProcessor/books_dataset.csv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            this.booksList = new ArrayList<>();
            Random r = new Random();
            String str = br.readLine();
            while ((str = br.readLine()) != null) {
                String[] strArray = str.strip().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                for(int i = 0; i < strArray.length; i++){
                    if(strArray[i].startsWith("\"")) strArray[i] = strArray[i].substring(1);
                    if(strArray[i].endsWith("\"")) strArray[i] = strArray[i].substring(0, strArray[i].length() - 1);
                    strArray[i] = strArray[i].strip().replaceAll("\"\"", "\"");
                }
                if (strArray.length != 11) continue;
                Book b = new Book();
                b.setUrl(strArray[0]);
                b.setName(strArray[1]);
                b.setAuthor(strArray[2]);
//                b.setPrice(Double.parseDouble(strArray[5]));
                b.setCategory(strArray[9]);
                b.setIsbn(strArray[8]);
                this.randomizeBookStorage(b, r);
                this.booksList.add(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void randomizeBookStorage(Book b, Random r) {
        int locationNum = r.nextInt(3) + 1;
        for (int i = 0; i < locationNum; i++) {
            int num = r.nextInt(100) + 1;
            this.myWarehouseList.get(r.nextInt(this.myWarehouseList.size())).storeBook(b, num);
        }
    }

    public ArrayList<Order> generateOrderFromOneWarehouse(Warehouse w){
        ArrayList<Order> orders = new ArrayList<>();
        Random r = new Random();

        int num = r.nextInt(30) + 10;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateString = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("0000");

        HashMap<String, Book> warehouseBooks = new HashMap<>();
        ArrayList<String> isbnList = new ArrayList<>();

        for(Book book : this.booksList) {
            if (w.getStorageMap().keySet().contains(book.getIsbn())) {
                warehouseBooks.put(book.getIsbn(), book);
                isbnList.add(book.getIsbn());
            }
        }

        for (int i = 0; i < num; i++) {
            Order o = new Order();
            Book b = warehouseBooks.get(isbnList.get(r.nextInt(isbnList.size())));
            ArrayList<Book> bl = o.getBookList();
            bl.add(b);
            o.setBookList(bl);
            City c = this.getRandomCity();
            String[] zipList = c.getZipcodes();
            o.setZipcode(zipList[r.nextInt(zipList.length)]);
            String orderNum = df.format(i);
            o.setId(c.getName() + dateString + orderNum);
            if (w.sellBook(b, 1))  orders.add(o);
        }

        return orders;
    }

    public Warehouse getRandomWarehouse() {
        Random r = new Random();
        return this.myWarehouseList.get(r.nextInt(this.myWarehouseList.size()));
    }

    public City getRandomCity() {
        Random r = new Random();
        return (City) this.myCityMap.values().toArray()[r.nextInt(this.myCityMap.size())];
    }

    public  ArrayList<Order> generateOrderWithMultipleBooks(){
        ArrayList<Order> orders = new ArrayList<>();
        Random r = new Random();
        int num = r.nextInt(500) + 100;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateString = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("0000");

        for (int i = 0; i < num; i++) {
            Order o = new Order();
            ArrayList<Book> bl = o.getBookList();
            int bookNum = r.nextInt(5) + 2;
            for (int j = 0; j < bookNum; j++) {
                Book b = this.booksList.get(r.nextInt(this.booksList.size()));
                bl.add(b);
            }
            o.setBookList(bl);
            City c = this.getRandomCity();
            String[] zipList = c.getZipcodes();
            o.setZipcode(zipList[r.nextInt(zipList.length)]);
            String orderNum = df.format(i);
            o.setId(c.getName() + dateString + orderNum);


            //仓库出书还不知道 assume 全部够
            orders.add(o);
        }
        return orders;
    }

    public void initialization() {
        this.parseCities();
        this.myWarehouseList = new ArrayList<>();
        for (City c : this.myCityMap.values()) {
            Warehouse w = new Warehouse(c.getName());
            this.myWarehouseList.add(w);
        }
        this.parseBooks();
    }

    public HashMap<String, City> getMyCityMap() {
        return myCityMap;
    }

    public ArrayList<Warehouse> getMyWareHouseList() {
        return myWarehouseList;
    }

    public ArrayList<Book> getBooksList() {
        return booksList;
    }
}
