package bms.giaodien;

import bms.work.ForgotPassword;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ForgotPasswordForm extends JFrame {

    public ForgotPasswordForm() {
        setTitle("Hệ thống quản lý nhà sách");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel lblTitle = new JLabel("Đặt lại mật khẩu");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Ô nhập ID
        JTextField txtId = new JTextField();
        txtId.setBorder(BorderFactory.createTitledBorder("ID"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(txtId, gbc);

        // Ô nhập Username
        JTextField txtUsername = new JTextField();
        txtUsername.setBorder(BorderFactory.createTitledBorder("Tài khoản"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(txtUsername, gbc);

        // Ô nhập Password mới
        JPasswordField txtNewPassword = new JPasswordField();
        txtNewPassword.setBorder(BorderFactory.createTitledBorder("Mật khẩu mới"));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(txtNewPassword, gbc);

        // Ô xác nhận Password mới
        JPasswordField txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBorder(BorderFactory.createTitledBorder("Xác nhận mật khẩu"));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(txtConfirmPassword, gbc);

        // Nút Reset Password
        JButton btnResetPassword = new JButton("Đặt lại mật khẩu");
        btnResetPassword.setBackground(new Color(33, 150, 243));  // Màu xanh lam
        btnResetPassword.setForeground(Color.WHITE);
        btnResetPassword.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(btnResetPassword, gbc);

        // Hành động khi nhấn nút Reset Password
        btnResetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                String username = txtUsername.getText();
                String newPassword = new String(txtNewPassword.getPassword());
                String confirmPassword = new String(txtConfirmPassword.getPassword());

                // Kiểm tra mật khẩu mới và mật khẩu xác nhận có khớp không
                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu xác nhận không khớp!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Gọi hàm resetPassword để thực hiện việc đặt lại mật khẩu
                ForgotPassword forgotPassword = new ForgotPassword();
                try {
                    int result = forgotPassword.resetPassword(id, username, newPassword);
                    if (result == 1) {
                        JOptionPane.showMessageDialog(null, "Đặt mật khẩu mới thành công!");
                    } else if (result == 2) {
                        JOptionPane.showMessageDialog(null, "Đặt lại thất bại, vui lòng thử lại.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tồn tại tài khoản, vui lòng thử lại!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void openForgotPasswordForm() {
        SwingUtilities.invokeLater(() -> {
            ForgotPasswordForm forgotPasswordUI = new ForgotPasswordForm();
            forgotPasswordUI.setVisible(true);
        });
    }
}
