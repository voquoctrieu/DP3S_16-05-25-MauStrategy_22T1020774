import java.util.ArrayList;
import java.util.List;

public class Folder implements Component {
    private int id;
    private String ten;
    private Integer idCha;
    private long dungLuong;
    private String loai;
    private List<Component> children;

    public Folder() {
        this.loai = "Folder";
        this.children = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTen() {
        return ten;
    }

    @Override
    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public Integer getIdCha() {
        return idCha;
    }

    @Override
    public void setIdCha(Integer idCha) {
        this.idCha = idCha;
    }

    @Override
    public long getDungLuong() {
        return dungLuong;
    }

    @Override
    public void setDungLuong(long dungLuong) {
        this.dungLuong = dungLuong;
    }

    @Override
    public String getLoai() {
        return loai;
    }

    @Override
    public void setLoai(String loai) {
        this.loai = loai;
    }

    public void addChild(Component component) {
        children.add(component);
    }

    public List<Component> getChildren() {
        return children;
    }

    @Override
    public String getFullPath() {
        return ten;
    }

    @Override
    public void displayInfo() {
        System.out.println("Tên: " + ten);
        System.out.println("Đường dẫn: " + getFullPath());
        System.out.println("Loại: " + loai);
        System.out.println("Dung lượng: " + dungLuong + " bytes");
        System.out.println("Danh sách các file:");
        for (Component child : children) {
            System.out.println("- " + child.getTen());
        }
    }
} 