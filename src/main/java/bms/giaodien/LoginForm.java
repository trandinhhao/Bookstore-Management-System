package bms.giaodien;

import bms.work.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginForm extends JFrame {

    public LoginForm() {
        // Cài đặt tiêu đề và kích thước cho cửa sổ
        setTitle("Hệ thống quản lý nhà sách");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Căn giữa cửa sổ
        setLayout(new BorderLayout());

        // Panel chính chứa tất cả thành phần
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new GridLayout(1, 2));  // Chia thành 2 cột

        // Panel trái chứa hình đại diện
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new BorderLayout());
        JLabel avatar = new JLabel(new ImageIcon("C:\\Users\\PC\\Desktop\\BMS\\BMS\\src\\main\\java\\bms\\giaodien\\login.png"));
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        panelLeft.add(avatar, BorderLayout.CENTER);

        // Panel phải chứa form đăng nhập
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new GridBagLayout());  // Sử dụng GridBagLayout để tùy chỉnh vị trí các thành phần
        panelRight.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề đăng nhập
        JLabel lblTitle = new JLabel("Đăng nhập");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panelRight.add(lblTitle, gbc);

        // Ô nhập Email
        JTextField txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Tài khoản"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelRight.add(txtEmail, gbc);

        // Ô nhập Password
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panelRight.add(txtPassword, gbc);

        // Nút Login
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setBackground(new Color(76, 175, 80));  // Màu xanh như trong hình
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelRight.add(btnLogin, gbc);

        // Dòng "Forgot Password"
        JLabel lblForgot = new JLabel("Quên mật khẩu?");
        lblForgot.setFont(new Font("Arial", Font.PLAIN, 12));
        lblForgot.setForeground(Color.GRAY);
        lblForgot.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelRight.add(lblForgot, gbc);

        // Dòng "Sign Up"
        JLabel lblSignUp = new JLabel("Don't have an account? Sign Up");
        lblSignUp.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSignUp.setForeground(Color.BLUE);
        lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
        lblSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panelRight.add(lblSignUp, gbc);

        // Thêm các panel vào giao diện chính
        panelMain.add(panelLeft);
        panelMain.add(panelRight);

        // Thêm panel chính vào cửa sổ
        add(panelMain);

        // Hành động cho nút Login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtEmail.getText();
                String password = new String(txtPassword.getPassword());

                // Xử lý đăng nhập (ví dụ)
                Login log = new Login();
                try {
                    if (log.checkLogin(username, password)) {
                        JOptionPane.showMessageDialog(null, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        SwingUtilities.invokeLater(() -> {
                            try {
                                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                                UIManager.put("Button.arc", 10);
                                UIManager.put("Component.arc", 10);
                                UIManager.put("TextComponent.arc", 10);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            new ProductManagementGUI().setVisible(true);
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Đăng nhập thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Hành động cho "Sign Up"
        lblSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterForm.openRegistrationForm();  // Mở form đăng ký
            }
        });
        
        // Hành động cho "Quên mk"
        lblForgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ForgotPasswordForm.openForgotPasswordForm();  // Mở form quên mk
            }
        });
    }

    // Hàm chạy giao diện
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginUI = new LoginForm();
            loginUI.setVisible(true);
        });
    }
}
