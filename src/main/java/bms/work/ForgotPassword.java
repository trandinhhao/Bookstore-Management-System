package bms.work;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ForgotPassword {

    public void resetPassword(String id, String username) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = ConnectMySQL.getConnection();

        // Kiem tra ton tai
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM acc WHERE id = ? AND username = ?");
        stmt.setString(1, id);
        stmt.setString(2, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Reset mat khau ve 12345
            String newPassword = "12345"; // Mat khau moi mac dinh
            PreparedStatement updateStmt = con.prepareStatement("UPDATE acc SET password = ? WHERE id = ? AND username = ?");
            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, id);
            updateStmt.setString(3, username);

            // Thuc hien update mat khau
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Thong bao rang mat khau duoc reset ve 12345
            } else {
                // Update mat khau khong thanh cong
            }
        } else {
            // Tai khoan khong ton tai
        }
    }
}
