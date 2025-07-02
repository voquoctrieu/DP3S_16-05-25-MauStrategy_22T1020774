CREATE DATABASE MaSinhVienDB;
GO

USE MaSinhVienDB;
GO

CREATE TABLE PhanTu (
    Id INT PRIMARY KEY,
    Ten NVARCHAR(100) NOT NULL,
    IdCha INT,
    DungLuong BIGINT,
    Loai NVARCHAR(10) CHECK (Loai IN ('File', 'Folder')),
    FOREIGN KEY (IdCha) REFERENCES PhanTu(Id)
);
GO

-- Insert sample data
INSERT INTO PhanTu (Id, Ten, IdCha, DungLuong, Loai) VALUES
(1, 'Root', NULL, 0, 'Folder'),
(2, 'Documents', 1, 0, 'Folder'),
(3, 'Images', 1, 0, 'Folder'),
(4, 'report.txt', 2, 1024, 'File'),
(5, 'image1.jpg', 3, 2048, 'File'),
(6, 'image2.jpg', 3, 3072, 'File');
GO 