package src;

import java.util.*;

public class Order {
    private String id;
    private ArrayList<Book> bookList; // the number of books.
    private String[] address;
    private String zipcode;

    public Order() {
        this.bookList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

//    public boolean orderBooks(HashMap<String, City> myCityMap) {
//        ArrayList<ArrayList<City>> cities = new ArrayList<>();
//
//        City endCity;
//        boolean found = false;
//        for (City c : myCityMap.values()) {
//            for (String zip : c.getZipcodes()) {
//                if (zip.equals(this.zipcode)) {
//                    endCity = c;
//                    break;
//                }
//            }
//            if (found) break;
//        }
//
//        for (Book book : this.bookList) {
//            ArrayList<City> bookStorage = new ArrayList<>();
//            for (String cityName : book.getWareHouseList()) {
//                bookStorage.add(myCityMap.get(cityName));
//            }
//            cities.add(bookStorage);
//        }
//
//        for (int i = 0; i < cities.size(); i++) {
//
//        }
//
//    }

}
