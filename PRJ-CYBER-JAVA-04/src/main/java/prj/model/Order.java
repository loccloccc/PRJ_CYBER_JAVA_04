package prj.model;

import java.time.LocalDateTime;

public class Order {
    private int order_id;
    private String user_id;
    private int food_id;
    private int quantity;
    private double total_price;
    private LocalDateTime order_time;
    private String status;

    public Order() {
    }

    public Order(int order_id, String user_id, int food_id, int quantity, double total_price, LocalDateTime order_time, String status) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.food_id = food_id;
        this.quantity = quantity;
        this.total_price = total_price;
        this.order_time = order_time;
        this.status = status;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public LocalDateTime getOrder_time() {
        return order_time;
    }

    public void setOrder_time(LocalDateTime order_time) {
        this.order_time = order_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
