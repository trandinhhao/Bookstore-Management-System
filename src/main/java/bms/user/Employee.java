package bms.user;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Employee extends Person {

    private String position;
    private String employmentType;

    public Employee(String position, String employmentType, String id, String name, String birth, String address, String phoneNumber, String email) {
        super(id, name, birth, address, phoneNumber, email);
        this.position = position;
        this.employmentType = employmentType;
    }

    // Them mot nhan vien vao co so du lieu
    public void addEmployee(String position, String employmentType, String id, String name, String birth, String address, String phoneNumber, String email) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de them du lieu vao bang Employee
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Employee VALUES (?, ?, ?, ?, ? ,?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setString(3, birth);
            stmt.setString(4, address);
            stmt.setString(5, phoneNumber);
            stmt.setString(6, email);
            stmt.setString(7, position);
            stmt.setString(8, employmentType);

            // Bam xac nhan nhap
            int row = stmt.executeUpdate();
            if (row > 0) {
                // Thong bao nhap thanh cong, hien thi ra thong bao them thanh cong
            }
        } catch (Exception e) {
            // Thong bao nhap du lieu khong hop le, yeu cau nhap lai
        }
    }

    // Xoa mot nhan vien khoi co so du lieu
    public void deleteEmployee(String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de xoa du lieu trong bang Employee
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Employee WHERE id = ?");
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

    // Cap nhat thong tin mot nhan vien trong co so du lieu
    public void updateEmployee(String col, String val, String id) {
        try {
            Connection con = ConnectMySQL.getConnection();
            // Chuan bi cau lenh SQL de cap nhat du lieu trong bang Employee
            PreparedStatement stmt = con.prepareStatement("UPDATE Employee SET " + col + " = ? WHERE id = ?");
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
