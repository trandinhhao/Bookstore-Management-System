package bms.system_management;
// DONE

import bms.connectDB.ConnectMySQL;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Stats {

    private String reportId;
    private String reportType;
    private Date generatedDate;
    private String content;

    public Stats(String reportType, Date generatedDate, String content) {
        this.reportType = reportType;
        this.generatedDate = generatedDate;
        this.content = content;
    }

    public void addReport() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO reports (report_id, report_type, generated_date, content) VALUES (?, ?, ?, ?)";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            this.reportId = UUID.randomUUID().toString();
            pstmt.setString(1, this.reportId);
            pstmt.setString(2, this.reportType);
            pstmt.setDate(3, new java.sql.Date(this.generatedDate.getTime()));
            pstmt.setString(4, this.content);
            pstmt.executeUpdate();

        }
    }

//    public static Stats generateAdvancedSalesReport(Date startDate, Date endDate) throws SQLException, ClassNotFoundException {
//        Connection conn = ConnectMySQL.getConnection();
//        StringBuilder content = new StringBuilder();
//        String sql = "SELECT o.order_date, SUM(o.total_amount) as daily_total "
//                + "FROM orders o "
//                + "WHERE o.order_date BETWEEN ? AND ? "
//                + "GROUP BY o.order_date "
//                + "ORDER BY o.order_date";
//
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
//            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
//            try (ResultSet rs = pstmt.executeQuery()) {
//                content.append("Sales Report\n");
//                content.append("------------\n");
//                content.append("Date | Total Sales\n");
//                while (rs.next()) {
//                    content.append(String.format("%s | $%.2f\n",
//                            rs.getDate("order_date"),
//                            rs.getDouble("daily_total")));
//                }
//            }
//        }
//        Stats report = new Stats(0, "Sales", new Date(), content.toString());
//        report.addReport();
//        return report;
//    }
    // Bổ sung thêm vào file .java
    public static Stats generateAdvancedSalesReport(Date startDate, Date endDate, String productType) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();
        StringBuilder content = new StringBuilder();

        // 1. Tổng doanh thu
        String totalSql = "SELECT SUM(total_price) as total_revenue "
                + "FROM orders "
                + "WHERE order_date BETWEEN ? AND ?";

        // 2. Top 5 sản phẩm bán chạy nhất
        String topProductsSql = String.format("SELECT p.id, p.name, p.sale_price, SUM(o.total_amount) as total_sold "
                + "FROM orders o "
                + "INNER JOIN %s p ON o.product_id = p.id "
                + "WHERE o.order_date BETWEEN ? AND ? "
                + "GROUP BY p.id, p.name, p.sale_price "
                + "ORDER BY total_sold DESC "
                + "LIMIT 5;", productType);

        // 3. 5 sản phẩm bán ít nhất
        String bottomProductsSql = String.format("SELECT p.id, p.name, p.sale_price, SUM(o.total_amount) as total_sold "
                + "FROM orders o "
                + "INNER JOIN %s p ON o.product_id = p.id "
                + "WHERE o.order_date BETWEEN ? AND ? "
                + "GROUP BY p.id, p.name, p.sale_price "
                + "ORDER BY total_sold ASC "
                + "LIMIT 5;", productType);

        try {
            // Lấy tổng doanh thu
            try (PreparedStatement pstmt = conn.prepareStatement(totalSql)) {
                pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
                pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
                ResultSet rs = pstmt.executeQuery();
                content.append("THỐNG KÊ BÁO CÁO DOANH THU\n");
                content.append("===========================\n\n");
                content.append(String.format("Từ ngày: %s\n", new SimpleDateFormat("dd/MM/yyyy").format(startDate)));
                content.append(String.format("Đến ngày: %s\n\n", new SimpleDateFormat("dd/MM/yyyy").format(endDate)));

                if (rs.next()) {
                    content.append(String.format("TỔNG DOANH THU: $%.2f\n", rs.getDouble("total_revenue")));
                }
            }

            // Lấy top 5 sản phẩm bán chạy
            content.append("\nTOP 5 SẢN PHẨM BÁN CHẠY NHẤT\n");
            content.append("============================\n");
            content.append(String.format("%-30s %-30s %-30s  %-10s\n", "Id sản phẩm", "Tên sản phẩm", "Giá sản phẩm", "Số lượng"));
            content.append("------------------------------------------------\n");

            try (PreparedStatement pstmt = conn.prepareStatement(topProductsSql)) {
                pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
                pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    content.append(String.format(" %-30s %-30s %-30s %-10d\n",
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("sale_price"),
                            rs.getInt("total_sold")));
                }
            }

            // Lấy 5 sản phẩm bán ít nhất
            content.append("\n5 SẢN PHẨM BÁN CHẬM NHẤT\n");
            content.append("=========================\n");
            content.append(String.format("%-30s %-30s %-30s %-10s\n", "Id sản phẩm", "Tên sản phẩm", "Giá sản phẩm", "Số lượng"));
            content.append("------------------------------------------------\n");

            try (PreparedStatement pstmt = conn.prepareStatement(bottomProductsSql)) {
                pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
                pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    content.append(String.format("%-30s %-30s %-30s %-10d\n",
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("sale_price"),
                            rs.getInt("total_sold")));
                }
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        Stats report = new Stats("Advanced Sales Report", new Date(), content.toString());
        report.addReport();
        return report;
    }

    public String getContent() {
        return this.content;
    }
}
