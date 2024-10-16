package bms.product;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Notebook extends Product {
    private int pageCount;
    private String paperType;
    private String size;
    private String manufacturer;

    public Notebook(int pageCount, String paperType, String size, String manufacturer, String id, String name,
                    double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.pageCount = pageCount;
        this.paperType = paperType;
        this.size = size;
        this.manufacturer = manufacturer;
    }

    // Getter methods
    public int getPageCount() {
        return pageCount;
    }

    public String getPaperType() {
        return paperType;
    }

    public String getSize() {
        return size;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    // Thêm một cuốn sổ vào cơ sở dữ liệu
    public void addNotebook() {
        try (Connection con = ConnectMySQL.getConnection()) {
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

            // Xác nhận nhập
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Thêm cuốn sổ thành công!"); // Hoặc hiển thị thông báo cho người dùng
            }
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi thêm cuốn sổ: " + e.getMessage());
        }
    }

    // Xóa một cuốn sổ khỏi cơ sở dữ liệu
    public void deleteNotebook(String id) {
        try (Connection con = ConnectMySQL.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Notebook WHERE id = ?");
            stmt.setString(1, id);

            // Xác nhận xóa
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Xóa cuốn sổ thành công!"); // Hoặc hiển thị thông báo cho người dùng
            }
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi xóa cuốn sổ: " + e.getMessage());
        }
    }

    // Cập nhật thông tin một cuốn sổ trong cơ sở dữ liệu
    public void updateNotebook(String col, String val, String id) {
        try (Connection con = ConnectMySQL.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE Notebook SET " + col + " = ? WHERE id = ?");
            stmt.setString(1, val);
            stmt.setString(2, id);

            // Xác nhận cập nhật
            int row = stmt.executeUpdate();
            if (row > 0) {
                System.out.println("Cập nhật cuốn sổ thành công!"); // Hoặc hiển thị thông báo cho người dùng
            }
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra khi cập nhật cuốn sổ: " + e.getMessage());
        }
    }
}
