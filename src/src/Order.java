package src;

import java.util.ArrayList;

public class Order {
    private String id;
    private ArrayList<Book> bookList; // the number of books.
    private String[] address;
    private int zipcode;

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

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }


    public City getCityByZipCode() {
        return new City();
    }


}
