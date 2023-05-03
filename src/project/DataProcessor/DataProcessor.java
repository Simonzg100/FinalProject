package project.DataProcessor;

import project.Book;
import project.City;
import project.WareHouse;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DataProcessor {

    private HashMap<String, City> myCityMap;
    private ArrayList<WareHouse> myWareHouseList;
    private ArrayList<Book> booksList;

    private static final double EARTH_R = 6371;
    public void parseCities() {
        try {
            File f = new File("project/DataProcessor/uscities.csv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<City> cities = new ArrayList<>();
            String str = br.readLine();
            while ((str = br.readLine()) != null) {
                str.replace("\"", "");
                System.out.println(str);
                String[] strArray = str.strip().split(",");
                if (strArray.length != 17) continue;
                if (Integer.parseInt(strArray[8].strip()) <= 500000) break;
                City c = new City();
                c.setName(strArray[1].strip().toLowerCase());
                c.setState(strArray[2].strip().toUpperCase());
                c.setLatitude(Double.parseDouble(strArray[6].strip()));
                c.setLongitude(Double.parseDouble(strArray[7].strip()));
                c.setZipcodes(strArray[15].strip().split("\\s+"));
                cities.add(c);
            }

            this.myCityMap = new HashMap<>();

            for (City city : cities) {
                HashMap<City, Double> myMap = new HashMap<>();
                for (City city1 : cities) {
                    if (city.equals(city1)) continue;
                    myMap.put(city1, countDistance(city, city1));
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

        return (EARTH_R * c > 0) ? a : -a;
    }

    public void parseBooks() {
        try {
            File f = new File("project/DataProcessor/books_dataset.csv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            this.booksList = new ArrayList<>();
            Random r = new Random();
            String str = br.readLine();
            while ((str = br.readLine()) != null) {
                System.out.println(str);
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
                b.setPrice(Double.parseDouble(strArray[5]));
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
            this.myWareHouseList.get(r.nextInt(this.myWareHouseList.size())).storeBook(b, num);
        }
    }

    public void initialization() {
        this.parseCities();
        this.myWareHouseList = new ArrayList<>();
        for (City c : this.myCityMap.values()) {
            WareHouse w = new WareHouse(c.getName());
            this.myWareHouseList.add(w);
        }
        this.parseBooks();
    }
}
