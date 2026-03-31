package prj.model;

public class Food {

    private int id;
    private String name;
    private double price;
    private int stock;
    private String type;

    public Food() {
    }

    public Food(String name , int stock) {
        this.name = name;
        this.stock = stock;
    }

    public Food(String name, double price, int stock, String type) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
