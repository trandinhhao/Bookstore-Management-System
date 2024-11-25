package bms.home;
// DONE

import bms.giaodien.LoginForm;
import java.sql.*;
import javax.swing.*;

public class MainClass {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Form
        SwingUtilities.invokeLater(() -> {
            LoginForm loginUI = new LoginForm();
            loginUI.setVisible(true);
        });
    }
}
