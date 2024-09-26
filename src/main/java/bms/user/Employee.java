package bms.user;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Employee extends Person{
    private String position;
    private String employmentType;

    public Employee(String position, String employmentType, String id, String name, String birth, String address, String phoneNumber, String email) {
        super(id, name, birth, address, phoneNumber, email);
        this.position = position;
        this.employmentType = employmentType;
    }
    
    public void addEmployee(String position, String employmentType, String id, String name, String birth, String address, String phoneNumber, String email) {
        try {
            Connection con = ConnectMySQL.getConnection();
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
            // Thong bao nhap du lieu k hop le, yeu cau nhap lai
        }
    }
}
