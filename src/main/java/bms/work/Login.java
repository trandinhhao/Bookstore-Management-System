package bms.work;

import bms.connectDB.ConnectMySQL;
import java.sql.*;
import java.util.*;

public class Login {
    
    public boolean checkLogin(String username, String password) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = ConnectMySQL.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM acc WHERE username = ? AND password = ?");
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return true;
        } else {
            return false;
        }
    }
}
