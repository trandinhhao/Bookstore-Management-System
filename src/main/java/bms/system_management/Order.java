package bms.system_management;

import bms.product.Product;
import bms.connectDB.ConnectMySQL;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Order {

    private int orderId;
    private int customerId;
//    private LinkedHashMap<Product, Integer> products;
    private String productID;
    private Date orderDate;
    private double totalAmount;
    private String status;

    public Order() {

    }

    public Order(String productID, int orderId, int customerId, Date orderDate, double totalAmount, String status) {
//        this.products.put(products, orderId);
        this.productID = productID;
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }

    public void addOrder() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders (customer_id, order_date, total_amount, status) VALUES (?, ?, ?, ?)";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.productID);
            pstmt.setInt(2, this.customerId);
            pstmt.setDate(3, new java.sql.Date(this.orderDate.getTime()));
            pstmt.setDouble(4, this.totalAmount);
            pstmt.setString(5, this.status);
            pstmt.executeUpdate();
        }
    }

    public static Order getOrderById(String productId, int orderId) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();

        String sqlString = "SELECT * FROM orders WHERE order_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getString("product_id"),
                            rs.getInt("order_id"),
                            rs.getInt("customer_id"),
                            rs.getDate("order_date"),
                            rs.getDouble("total_amount"),
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
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("order_date"),
                        rs.getDouble("total_amount"),
                        rs.getString("status")
                ));
            }
        }
        return null;
    }

    public void updateInDatabase() throws SQLException, ClassNotFoundException {
        String sqlString = "UPDATE orders SET customer_id = ?, order_date = ?, total_amount = ?, status = ? WHERE order_id = ?";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setString(1, this.productID);
            pstmt.setInt(2, this.orderId);
            pstmt.setInt(3, this.customerId);
            pstmt.setDate(4, new java.sql.Date(this.orderDate.getTime()));
            pstmt.setDouble(5, this.totalAmount);
            pstmt.setString(6, this.status);
            pstmt.executeUpdate();
        }
    }

    public void deleteFromDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.orderId);
            pstmt.executeUpdate();
        }
    }

    public void updateProduct(int totalAmount) throws SQLException, ClassNotFoundException {
        String sqlString = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";

        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setInt(1, totalAmount);
            pstmt.setString(2, this.productID);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Update failed, no product found with ID: " + this.productID);
            }
        } catch (SQLException e) {
            System.out.println("Error updating product quantity: " + e.getMessage());
        }
    }

    public double calculateInvoiceTotal(String productId) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();

        String sqlPrice = "SELECT sale_price FROM products WHERE product_id = ?";
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

        String sql = "SELECT quantity as total FROM orders WHERE product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalAmount = rs.getDouble("total");
                }
            }
        }
        return totalAmount * price;
    }
}
