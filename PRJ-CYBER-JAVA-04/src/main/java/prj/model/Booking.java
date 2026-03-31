package prj.model;

import java.time.LocalDateTime;

public class Booking {
    private int booking_id;
    private String user_id;
    private int pc_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private double total_cost;

    public Booking() {}

    public Booking(int booking_id, String user_id, int pc_id, LocalDateTime start_time, LocalDateTime end_time, double total_cost) {
        this.booking_id = booking_id;
        this.user_id = user_id;
        this.pc_id = pc_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.total_cost = total_cost;
    }

    public int getBooking_id() { return booking_id; }
    public void setBooking_id(int booking_id) { this.booking_id = booking_id; }

    public String getUser_id() { return user_id; }
    public void setUser_id(String user_id) { this.user_id = user_id; }

    public int getPc_id() { return pc_id; }
    public void setPc_id(int pc_id) { this.pc_id = pc_id; }

    public LocalDateTime getStart_time() { return start_time; }
    public void setStart_time(LocalDateTime start_time) { this.start_time = start_time; }

    public LocalDateTime getEnd_time() { return end_time; }
    public void setEnd_time(LocalDateTime end_time) { this.end_time = end_time; }

    public double getTotal_cost() { return total_cost; }
    public void setTotal_cost(double total_cost) { this.total_cost = total_cost; }
}