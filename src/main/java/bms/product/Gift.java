package bms.product;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Gift extends Product {

    private String type;
    private String material;

    public Gift(String type, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.type = type;
        this.material = material;
    }

    // Them mot mon qua vao co so du lieu
    public void addGift(String type, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de them du lieu vao bang Gift
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Gift VALUES (?, ?, ?, ?, ? ,?, ?, ?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, costPrice);
            stmt.setDouble(4, salePrice);
            stmt.setInt(5, quantity);
            stmt.setString(6, unit);
            stmt.setString(7, origin);
            stmt.setString(8, type);
            stmt.setString(9, material);

            // Bam xac nhan nhap
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao nhap thanh cong, hien thi ra thong bao them thanh cong
            }
        } catch (Exception e) {
            // Thong bao nhap du lieu khong hop le, yeu cau nhap lai
        }
    }

    // Xoa mot mon qua khoi co so du lieu
    public void deleteGift(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de xoa du lieu trong bang Gift
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Gift WHERE id = ?");
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

    // Cap nhat thong tin mot mon qua trong co so du lieu
    public void updateGift(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de cap nhat du lieu trong bang Gift
            PreparedStatement stmt = con.prepareStatement("UPDATE Gift SET " + col + " = ? WHERE id = ?");
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
    
    public static Gift getProductById(String productId) throws SQLException, ClassNotFoundException {
        String sqlString = "SELECT * FROM gift WHERE id= ?";
        Connection con = ConnectMySQL.getConnection();
        try(PreparedStatement stmt = con.prepareStatement(sqlString)){
            stmt.setString(1, productId);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    return new Gift(
                            rs.getString("type"),
                            rs.getString("material"),
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("cost_price"),
                            rs.getDouble("sale_price"),
                            rs.getInt("quantity"),
                            rs.getString("unit"),
                            rs.getString("origin")
                    );
                }
            }
        }
        return null;
    }
    
    
}
