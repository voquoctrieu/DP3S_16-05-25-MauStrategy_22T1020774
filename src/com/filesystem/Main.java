package com.filesystem;

import com.filesystem.dao.DatabaseConnection;
import com.filesystem.model.Component;
import com.filesystem.model.File;
import com.filesystem.model.Folder;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            // 1. Đọc toàn bộ cấu trúc cây thư mục
            System.out.println("Đọc cấu trúc cây thư mục từ database...");
            List<Component> components = DatabaseConnection.getAllComponents();
            System.out.println("Đã đọc thành công " + components.size() + " phần tử.\n");

            // 2. Nhập Id và hiển thị thông tin
            System.out.print("Nhập ID cần tìm: ");
            int id = scanner.nextInt();
            Component component = DatabaseConnection.getComponentById(id);
            
            if (component != null) {
                System.out.println("\nThông tin phần tử:");
                component.displayInfo();
            } else {
                System.out.println("Không tìm thấy phần tử với ID: " + id);
            }

            // 3. Thêm file mới
            System.out.print("\nNhập ID thư mục để thêm file mới: ");
            int folderId = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới

            System.out.print("Nhập tên file mới: ");
            String fileName = scanner.nextLine();

            System.out.print("Nhập dung lượng file (bytes): ");
            long fileSize = scanner.nextLong();

            // Tạo file mới
            File newFile = new File();
            newFile.setId(components.size() + 1); // Tạo ID mới
            newFile.setTen(fileName);
            newFile.setIdCha(folderId);
            newFile.setDungLuong(fileSize);

            // Thêm file vào database
            DatabaseConnection.addFile(newFile);
            System.out.println("\nĐã thêm file mới thành công!");

            // Hiển thị thông tin thư mục sau khi thêm
            Component folder = DatabaseConnection.getComponentById(folderId);
            if (folder != null && folder instanceof Folder) {
                System.out.println("\nThông tin thư mục sau khi thêm file:");
                folder.displayInfo();
            }

        } catch (SQLException e) {
            System.out.println("Lỗi kết nối database: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
} 