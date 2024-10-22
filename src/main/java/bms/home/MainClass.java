package bms.home;

import bms.giaodien.LoginForm;
import bms.giaodien.ProductManagementGUI;
import bms.work.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class MainClass {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //// Form
        SwingUtilities.invokeLater(() -> {
            LoginForm loginUI = new LoginForm();
            loginUI.setVisible(true);
        });
        // Giao dien menu
    }
}
