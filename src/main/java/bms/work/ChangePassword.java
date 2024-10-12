package bms.work;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ChangePassword {

    public void changePassword(String username) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = ConnectMySQL.getConnection();

        // Nhap mat khau cu
        System.out.print("Nhap mat khau cu: ");
        String oldPassword = sc.nextLine();

        // Kiem tra tai khoan voi username va mat khau cu
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM acc WHERE username = ? AND password = ?");
        stmt.setString(1, username);
        stmt.setString(2, oldPassword);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Neu mat khau cu dung, yeu cau nhap mat khau moi
            System.out.print("Nhap mat khau moi: ");
            String newPassword = sc.nextLine();

            System.out.print("Xac nhan lai mat khau moi: ");
            String confirmPassword = sc.nextLine();

            // Kiem tra xem mat khau moi va xac nhan mat khau co khop khong
            if (newPassword.equals(confirmPassword)) {
                // Cap nhat mat khau moi vao CSDL
                PreparedStatement updateStmt = con.prepareStatement("UPDATE acc SET password = ? WHERE username = ?");
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, username);

                // Thuc hien update mat khau
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Mat khau da duoc thay doi thanh cong.");
                } else {
                    System.out.println("Khong the thay doi mat khau. Vui long thu lai.");
                }
            } else {
                System.out.println("Mat khau moi khong khop. Vui long nhap lai.");
            }
        } else {
            System.out.println("Mat khau cu khong dung. Vui long thu lai.");
        }
    }
}
