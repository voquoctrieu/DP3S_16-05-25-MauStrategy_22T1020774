package com.nvtrung.dp.strategy.context;

import com.nvtrung.dp.strategy.model.SinhVien;
import com.nvtrung.dp.strategy.strategy.DatabaseStrategy;
import java.util.List;

public class DatabaseContext {
    private DatabaseStrategy strategy;

    public void setStrategy(DatabaseStrategy strategy) {
        this.strategy = strategy;
    }

    public void insertData(List<SinhVien> sinhVienList) {
        if (strategy != null) {
            strategy.insert(sinhVienList);
        }
    }

    public void clearTable() {
        if (strategy != null) {
            strategy.clearTable();
        } else {
            System.out.println("No database strategy set.");
        }
    }

    public void close() {
        if (strategy != null) {
            strategy.close();
        }
    }
} 