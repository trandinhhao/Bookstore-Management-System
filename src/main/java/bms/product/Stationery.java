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

    // Thêm một sản phẩm văn phòng vào cơ sở dữ liệu
    public void addStationery(String type, String manufacturer, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuẩn bị câu lệnh SQL để thêm dữ liệu vào bảng Stationery
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

            // Xác nhận nhập
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Thêm sản phẩm văn phòng thành công.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Có lỗi xảy ra trong quá trình thêm sản phẩm văn phòng. Vui lòng kiểm tra lại.");
        }
    }

    // Xóa một sản phẩm văn phòng khỏi cơ sở dữ liệu
    public void deleteStationery(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuẩn bị câu lệnh SQL để xóa dữ liệu trong bảng Stationery
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Stationery WHERE id = ?");
            stmt.setString(1, id);

            // Xác nhận xóa
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Xóa sản phẩm văn phòng thành công.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Có lỗi xảy ra trong quá trình xóa sản phẩm văn phòng. Vui lòng kiểm tra lại ID.");
        }
    }

    // Cập nhật thông tin một sản phẩm văn phòng trong cơ sở dữ liệu
    public void updateStationery(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuẩn bị câu lệnh SQL để cập nhật dữ liệu trong bảng Stationery
            PreparedStatement stmt = con.prepareStatement("UPDATE Stationery SET " + col + " = ? WHERE id = ?");
            stmt.setString(1, val);
            stmt.setString(2, id);

            // Xác nhận cập nhật
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Cập nhật sản phẩm văn phòng thành công.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Có lỗi xảy ra trong quá trình cập nhật sản phẩm văn phòng. Vui lòng kiểm tra lại thông tin.");
        }
    }
}
