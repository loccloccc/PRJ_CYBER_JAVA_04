package prj.dao;

import prj.model.Booking;

import java.sql.Timestamp;
import java.util.List;

public interface BookingDAO {
    boolean createBooking(Booking booking);
    List<Booking> getBookings();
    public boolean update(int bookingId, Timestamp endTime, double totalCost);
}
