package prj.dao.impl;

import prj.dao.FoodDAO;
import prj.model.Food;
import prj.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDaoImpl implements FoodDAO {
    // them
    @Override
    public boolean addFood(Food f){
        boolean result = false;
        String sql = "{call insertfood(?,?,?,?)}";
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn =  DBConnection.getConnection();
            pstmt = conn.prepareCall(sql);

            pstmt.setString(1,f.getName());
            pstmt.setDouble(2,f.getPrice());
            pstmt.setInt(3,f.getStock());
            pstmt.setString(4,f.getType());
            int i =  pstmt.executeUpdate();
            if(i>0){
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    // lay
    @Override
    public List<Food> getAllFood(){
        List<Food> list = new ArrayList<>();

        String sql = "{call getallfood}";
        Connection conn = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        conn = DBConnection.getConnection();
        try {
            cs = conn.prepareCall(sql);
            rs = cs.executeQuery();
            while(rs.next()){
                Food f = new Food();
                f.setId(rs.getInt("food_id"));
                f.setName(rs.getString("food_name"));
                f.setPrice(rs.getDouble("price"));
                f.setStock(rs.getInt("stock"));
                f.setType(rs.getString("type"));
                list.add(f);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // xoa
    @Override
    public boolean deletePcsFoodName(String foodName) {
        boolean result = false;
        Connection conn = null;
        CallableStatement cstmt = null;
        String sql = "{CALL deletefood(?)}";
        try {
            conn = DBConnection.getConnection();
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1,foodName);
            int affectedRows = cstmt.executeUpdate();
            if(affectedRows>0){
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    // sua
    @Override
    public boolean updateFood(Food f){
        boolean result = false;
        Connection conn = null;
        CallableStatement cstmt = null;
        String sql = "{call  updatefood(?,?,?,?)}";
        conn = DBConnection.getConnection();
        try {
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1,f.getName());
            cstmt.setDouble(2,f.getPrice());
            cstmt.setInt(3,f.getStock());
            cstmt.setString(4,f.getType());
            int affectedRows = cstmt.executeUpdate();
            if(affectedRows>0){
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    // sua so luong
    public boolean updateQuantity(String foodName, int quantity) {

        String sql = "{call updatefoodquantity(?,?,?)}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, foodName);
            cs.setInt(2, quantity);
            cs.registerOutParameter(3, Types.BOOLEAN);

            cs.execute();

            return cs.getBoolean(3);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
