package bms.product;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Toy extends Product {

    private String type;
    private String ageRange;
    private String material;

    public Toy(String type, String ageRange, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.type = type;
        this.ageRange = ageRange;
        this.material = material;
    }

    // Thêm một đồ chơi vào cơ sở dữ liệu
    public void addToy(String type, String ageRange, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuẩn bị câu lệnh SQL để thêm dữ liệu vào bảng Toy
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Toy VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, costPrice);
            stmt.setDouble(4, salePrice);
            stmt.setInt(5, quantity);
            stmt.setString(6, unit);
            stmt.setString(7, origin);
            stmt.setString(8, type);
            stmt.setString(9, ageRange);
            stmt.setString(10, material);

            // Xác nhận nhập
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Thêm đồ chơi thành công.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Có lỗi xảy ra trong quá trình thêm đồ chơi. Vui lòng kiểm tra lại.");
        }
    }

    // Xóa một đồ chơi khỏi cơ sở dữ liệu
    public void deleteToy(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuẩn bị câu lệnh SQL để xóa dữ liệu trong bảng Toy
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Toy WHERE id = ?");
            stmt.setString(1, id);

            // Xác nhận xóa
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Xóa đồ chơi thành công.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Có lỗi xảy ra trong quá trình xóa đồ chơi. Vui lòng kiểm tra lại ID.");
        }
    }

    // Cập nhật thông tin một đồ chơi trong cơ sở dữ liệu
    public void updateToy(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuẩn bị câu lệnh SQL để cập nhật dữ liệu trong bảng Toy
            PreparedStatement stmt = con.prepareStatement("UPDATE Toy SET " + col + " = ? WHERE id = ?");
            stmt.setString(1, val);
            stmt.setString(2, id);

            // Xác nhận cập nhật
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Cập nhật đồ chơi thành công.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Có lỗi xảy ra trong quá trình cập nhật đồ chơi. Vui lòng kiểm tra lại thông tin.");
        }
    }
}
