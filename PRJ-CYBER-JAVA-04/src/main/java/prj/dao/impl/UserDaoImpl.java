package prj.dao.impl;

import prj.dao.UserDAO;
import prj.model.User;
import prj.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDAO {
    @Override
    public User login(String username,String password){
        String sql = "{call sp_login(?,?)}";
        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql)){

            cs.setString(1,username);
            cs.setString(2,password);

            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                User u = new User();
                u.setUserId(rs.getString("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullName(rs.getString("full_name"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                u.setBalance(rs.getDouble("balance"));
                u.setCreate_at(rs.getTimestamp("created_at"));
                return u;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean register(User user){
        String sql = "{call sp_register(?,?,?,?,?,?)}";
        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql)){

            cs.setString(1,user.getUserId());
            cs.setString(2,user.getUsername());
            cs.setString(3,user.getPassword());
            cs.setString(4,user.getFullName());
            cs.setString(5,user.getPhone());
            cs.setString(6,user.getRole());

            return cs.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAllUser(){
        List<User> list = new ArrayList<>();
        String sql = "{call getallusers()}";

        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery()) {

            while(rs.next()){
                User u = new User();
                u.setUserId(rs.getString("user_id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullName(rs.getString("full_name"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                u.setBalance(rs.getDouble("balance"));
                u.setCreate_at(rs.getTimestamp("created_at"));
                list.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean updateUser(User user){
        String sql = "{call updateuser(?,?,?,?,?,?)}";
        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1,user.getUsername());
            cs.setString(2,user.getPassword());
            cs.setString(3,user.getFullName());
            cs.setString(4,user.getPhone());
            cs.setString(5,user.getRole());
            cs.setDouble(6,user.getBalance());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteUser(User user){
        String sql = "{call deleteuser(?)}";
        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1,user.getUsername());
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addUser(User user){
        String sql = "{call addUser(?,?,?,?,?,?,?)}";
        try(Connection conn = DBConnection.getConnection();
            CallableStatement cs = conn.prepareCall(sql)){

            cs.setString(1,user.getUserId());
            cs.setString(2,user.getUsername());
            cs.setString(3,user.getPassword());
            cs.setString(4,user.getFullName());
            cs.setString(5,user.getPhone());
            cs.setString(6,user.getRole());
            cs.setDouble(7,user.getBalance());

            return cs.executeUpdate() > 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}