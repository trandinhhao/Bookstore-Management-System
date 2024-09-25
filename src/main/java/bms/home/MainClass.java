package bms.home;

import bms.work.*;
import java.sql.*;
import java.util.*;

public class MainClass {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        // Tao giao dien dang nhap

        // Login
        Login log = new Login();
        String username = sc.nextLine(); // thay cai nay bang data nhap tu form
        String password = sc.nextLine();
        while (!log.checkLogin(username, password)) {
            // Dang nhap that bai, hien len 1 thong bao dang nhap lai
        }
        // Giao dien menu
    }
}
