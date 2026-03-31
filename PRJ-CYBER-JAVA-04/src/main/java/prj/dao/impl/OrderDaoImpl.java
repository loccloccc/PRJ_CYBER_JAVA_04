package prj.dao.impl;

import prj.dao.OrderDAO;
import prj.model.Order;
import prj.util.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDAO {

    @Override
    public boolean addOrder(Order o) {

        String sql = "{call addorder(?,?,?,?,?)}";

        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql)){

            cs.setString(1,o.getUser_id());
            cs.setInt(2,o.getFood_id());
            cs.setInt(3,o.getQuantity());
            cs.setDouble(4,o.getTotal_price());
            cs.setString(5,o.getStatus());

            return cs.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Order> getAllOrders() {

        List<Order> list = new ArrayList<>();
        String sql = "{call getallorders()}";

        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery()){

            while(rs.next()){

                Order o = new Order();

                o.setOrder_id(rs.getInt("order_id"));
                o.setUser_id(rs.getString("user_id"));
                o.setFood_id(rs.getInt("food_id"));
                o.setQuantity(rs.getInt("quantity"));
                o.setTotal_price(rs.getDouble("total_price"));
                o.setStatus(rs.getString("status"));

                Timestamp t = rs.getTimestamp("order_time");
                if(t != null){
                    o.setOrder_time(t.toLocalDateTime());
                }

                list.add(o);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean updateOrderStatus(String userId, String status) {

        String sql = "{call updateorderstatus(?,?)}";

        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql)){

            cs.setString(1,userId);
            cs.setString(2,status);

            return cs.executeUpdate() > 0;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}