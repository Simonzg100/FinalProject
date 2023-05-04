import java.util.ArrayList;

public class Book {
    private String name;
    private String author;
    private String isbn;
    private String url;
    private String category;
    private double price;
    private ArrayList<String> wareHouseList;
    private int stockQuantity;

    public Book() {
        this.wareHouseList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getWareHouseList() {
        return wareHouseList;
    }

    public void setWareHouseList(ArrayList<String> wareHouseList) {
        this.wareHouseList = wareHouseList;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

