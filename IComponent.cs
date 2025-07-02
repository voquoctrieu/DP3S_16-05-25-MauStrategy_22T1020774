using System;
using System.Collections.Generic;

namespace FileSystemComposite
{
    public interface IComponent
    {
        int Id { get; set; }
        string Ten { get; set; }
        int? IdCha { get; set; }
        long DungLuong { get; set; }
        string Loai { get; set; }
        string GetFullPath();
        void DisplayInfo();
    }
} 