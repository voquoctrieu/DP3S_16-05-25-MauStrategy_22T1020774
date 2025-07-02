package com.filesystem.dao;

import com.filesystem.model.Component;
import com.filesystem.model.File;
import com.filesystem.model.Folder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=MaSinhVienDB;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "20082004";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<Component> getAllComponents() throws SQLException {
        List<Component> components = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PhanTu")) {

            while (rs.next()) {
                Component component;
                if (rs.getString("Loai").equals("File")) {
                    component = new File();
                } else {
                    component = new Folder();
                }
                component.setId(rs.getInt("Id"));
                component.setTen(rs.getString("Ten"));
                component.setIdCha(rs.getInt("IdCha"));
                component.setDungLuong(rs.getLong("DungLuong"));
                component.setLoai(rs.getString("Loai"));
                components.add(component);
            }
        }
        return components;
    }

    public static Component getComponentById(int id) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PhanTu WHERE Id = ?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Component component;
                if (rs.getString("Loai").equals("File")) {
                    component = new File();
                } else {
                    component = new Folder();
                }
                component.setId(rs.getInt("Id"));
                component.setTen(rs.getString("Ten"));
                component.setIdCha(rs.getInt("IdCha"));
                component.setDungLuong(rs.getLong("DungLuong"));
                component.setLoai(rs.getString("Loai"));
                return component;
            }
        }
        return null;
    }

    public static void addFile(File file) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO PhanTu (Id, Ten, IdCha, DungLuong, Loai) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setInt(1, file.getId());
            pstmt.setString(2, file.getTen());
            pstmt.setInt(3, file.getIdCha());
            pstmt.setLong(4, file.getDungLuong());
            pstmt.setString(5, file.getLoai());
            pstmt.executeUpdate();
        }
    }
} 