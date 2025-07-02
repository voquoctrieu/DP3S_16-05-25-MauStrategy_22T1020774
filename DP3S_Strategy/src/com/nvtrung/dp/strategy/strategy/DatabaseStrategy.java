package com.nvtrung.dp.strategy.strategy;

import com.nvtrung.dp.strategy.model.SinhVien;
import java.util.List;

public interface DatabaseStrategy {
    void insert(List<SinhVien> sinhVienList);
    void clearTable(); 
    void close();
}