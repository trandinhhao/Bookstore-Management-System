package bms.product;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Gift extends Product {

    private String type;
    private String material;

    public Gift(String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin,
                String type, String material) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.type = type;
        this.material = material;
    }

    // Getter methods
    public String getType() {
        return type;
    }

    public String getMaterial() {
        return material;
    }

    // Thêm một món quà vào cơ sở dữ liệu
    public void addGift() {
        try (Connection con = ConnectMySQL.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Gift VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, costPrice);
            stmt.setDouble(4, salePrice);
            stmt.setInt(5, quantity);
            stmt.setString(6, unit);
            stmt.setString(7, origin);
            stmt.setString(8, type);
            stmt.setString(9, material);

            // Xác nhận nhập
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Thêm món quà thành công!"); // Hoặc hiển thị thông báo cho người dùng
            }
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi thêm món quà: " + e.getMessage());
        }
    }

    // Xóa một món quà khỏi cơ sở dữ liệu
    public void deleteGift(String id) {
        try (Connection con = ConnectMySQL.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Gift WHERE id = ?");
            stmt.setString(1, id);

            // Xác nhận xóa
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Xóa món quà thành công!"); // Hoặc hiển thị thông báo cho người dùng
            }
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi xóa món quà: " + e.getMessage());
        }
    }

    // Cập nhật thông tin một món quà trong cơ sở dữ liệu
    public void updateGift(String col, String val, String id) {
        try (Connection con = ConnectMySQL.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE Gift SET " + col + " = ? WHERE id = ?");
            stmt.setString(1, val);
            stmt.setString(2, id);

            // Xác nhận cập nhật
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Cập nhật món quà thành công!"); // Hoặc hiển thị thông báo cho người dùng
            }
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi cập nhật món quà: " + e.getMessage());
        }
    }
}
