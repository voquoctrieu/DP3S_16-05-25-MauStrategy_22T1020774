package com.nvtrung.dp.strategy.main;

import com.nvtrung.dp.strategy.context.DatabaseContext;
import com.nvtrung.dp.strategy.model.SinhVien;
import com.nvtrung.dp.strategy.strategy.*;
import java.util.*;

public class BenchmarkApp {
    public static void main(String[] args) {
        // Tạo danh sách sinh viên mẫu
        List<SinhVien> sinhVienList = generateSampleData(1000);
        
        // Tạo context
        DatabaseContext context = new DatabaseContext();
        
        // Benchmark cho MySQL
        System.out.println("Benchmark MySQL:");
        context.setStrategy(new MySQLStrategy());
        benchmarkInsert(context, sinhVienList);
        // Đóng kết nối sau mỗi benchmark
        context.close();
        
        // Benchmark cho SQL Server
        System.out.println("\nBenchmark SQL Server:");
        context.setStrategy(new SQLServerStrategy());
        benchmarkInsert(context, sinhVienList);
        
        // Benchmark cho SQLite
        System.out.println("\nBenchmark SQLite:");
        context.setStrategy(new SQLiteStrategy());
        benchmarkInsert(context, sinhVienList);
        
        // Đóng kết nối
        context.close();
    }
    
    private static void benchmarkInsert(DatabaseContext context, List<SinhVien> sinhVienList) {
        context.clearTable();
        long startTime = System.currentTimeMillis();
        context.insertData(sinhVienList);
        long endTime = System.currentTimeMillis();
        System.out.println("Thời gian thực thi: " + (endTime - startTime) + "ms");
    }
    
    private static List<SinhVien> generateSampleData(int count) {
        List<SinhVien> list = new ArrayList<>();
        Random random = new Random();
        String[] ho = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng"};
        String[] ten = {"An", "Bình", "Cường", "Dũng", "Em", "Phương", "Hương", "Linh"};
        
        for (int i = 0; i < count; i++) {
            String hoTen = ho[random.nextInt(ho.length)] + " " + ten[random.nextInt(ten.length)];
            String gioiTinh = random.nextBoolean() ? "Nam" : "Nữ";
            Date ngaySinh = new Date(random.nextLong(946684800000L, 1577836800000L)); // 2000-2020
            String diaChi = "Địa chỉ " + (i + 1);
            String email = "email" + (i + 1) + "@example.com";
            
            list.add(new SinhVien(i + 1, hoTen, gioiTinh, ngaySinh, diaChi, email));
        }
        return list;
    }
} 