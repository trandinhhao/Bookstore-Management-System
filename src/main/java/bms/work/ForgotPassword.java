package bms.work;

import bms.connectDB.ConnectMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ForgotPassword {

    public int resetPassword(String id, String username, String newPassword) throws ClassNotFoundException, SQLException {
        Connection con = ConnectMySQL.getConnection();

        // Kiểm tra tài khoản tồn tại
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM acc WHERE id = ? AND username = ?");
        stmt.setString(1, id);
        stmt.setString(2, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Reset mật khẩu về mật khẩu mới
            PreparedStatement updateStmt = con.prepareStatement("UPDATE acc SET password = ? WHERE id = ? AND username = ?");
            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, id);
            updateStmt.setString(3, username);

            // Thực hiện update mật khẩu
            int rowsUpdated = updateStmt.executeUpdate();
            if (rowsUpdated > 0) {
                return 1; // Mật khẩu được reset thành công
            } else {
                return 2; // Không thể update mật khẩu
            }
        } else {
            return 3; // Tài khoản không tồn tại
        }
    }

}
