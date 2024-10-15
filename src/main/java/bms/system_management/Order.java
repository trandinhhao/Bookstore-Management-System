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
    private Date orderDate;
    private double totalAmount;
    private String status;

    public Order(int orderId, int customerId, Date orderDate, double totalAmount, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
//        this.products = products;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.status = status;
    }
    
    public void addOrder() throws SQLException{
        String sql = "INSERT INTO orders (customer_id, order_date, total_amount, status) VALUES (?, ?, ?, ?)";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.customerId);
            pstmt.setDate(2, new java.sql.Date(this.orderDate.getTime()));
            pstmt.setDouble(3, this.totalAmount);
            pstmt.setString(4, this.status);
            pstmt.executeUpdate();
        }
    }
    
    public static Order getOrderById(int orderId) throws SQLException{
        String sqlString = "SELECT * FROM orders WHERE order_id = ?";
        Connection conn = ConnectMySQL.getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sqlString)){
            pstmt.setInt(1, orderId);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return new Order(
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
    
    public static ArrayList<Order> getAllOrders() throws SQLException{
        ArrayList<Order> orders = new ArrayList<>();
        String sqlString = "SELECT * FROM orders";
        Connection conn = ConnectMySQL.getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sqlString)){
            ResultSet rs  = pstmt.executeQuery();
            while(rs.next()){
                orders.add(new Order(
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
    
    public void updateInDatabase() throws SQLException{
        String sqlString = "UPDATE orders SET customer_id = ?, order_date = ?, total_amount = ?, status = ? WHERE order_id = ?";
        Connection conn = ConnectMySQL.getConnection();
        try(PreparedStatement pstmt = conn.prepareStatement(sqlString)){
            pstmt.setInt(1, this.orderId);
            pstmt.setInt(2, this.customerId);
            pstmt.setDate(3, new java.sql.Date(this.orderDate.getTime()));
            pstmt.setDouble(4, this.totalAmount);
            pstmt.setString(5, this.status);
            pstmt.executeUpdate();
        }
    }
    
    public void deleteFromDatabase() throws SQLException {
        Connection conn = ConnectMySQL.getConnection();
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.orderId);
            pstmt.executeUpdate();
        }
    }
}
    
