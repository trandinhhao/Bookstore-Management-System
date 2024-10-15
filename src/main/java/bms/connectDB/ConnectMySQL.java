package bms.connectDB;

import java.sql.*;

public class ConnectMySQL {
    private static String url = "jdbc:mysql://localhost:3306/bms?autoReconnect=true&useSSL=false";
    private static String username = "root";
    private static String password = "123456";

    public static Connection getConnection() throws SQLException {
        //Nap Driver
        return DriverManager.getConnection(url, username, password);
    }

//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Connection con = getConnection();
//        if (con != null) {
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM Customers LIMIT 10");
//            int row = stmt.executeUpdate("INSERT INTO Customers VALUES(1999, 'Nguyen Van Nam', 'Nam', 'Hai Duong', 'HaiDuong', '2008', 'VN');");
//            System.out.println(row);
//        } else {
//            System.out.println("Khong ket noi duoc toi CSDL");
//        }
//    }
}
