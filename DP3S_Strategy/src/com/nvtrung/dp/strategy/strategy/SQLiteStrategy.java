package com.nvtrung.dp.strategy.strategy;

import com.nvtrung.dp.strategy.model.SinhVien; // Đảm bảo import này đúng
import java.sql.*;
import java.util.List;

public class SQLiteStrategy implements DatabaseStrategy {
    private Connection connection;
    private static final String DB_PATH = "sinhvien.db"; // File database SQLite sẽ tạo/sử dụng

    public SQLiteStrategy() {
        try {
         
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);

          
            connection.setAutoCommit(false);

       
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
         
            if (!connection.getAutoCommit()) {
                 connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
             try {
                 if (!connection.getAutoCommit()) {
                     connection.rollback(); 
                 }
             } catch (SQLException ex) {
                 ex.printStackTrace();
             }
        }
    }

    @Override
    public void insert(List<SinhVien> sinhVienList) {
        System.out.println("SQLiteStrategy: Bắt đầu chèn dữ liệu."); // Log begin
        System.out.println("SQLiteStrategy: Số lượng sinh viên cần chèn: " + sinhVienList.size()); // Log list size
        String sql = "INSERT INTO sinhvien (id, ho_ten, gioi_tinh, ngay_sinh, dia_chi, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            int batchSize = 1000; 
            int count = 0; 

            for (SinhVien sv : sinhVienList) {
                pstmt.setInt(1, sv.getId());
                pstmt.setString(2, sv.getHoTen());
                pstmt.setString(3, sv.getGioiTinh());
                pstmt.setDate(4, new java.sql.Date(sv.getNgaySinh().getTime())); 
                pstmt.setString(5, sv.getDiaChi());
                pstmt.setString(6, sv.getEmail());
                pstmt.addBatch();

                count++;
               
                if (count % batchSize == 0 || count == sinhVienList.size()) {
                     System.out.println("SQLiteStrategy: Chuẩn bị thực thi batch với " + count % batchSize + " bản ghi (hoặc cuối cùng)."); // Log batch prepare
                    int[] results = pstmt.executeBatch();
                    System.out.println("SQLiteStrategy: Đã thực thi batch. Số lượng kết quả: " + results.length); // Log batch execute result

                  
                    if (!connection.getAutoCommit()) {
                        connection.commit();
                        System.out.println("SQLiteStrategy: Đã commit batch."); 
                    }
                    pstmt.clearBatch();
                }
            }
            System.out.println("SQLiteStrategy: Hoàn thành quá trình chèn dữ liệu."); 

        } catch (SQLException e) {
            System.err.println("SQLiteStrategy: Lỗi khi chèn dữ liệu!"); 
            e.printStackTrace();
            try {
                 if (!connection.getAutoCommit()) {
                     connection.rollback(); 
                     System.err.println("SQLiteStrategy: Đã thực hiện rollback do lỗi."); 
                 }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void clearTable() {
        String sql = "DELETE FROM sinhvien";
        try (Statement stmt = connection.createStatement()) {
            int deletedRows = stmt.executeUpdate(sql); 
            System.out.println("SQLiteStrategy: Đã xóa " + deletedRows + " bản ghi khỏi bảng sinhvien."); 
           
            if (!connection.getAutoCommit()) {
                 connection.commit();
                 System.out.println("SQLiteStrategy: Đã commit xóa bảng."); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
             try {
                 if (!connection.getAutoCommit()) {
                     connection.rollback(); 
                 }
             } catch (SQLException ex) {
                 ex.printStackTrace();
             }
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
              
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.close();
                System.out.println("SQLiteStrategy: Đã đóng kết nối database."); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}