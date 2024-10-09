package bms.product;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Stationery extends Product {

    private String type;
    private String manufacturer;
    private String material;

    public Stationery(String type, String manufacturer, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.type = type;
        this.manufacturer = manufacturer;
        this.material = material;
    }

    // Them mot san pham van phong vao co so du lieu
    public void addStationery(String type, String manufacturer, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de them du lieu vao bang Stationery
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Stationery VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, costPrice);
            stmt.setDouble(4, salePrice);
            stmt.setInt(5, quantity);
            stmt.setString(6, unit);
            stmt.setString(7, origin);
            stmt.setString(8, type);
            stmt.setString(9, manufacturer);
            stmt.setString(10, material);

            // Bam xac nhan nhap
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao nhap thanh cong, hien thi ra thong bao them thanh cong
            }
        } catch (Exception e) {
            // Thong bao nhap du lieu khong hop le, yeu cau nhap lai
        }
    }

    // Xoa mot san pham van phong khoi co so du lieu
    public void deleteStationery(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de xoa du lieu trong bang Stationery
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Stationery WHERE id = ?");
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

    // Cap nhat thong tin mot san pham van phong trong co so du lieu
    public void updateStationery(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de cap nhat du lieu trong bang Stationery
            PreparedStatement stmt = con.prepareStatement("UPDATE Stationery SET " + col + " = ? WHERE id = ?");
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
