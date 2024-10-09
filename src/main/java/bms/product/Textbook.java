package bms.product;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Textbook extends Book {
    private String subject;
    private int grade;
    private String eduLevel;

    public Textbook(String subject, int grade, String eduLevel, String author, String publisher, int publicationYear, String genre, String language, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(author, publisher, publicationYear, genre, language, id, name, costPrice, salePrice, quantity, unit, origin);
        this.subject = subject;
        this.grade = grade;
        this.eduLevel = eduLevel;
    }

    // Them mot sach giao khoa vao co so du lieu
    public void addTextbook(String subject, int grade, String eduLevel, String author, String publisher, int publicationYear, String genre, String language, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de them du lieu vao bang Textbook
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Textbook VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            stmt.setString(13, subject);
            stmt.setInt(14, grade);
            stmt.setString(15, eduLevel);

            // Bam xac nhan nhap
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao nhap thanh cong, hien thi ra thong bao them thanh cong
            }
        } catch (Exception e) {
            // Thong bao nhap du lieu khong hop le, yeu cau nhap lai
        }
    }

    // Xoa mot sach giao khoa khoi co so du lieu
    public void deleteTextbook(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de xoa du lieu trong bang Textbook
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Textbook WHERE id = ?");
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

    // Cap nhat thong tin mot sach giao khoa trong co so du lieu
    public void updateTextbook(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de cap nhat du lieu trong bang Textbook
            PreparedStatement stmt = con.prepareStatement("UPDATE Textbook SET " + col + " = ? WHERE id = ?");
            stmt.setString(1, val);
            stmt.setString(2, id);

            // Bam xac nhan cap nhat
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao cap nhat thanh cong, hien thi ra thong bao cap nhat thanh cong
            }
        } catch (Exception e) {
            // Thong bao cap nhat khong thanh cong, yeu cau nhap lai thong tin
        }
    }
}
