package bms.product;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book extends Product {

    protected String author;
    protected String publisher;
    protected int publicationYear;
    protected String genre;
    protected String language;

    public Book(String author, String publisher, int publicationYear, String genre, String language, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.language = language;
    }

    public void addBook(double costPrice, double salePrice, String author, String publisher, int publicationYear, String genre, String language, String id, String name, int quantity, String unit, String origin) {
        try {
            Connection con = ConnectMySQL.getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Book VALUES (?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ? ,?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, costPrice);
            stmt.setDouble(4, salePrice);
            stmt.setInt(5, quantity);
            stmt.setString(6, unit);
            stmt.setString(7, origin);
            stmt.setString(8, author);
            stmt.setString(9, publisher);
            stmt.setInt(10, publicationYear);
            stmt.setString(11, genre);
            stmt.setString(12, language);
            // Bam xac nhan nhap
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao nhap thanh cong, hien thi ra thong bao them thanh cong
            }
        } catch (Exception e) {
            // Thong bao nhap du lieu k hop le, yeu cau nhap lai
        }
    }

    public void deleteBook(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Book WHERE id = ?");
            stmt.setString(1, id);
            // Bam xac nhan xoa
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao da xoa thanh cong, hien thi ra thong bao xoa thanh cong
            }
        } catch (Exception e) {
            // Thong bao xoa khong thanh cong, yeu cau nhap lai id
        }
    }

    public void updateBook(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE Book SET " + col + " = ? WHERE id = ?");
            stmt.setString(1, val);
            stmt.setString(2, id);
            // Bam xac nhan update
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao da xoa thanh cong, hien thi ra thong bao xoa thanh cong
            }
        } catch (Exception e) {
            // Thong bao xoa khong thanh cong, yeu cau nhap lai id
        }
    }
    
    public static Book getProductById(String productId) throws SQLException, ClassNotFoundException {
        String sqlString = "SELECT * FROM book WHERE id= ?";
        Connection con = ConnectMySQL.getConnection();
        try(PreparedStatement stmt = con.prepareStatement(sqlString)){
            stmt.setString(1, productId);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    return new Book(
                            rs.getString("author"),
                            rs.getString("publisher"),
                            rs.getInt("publicationYear"),
                            rs.getString("genre"),
                            rs.getString("language"),
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("cost_price"),
                            rs.getDouble("sale_price"),
                            rs.getInt("quantity"),
                            rs.getString("unit"),
                            rs.getString("origin")
                    );
                }
            }
        }
        return null;
    }
}
