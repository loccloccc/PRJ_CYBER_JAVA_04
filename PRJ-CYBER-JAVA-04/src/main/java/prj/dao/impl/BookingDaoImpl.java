package prj.dao.impl;

import prj.dao.BookingDAO;
import prj.model.Booking;
import prj.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookingDaoImpl implements BookingDAO {

    @Override
    public boolean createBooking(Booking b) {
        boolean result = false;
        String sql = "{call addBooking(?,?,?,?,?)}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, b.getUser_id());
            stmt.setInt(2, b.getPc_id());
            stmt.setTimestamp(3, Timestamp.valueOf(b.getStart_time()));
            // Nếu chưa có end_time, truyền NULL
            if (b.getEnd_time() != null) {
                stmt.setTimestamp(4, Timestamp.valueOf(b.getEnd_time()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }
            stmt.setDouble(5, b.getTotal_cost());

            int i = stmt.executeUpdate();
            result = i > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List<Booking> getBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "{call getAllBooking()}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking b = new Booking();
                b.setBooking_id(rs.getInt("booking_id"));
                b.setUser_id(rs.getString("user_id"));
                b.setPc_id(rs.getInt("pc_id"));

                Timestamp start = rs.getTimestamp("start_time");
                Timestamp end = rs.getTimestamp("end_time");
                if (start != null) b.setStart_time(start.toLocalDateTime());
                if (end != null) b.setEnd_time(end.toLocalDateTime());

                b.setTotal_cost(rs.getDouble("total_cost"));
                list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public boolean update(int id, java.sql.Timestamp endTime, double totalCost) {
        String sql = "{call updatebookingendtimeandcost(?,?,?)}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.setTimestamp(2, endTime);
            cs.setDouble(3, totalCost);

            return cs.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}