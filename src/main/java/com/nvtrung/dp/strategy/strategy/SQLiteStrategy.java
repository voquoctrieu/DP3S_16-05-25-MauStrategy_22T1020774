package com.nvtrung.dp.strategy.strategy;

import com.nvtrung.dp.strategy.model.SinhVien;
import java.sql.*;
import java.util.List;

public class SQLiteStrategy implements DatabaseStrategy {
    private Connection connection;
    private static final String DB_PATH = "sinhvien.db";

    public SQLiteStrategy() {
        try {
            // Tạo kết nối đến SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            
            // Tạo bảng nếu chưa tồn tại
            createTableIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS sinhvien (
                id INTEGER PRIMARY KEY,
                ho_ten TEXT NOT NULL,
                gioi_tinh TEXT,
                ngay_sinh DATE,
                dia_chi TEXT,
                email TEXT
            )
        """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(List<SinhVien> sinhVienList) {
        System.out.println("SQLiteStrategy: Bắt đầu chèn dữ liệu.");
        System.out.println("SQLiteStrategy: Số lượng sinh viên cần chèn: " + sinhVienList.size());
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
            System.out.println("SQLiteStrategy: Hoàn thành chuẩn bị batch insert.");
            int[] results = pstmt.executeBatch();
            System.out.println("SQLiteStrategy: Hoàn thành executeBatch(). Số lượng thao tác thành công (có thể): " + results.length);
        } catch (SQLException e) {
            System.err.println("SQLiteStrategy: Lỗi khi chèn dữ liệu!");
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
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 