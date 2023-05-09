package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
    private String city;
    private HashMap<String, Integer> storageMap;

    public Warehouse(String city) {
        this.city = city;
        this.storageMap = new HashMap<>();
    }

    public Warehouse() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HashMap<String, Integer> getStorageMap() {
        return storageMap;
    }

    public void setStorageMap(HashMap<String, Integer> storageMap) {
        this.storageMap = storageMap;
    }

    public void storeBook(Book b, int num) {
        String isbn = b.getIsbn();
        b.setStockQuantity(b.getStockQuantity() + num);

        if (this.storageMap.containsKey(isbn)) {
            num += this.storageMap.get(isbn);
            this.storageMap.remove(isbn);
            this.storageMap.put(isbn, num);
        } else {
            this.storageMap.put(isbn, num);
            ArrayList<String> list = b.getWareHouseList();
            list.add(this.city);
            b.setWareHouseList(list);
        }
    }

    public boolean sellBook(Book b, int num) {
        String isbn = b.getIsbn();
        if (this.getStorageMap().get(isbn) < num) return false;
        b.setStockQuantity(b.getStockQuantity() - num);

        if (this.storageMap.containsKey(isbn)) {
            num = this.storageMap.get(isbn) - num;
            this.storageMap.remove(isbn);
            if (num > 0) {
                this.storageMap.put(isbn, num);
            } else {
                ArrayList<String> list = b.getWareHouseList();
                list.remove(this.city);
                b.setWareHouseList(list);
            }
        }

        return true;
    }



}
