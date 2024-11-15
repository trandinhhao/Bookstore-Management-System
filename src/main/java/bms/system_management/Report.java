package bms.system_management;

import bms.connectDB.ConnectMySQL;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Report {

    private int reportId;
    private String reportType;
    private Date generatedDate;
    private String content;

    public Report(int reportId, String reportType, Date generatedDate, String content) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.generatedDate = generatedDate;
        this.content = content;
    }

    public void addReport() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO reports (report_type, generated_date, content) VALUES (?, ?, ?)";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.reportType);
            pstmt.setDate(2, new java.sql.Date(this.generatedDate.getTime()));
            pstmt.setString(3, this.content);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.reportId = generatedKeys.getInt(1);
                }
            }
        }
    }

    public static Report getReportById(int reportId) throws SQLException, ClassNotFoundException {
        String sqlString = "SELECT * FROM WHERE report_id = ?";
        Connection conn = ConnectMySQL.getConnection();
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            pstmt.setInt(1, reportId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Report(
                            rs.getInt("report_id"),
                            rs.getString("report_type"),
                            rs.getDate("generated_date"),
                            rs.getString("content"));
                }
            }
        }
        return null;
    }

    public ArrayList<Report> getAllRepor() throws SQLException, ClassNotFoundException {
        ArrayList<Report> reports = new ArrayList<>();
        Connection conn = ConnectMySQL.getConnection();
        String sqlString = "SELECT * FROM reports ORDER BY generated_date DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlString)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reports.add(new Report(
                        rs.getInt("report_id"),
                        rs.getString("report_type"),
                        rs.getDate("generated_date"),
                        rs.getString("content")));
            }
        }
        return null;
    }

//    public static Report generateInventoryReport() throws SQLException, ClassNotFoundException {
//        StringBuilder content = new StringBuilder();
//        String sql = "SELECT product_name, quantity, unit_price FROM inventory";
//        Connection conn = ConnectMySQL.getConnection();
//        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
//            content.append("Inventory Report\n");
//            content.append("----------------\n");
//            content.append("Product Name | Quantity | Unit Price\n");
//            while (rs.next()) {
//                content.append(String.format("%-12s | %-8d | $%.2f\n",
//                        rs.getString("product_name"),
//                        rs.getInt("quantity"),
//                        rs.getDouble("unit_price")));
//            }
//        }
//
//        Report report = new Report(0, "Inventory", new Date(), content.toString());
//        report.addReport();
//        return report;
//    }

    public static Report generateSalesReport(Date startDate, Date endDate) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();
        StringBuilder content = new StringBuilder();
        String sql = "SELECT o.order_date, SUM(o.total_amount) as daily_total "
                + "FROM orders o "
                + "WHERE o.order_date BETWEEN ? AND ? "
                + "GROUP BY o.order_date "
                + "ORDER BY o.order_date";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
            try (ResultSet rs = pstmt.executeQuery()) {
                content.append("Sales Report\n");
                content.append("------------\n");
                content.append("Date | Total Sales\n");
                while (rs.next()) {
                    content.append(String.format("%s | $%.2f\n",
                            rs.getDate("order_date"),
                            rs.getDouble("daily_total")));
                }
            }
        }
        Report report = new Report(0, "Sales", new Date(), content.toString());
        report.addReport();
        return report;
    }
}
