package prj.dao.impl;

import prj.dao.PcDAO;
import prj.model.Pc;
import prj.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PcDaoImpl implements PcDAO {

    // Thêm PC
    @Override
    public boolean addPc(Pc p) {
        boolean result = false;
        String sql = "{call insertPcs(?,?,?,?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, p.getName());
            stmt.setInt(2,p.getCate_id());
            stmt.setString(3, p.getStatus());
            stmt.setDouble(4, p.getPriceHoue());

            int i = stmt.executeUpdate();
            if (i > 0) result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Lấy tất cả PC
    @Override
    public List<Pc> getAllPc() {
        List<Pc> list = new ArrayList<>();
        String sql = "{call getAllPc()}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pc p = new Pc(
                        rs.getInt("pc_id"),
                        rs.getString("pc_name"),
                        rs.getInt("cate_id"),
                        rs.getString("status"),
                        rs.getDouble("priceHoue")
                );
                list.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Xóa PC theo tên
    @Override
    public boolean deletePcsByName(String pcName) {
        boolean result = false;
        String sql = "{call deletePcs(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, pcName);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Sửa PC
    @Override
    public boolean updatePc(Pc p) {
        boolean result = false;
        String sql = "{call updatePcs(?,?,?,?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getCate_id());
            stmt.setString(3, p.getStatus());
            stmt.setDouble(4, p.getPriceHoue());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) result = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // sửa theo id để cập nhật trạng thái
    @Override
    public boolean updateStatus(int id) {
        boolean result = false;
        String sql = "{call updatepcStatusToBooking(?)}";
        Connection conn = null;
        CallableStatement stmt = null;
        conn = DBConnection.getConnection();
        try{
            stmt = conn.prepareCall(sql);
            stmt.setInt(1, id);
            int i = stmt.executeUpdate();
            if (i > 0) result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean closeStatus(int id) {
        boolean result = false;
        String sql = "{call closeStatusToBooking(?)}";
        Connection conn = null;
        CallableStatement stmt = null;
        conn = DBConnection.getConnection();
        try{
            stmt = conn.prepareCall(sql);
            stmt.setInt(1, id);
            int i = stmt.executeUpdate();
            if (i > 0) result = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}