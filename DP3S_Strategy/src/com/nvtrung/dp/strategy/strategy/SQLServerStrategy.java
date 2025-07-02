package com.nvtrung.dp.strategy.strategy;

import com.nvtrung.dp.strategy.model.SinhVien;
import java.sql.*;
import java.util.List;

public class SQLServerStrategy implements DatabaseStrategy {
    private Connection connection;
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=NhanVienDB;encrypt=false"; 
    private static final String USER = "sa"; 
    private static final String PASSWORD = "20082004"; 

    public SQLServerStrategy() {
        try {
             
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
    }

    @Override
    public void insert(List<SinhVien> sinhVienList) {
        String sql = "INSERT INTO sinhvien (id, ho_ten, gioi_tinh, ngay_sinh, dia_chi, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (SinhVien sv : sinhVienList) {
                pstmt.setInt(1, sv.getId());
                pstmt.setString(2, sv.getHoTen());
                pstmt.setString(3, sv.getGioiTinh());
                pstmt.setDate(4, new java.sql.Date(sv.getNgaySinh().getTime())); 
                pstmt.setString(5, sv.getDiaChi());
                pstmt.setString(6, sv.getEmail());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM sinhvien";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}