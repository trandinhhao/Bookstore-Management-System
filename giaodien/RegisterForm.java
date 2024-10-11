
package bms.giaodien;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class RegisterForm extends JFrame {
    public RegisterForm() {
        // Cài đặt tiêu đề và kích thước cho cửa sổ
        setTitle("User Registration");
        setSize(800, 550);  // Tăng chiều cao để chứa mô tả
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Đóng cửa sổ đăng ký nhưng không đóng toàn bộ ứng dụng
        setLocationRelativeTo(null);  // Căn giữa cửa sổ
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề đăng ký
        JLabel lblTitle = new JLabel("Sign Up");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Ô nhập tên
        JTextField txtUsername = new JTextField();
        txtUsername.setBorder(BorderFactory.createTitledBorder("Username"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(txtUsername, gbc);

        // Mô tả dưới Username
        JLabel lblUsernameDesc = new JLabel("Please enter a unique username.");
        lblUsernameDesc.setFont(new Font("Arial", Font.ITALIC, 12));
        lblUsernameDesc.setForeground(Color.GRAY);
        gbc.gridy = 2;
        add(lblUsernameDesc, gbc);

        // Ô nhập Email
        JTextField txtEmail = new JTextField();
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(txtEmail, gbc);

        // Mô tả dưới Email
        JLabel lblEmailDesc = new JLabel("Use a valid email address.");
        lblEmailDesc.setFont(new Font("Arial", Font.ITALIC, 12));
        lblEmailDesc.setForeground(Color.GRAY);
        gbc.gridy = 4;
        add(lblEmailDesc, gbc);

        // Ô nhập Password
        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(txtPassword, gbc);

        // Mô tả dưới Password
        JLabel lblPasswordDesc = new JLabel("Password should be at least 8 characters.");
        lblPasswordDesc.setFont(new Font("Arial", Font.ITALIC, 12));
        lblPasswordDesc.setForeground(Color.GRAY);
        gbc.gridy = 6;
        add(lblPasswordDesc, gbc);

        // Ô nhập Confirm Password
        JPasswordField txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        add(txtConfirmPassword, gbc);

        // Mô tả dưới Confirm Password
        JLabel lblConfirmPasswordDesc = new JLabel("Re-enter the password to confirm.");
        lblConfirmPasswordDesc.setFont(new Font("Arial", Font.ITALIC, 12));
        lblConfirmPasswordDesc.setForeground(Color.GRAY);
        gbc.gridy = 8;
        add(lblConfirmPasswordDesc, gbc);

        // Nút Đăng ký
        JButton btnRegister = new JButton("Register");
        btnRegister.setBackground(new Color(33, 150, 243));  // Màu xanh lam
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        add(btnRegister, gbc);

        // Hành động khi nhấn nút Đăng ký
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String email = txtEmail.getText();
                String password = new String(txtPassword.getPassword());
                String confirmPassword = new String(txtConfirmPassword.getPassword());

                // Kiểm tra nếu password và confirmPassword khớp
                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Xử lý đăng ký (ví dụ)
                JOptionPane.showMessageDialog(null, "User " + username + " registered successfully!");
                // Ở đây bạn có thể thêm mã để lưu dữ liệu đăng ký vào cơ sở dữ liệu
            }
        });
    }

    // Hàm chạy giao diện đăng ký
    public static void openRegistrationForm() {
        SwingUtilities.invokeLater(() -> {
            RegisterForm registerUI = new RegisterForm();
            registerUI.setVisible(true);
        });
    }
}

