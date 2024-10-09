package bms.user;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Customer extends Person {

    private int loyaltyPoints;
    private String registerDate;
    private String membershipTier;
    private int discount;

    public Customer(int loyaltyPoints, String registerDate, String membershipTier, int discount, String id, String name, String birth, String address, String phoneNumber, String email) {
        super(id, name, birth, address, phoneNumber, email);
        this.loyaltyPoints = loyaltyPoints;
        this.registerDate = registerDate;
        this.membershipTier = membershipTier;
        this.discount = discount;
    }

    // Them mot khach hang vao co so du lieu
    public void addCustomer(int loyaltyPoints, String registerDate, String membershipTier, int discount, String id, String name, String birth, String address, String phoneNumber, String email) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de them du lieu vao bang Customer
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, birth);
            stmt.setString(4, address);
            stmt.setString(5, phoneNumber);
            stmt.setString(6, email);
            stmt.setInt(7, loyaltyPoints);
            stmt.setString(8, registerDate);
            stmt.setString(9, membershipTier);
            stmt.setInt(10, discount);

            // Bam xac nhan nhap
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao nhap thanh cong, hien thi ra thong bao them thanh cong
            }
        } catch (Exception e) {
            // Thong bao nhap du lieu khong hop le, yeu cau nhap lai
        }
    }

    // Xoa mot khach hang khoi co so du lieu
    public void deleteCustomer(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de xoa du lieu trong bang Customer
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Customer WHERE id = ?");
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

    // Cap nhat thong tin mot khach hang trong co so du lieu
    public void updateCustomer(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de cap nhat du lieu trong bang Customer
            PreparedStatement stmt = con.prepareStatement("UPDATE Customer SET " + col + " = ? WHERE id = ?");
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
