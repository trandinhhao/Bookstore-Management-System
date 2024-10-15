package bms.system_management;

import bms.connectDB.ConnectMySQL;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;

public class InventoryManager {
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private String category;
    private String location;

    public InventoryManager(int productId, String productName, int quantity, double unitPrice, String category, String location) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
        this.location = location;
    }
    
    public void saveToDatabase() throws SQLException{
        String sqlString = "INSERT INTO inventory (product_name, quantity, unit_price, category, location) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = ConnectMySQL.getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sqlString)){
            pstmt.setInt(1, this.productId);
            pstmt.setString(2, this.productName);
            pstmt.setInt(3, this.quantity);
            pstmt.setDouble(4, this.unitPrice);
            pstmt.setString(5, this.category);
            pstmt.setString(6, this.location);
            pstmt.executeUpdate();
        }
    }
    
    
    public static InventoryManager getProductById(int productId) throws SQLException{
        String sqlString = "SELECT * FROM inventory WHERE product_id = ?";
        Connection conn = ConnectMySQL.getConnection();
        
        try(PreparedStatement pstmt = conn.prepareStatement(sqlString)){
            pstmt.setInt(1, productId);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return new InventoryManager(
                            rs.getInt("product_id"), 
                            rs.getString("product_name"), 
                            rs.getInt("quantity"),
                            rs.getDouble("unit_price"),
                            rs.getString("category"),
                            rs.getString("location"));
                }
            }
        }
        return null;
    }
    
    public static ArrayList<InventoryManager> getAllProducts() throws SQLException{
        String sqlString = "SELECT * FROM inventory";
        ArrayList<InventoryManager> products = new ArrayList<>();
        Connection conn = ConnectMySQL.getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sqlString)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                products.add(new InventoryManager(
                        rs.getInt("product_id"), 
                            rs.getString("product_name"), 
                            rs.getInt("quantity"),
                            rs.getDouble("unit_price"),
                            rs.getString("category"),
                            rs.getString("location")
                ));
            }
        }
        return null;
    }
    
    public void updateToDatabase() throws SQLException{
        String sqlString = "UPDATE inventory SET product_name = ?, quantity = ?, unit_price = ?, category = ?, location = ? WHERE product_id = ?";
        Connection conn = ConnectMySQL.getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sqlString)){
            pstmt.setString(1, this.productName);
            pstmt.setInt(2, this.quantity);
            pstmt.setDouble(3, this.unitPrice);
            pstmt.setString(4, this.category);
            pstmt.setString(5, this.location);
            pstmt.setInt(6, this.productId);
            pstmt.executeUpdate();
        }
    }
    
    public void deleteFromDatabase() throws SQLException {
        String sqlString = "DELETE FROM inventory WHERE product_id = ?";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setInt(1, this.productId);
            pstmt.executeUpdate();
        }
    }
    
    public void updateQuantity(int change) throws SQLException {
        Connection conn = ConnectMySQL.getConnection();
        this.quantity += change;
        String sql = "UPDATE inventory SET quantity = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.quantity);
            pstmt.setInt(2, this.productId);
            pstmt.executeUpdate();
        }
    }
    
    public static List<InventoryManager> getLowStockProducts(int threshold) throws SQLException {
        Connection conn = ConnectMySQL.getConnection();
        ArrayList<InventoryManager> lowStockProducts = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE quantity < ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, threshold);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lowStockProducts.add(new InventoryManager(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price"),
                        rs.getString("category"),
                        rs.getString("location")
                    ));
                }
            }
        }
        return lowStockProducts;
    }
}
