using System;
using System.Collections.Generic;

namespace FileSystemComposite
{
    public class File : IComponent
    {
        public int Id { get; set; }
        public string Ten { get; set; }
        public int? IdCha { get; set; }
        public long DungLuong { get; set; }
        public string Loai { get; set; }

        public File()
        {
            Loai = "File";
        }

        public string GetFullPath()
        {
            return Ten;
        }

        public void DisplayInfo()
        {
            Console.WriteLine($"Tên: {Ten}");
            Console.WriteLine($"Đường dẫn: {GetFullPath()}");
            Console.WriteLine($"Loại: {Loai}");
            Console.WriteLine($"Dung lượng: {DungLuong} bytes");
        }
    }
} 