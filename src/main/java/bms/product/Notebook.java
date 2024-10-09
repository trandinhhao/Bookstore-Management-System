package bms.product;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Notebook extends Product {
    private int pageCount;
    private String paperType;
    private String size;
    private String manufacturer;

    public Notebook(int pageCount, String paperType, String size, String manufacturer, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.pageCount = pageCount;
        this.paperType = paperType;
        this.size = size;
        this.manufacturer = manufacturer;
    }

    // Them mot cuon sach vao co so du lieu
    public void addNotebook(int pageCount, String paperType, String size, String manufacturer, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de them du lieu vao bang Notebook
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Notebook VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, costPrice);
            stmt.setDouble(4, salePrice);
            stmt.setInt(5, quantity);
            stmt.setString(6, unit);
            stmt.setString(7, origin);
            stmt.setInt(8, pageCount);
            stmt.setString(9, paperType);
            stmt.setString(10, size);
            stmt.setString(11, manufacturer);

            // Bam xac nhan nhap
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao nhap thanh cong, hien thi ra thong bao them thanh cong
            }
        } catch (Exception e) {
            // Thong bao nhap du lieu khong hop le, yeu cau nhap lai
        }
    }

    // Xoa mot cuon sach khoi co so du lieu
    public void deleteNotebook(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de xoa du lieu trong bang Notebook
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Notebook WHERE id = ?");
            stmt.setString(1, id);

            // Bam xac nhan xoa
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao da xoa thanh cong, hien thi ra thong bao xoa thanh cong
            }
        } catch (Exception e) {
            // Thong bao xoa khong thanh cong, yeu cau nhap lai id
        }
    }

    // Cap nhat thong tin mot cuon sach trong co so du lieu
    public void updateNotebook(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de cap nhat du lieu trong bang Notebook
            PreparedStatement stmt = con.prepareStatement("UPDATE Notebook SET " + col + " = ? WHERE id = ?");
            stmt.setString(1, val);
            stmt.setString(2, id);

            // Bam xac nhan cap nhat
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao cap nhat thanh cong, hien thi ra thong bao cap nhat thanh cong
            }
        } catch (Exception e) {
            // Thong bao cap nhat khong thanh cong, yeu cau nhap lai thong tin
        }
    }
}
