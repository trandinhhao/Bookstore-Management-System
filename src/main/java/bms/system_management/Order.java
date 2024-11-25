package bms.system_management;
// DONE

import bms.product.Product;
import bms.connectDB.ConnectMySQL;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Order {

    private String orderId;
    private String productID;
    private java.util.Date orderDate;
    private int totalAmount;
    private double totalPrice;
    private String status;

    public Order() {

    }

    public Order(String productID, String orderId, java.util.Date orderDate, int totalAmount, double total_price, String status) {
//        this.products.put(products, orderId);
        this.productID = productID;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.totalPrice = total_price;
        this.orderDate = orderDate;
        this.status = status;
    }

    public void addOrder() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders (order_id, product_id, order_date, total_amount, total_price, status) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, orderId);
            pstmt.setString(2, this.productID);
            pstmt.setDate(3, new java.sql.Date(this.orderDate.getTime()));
            pstmt.setDouble(4, this.totalAmount);
            pstmt.setDouble(5, this.totalPrice);
            pstmt.setString(6, this.status);
            pstmt.executeUpdate();
        }
    }

    public static Order getOrderById(String productId) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();

        String sqlString = "SELECT * FROM orders WHERE product_id= ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setString(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getString("product_id"),
                            rs.getString("order_id"),
                            rs.getDate("order_date"),
                            rs.getInt("total_amount"),
                            rs.getDouble("total_price"),
                            rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public static ArrayList<Order> getAllOrders() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        String sqlString = "SELECT * FROM orders";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("product_id"),
                        rs.getString("order_id"),
                        rs.getDate("order_date"),
                        rs.getInt("total_amount"),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                ));
            }
        }
        return null;
    }

    public void updateInDatabase() throws SQLException, ClassNotFoundException {
        String sqlString = "UPDATE orders SET customer_id = ?, order_date = ?, total_amount = ?, total_price = ?, status = ? WHERE order_id = ?";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setString(1, this.productID);
            pstmt.setString(2, this.orderId);
            pstmt.setDate(3, new java.sql.Date(this.orderDate.getTime()));
            pstmt.setDouble(4, this.totalAmount);
            pstmt.setString(5, this.status);
            pstmt.executeUpdate();
        }
    }

    public void deleteFromDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.orderId);
            pstmt.executeUpdate();
        }
    }

    public static void updateProduct(int totalAmount, String productID, String productType) throws SQLException, ClassNotFoundException {
        String sqlString = String.format("UPDATE %s SET quantity = quantity - ? WHERE id = ?", productType);

        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setInt(1, totalAmount);
            pstmt.setString(2, productID);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Update failed, no product found with ID: " + productID);
            }
        } catch (SQLException e) {
            System.out.println("Error updating product quantity: " + e.getMessage());
        }
    }

    public static double calculateInvoiceTotal(String productId, int quantity, String productType) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();

        String sqlPrice = String.format("SELECT sale_price FROM %s WHERE id = ?", productType);
        double price = 0;
        double totalAmount = 0;
        try (PreparedStatement pstmt = conn.prepareStatement(sqlPrice)) {
            pstmt.setString(1, productId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("sale_price");
                }
            }
        }
        return price * quantity;
    }

    public String getOrderId() {
        return this.orderId;
    }
}
