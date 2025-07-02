package com.nvtrung.dp.strategy.strategy;

import com.nvtrung.dp.strategy.model.SinhVien;
import java.sql.*;
import java.util.List;

public class MySQLStrategy implements DatabaseStrategy {
    private Connection connection;
 
    private static final String URL = "jdbc:mysql://localhost:3306/sinhvien_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "123456"; 
    public MySQLStrategy() {
        try {
           
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi kết nối MySQL: Vui lòng kiểm tra MySQL server đã chạy, database 'sinhvien_db' tồn tại và thông tin kết nối trong MySQLStrategy.java");

        }
    }

    @Override
    public void insert(List<SinhVien> sinhVienList) {
        String sql = "INSERT INTO sinhvien (id, ho_ten, gioi_tinh, ngay_sinh, dia_chi, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            if (connection != null && !connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }

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

          
            if (connection != null && !connection.getAutoCommit()) {
                connection.commit();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi chèn dữ liệu vào MySQL!");
       
            try {
                if (connection != null && !connection.getAutoCommit()) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
    
             try {
                 if (connection != null && !connection.getAutoCommit()) {
      
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
            System.out.println("MySQLStrategy: Đã xóa " + deletedRows + " bản ghi khỏi bảng sinhvien.");
   
            if (connection != null && !connection.getAutoCommit()) {
                 connection.commit();
                 System.out.println("MySQLStrategy: Đã commit xóa bảng.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi xóa bảng trong MySQL!");
          
             try {
                 if (connection != null && !connection.getAutoCommit()) {
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
                System.out.println("MySQLStrategy: Đã đóng kết nối database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}