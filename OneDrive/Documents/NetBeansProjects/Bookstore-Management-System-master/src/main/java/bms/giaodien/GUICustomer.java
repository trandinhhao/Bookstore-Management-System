package bms.giaodien;

import bms.connectDB.ConnectMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GUICustomer extends JPanel {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtBirth, txtAddress, txtPhone, txtEmail, txtLoyaltyPoints, txtRegisterDate, txtMembershipTier, txtDiscount;

    public GUICustomer() {
        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel titleLabel = new JLabel("Danh sách khách hàng", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Panel chứa các nút và vùng nhập dữ liệu
        JPanel topPanel = new JPanel(new BorderLayout());

        // Panel vùng nhập dữ liệu
        JPanel inputPanel = new JPanel(new GridLayout(2, 10, 10, 10));
        txtId = new JTextField();
        txtName = new JTextField();
        txtBirth = new JTextField();
        txtAddress = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtLoyaltyPoints = new JTextField();
        txtRegisterDate = new JTextField();
        txtMembershipTier = new JTextField();
        txtDiscount = new JTextField();

        inputPanel.add(new JLabel("Mã KH:"));
        inputPanel.add(new JLabel("Tên KH:"));
        inputPanel.add(new JLabel("Ngày sinh:"));
        inputPanel.add(new JLabel("Địa chỉ:"));
        inputPanel.add(new JLabel("Số ĐT:"));
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(new JLabel("Điểm tích lũy:"));
        inputPanel.add(new JLabel("Ngày đăng ký:"));
        inputPanel.add(new JLabel("Hạng hội viên:"));
        inputPanel.add(new JLabel("Giảm giá:"));

        inputPanel.add(txtId);
        inputPanel.add(txtName);
        inputPanel.add(txtBirth);
        inputPanel.add(txtAddress);
        inputPanel.add(txtPhone);
        inputPanel.add(txtEmail);
        inputPanel.add(txtLoyaltyPoints);
        inputPanel.add(txtRegisterDate);
        inputPanel.add(txtMembershipTier);
        inputPanel.add(txtDiscount);

        topPanel.add(inputPanel, BorderLayout.CENTER);

        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Tiêu đề cột bảng
        String[] columnNames = {"Mã KH", "Tên KH", "Ngày sinh", "Địa chỉ", "Số ĐT", "Email",
                "Điểm tích lũy", "Ngày đăng ký", "Hạng hội viên", "GIảm giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        // Thêm sự kiện cho các nút
        btnAdd.addActionListener(e -> addCustomer());
        btnEdit.addActionListener(e -> editCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnRefresh.addActionListener(e -> loadCustomerData());

        // Tải dữ liệu lần đầu
        loadCustomerData();
    }

    /**
     * Tải dữ liệu khách hàng từ cơ sở dữ liệu và hiển thị trong bảng
     */
    private void loadCustomerData() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        try (Connection con = ConnectMySQL.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Customer")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String birth = rs.getString("birth");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");
                int loyaltyPoints = rs.getInt("loyalty_points");
                String registerDate = rs.getString("register_date");
                String membershipTier = rs.getString("membership_tier");
                int discount = rs.getInt("discount");

                tableModel.addRow(new Object[]{id, name, birth, address, phoneNumber, email,
                        loyaltyPoints, registerDate, membershipTier, discount});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading customer data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Thêm khách hàng mới
     */
    private void addCustomer() {
        try (Connection con = ConnectMySQL.getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                     "INSERT INTO Customer (id, name, birth, address, phone_number, email, loyalty_points, register_date, membership_tier, discount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, txtId.getText());
            pstmt.setString(2, txtName.getText());
            pstmt.setString(3, txtBirth.getText());
            pstmt.setString(4, txtAddress.getText());
            pstmt.setString(5, txtPhone.getText());
            pstmt.setString(6, txtEmail.getText());
            pstmt.setInt(7, Integer.parseInt(txtLoyaltyPoints.getText()));
            pstmt.setString(8, txtRegisterDate.getText());
            pstmt.setString(9, txtMembershipTier.getText());
            pstmt.setInt(10, Integer.parseInt(txtDiscount.getText()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
            loadCustomerData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sửa thông tin khách hàng
     */
    private void editCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection con = ConnectMySQL.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(
                         "UPDATE Customer SET name = ?, birth = ?, address = ?, phone_number = ?, email = ?, loyalty_points = ?, register_date = ?, membership_tier = ?, discount = ? WHERE id = ?")) {
                pstmt.setString(1, txtName.getText());
                pstmt.setString(2, txtBirth.getText());
                pstmt.setString(3, txtAddress.getText());
                pstmt.setString(4, txtPhone.getText());
                pstmt.setString(5, txtEmail.getText());
                pstmt.setInt(6, Integer.parseInt(txtLoyaltyPoints.getText()));
                pstmt.setString(7, txtRegisterDate.getText());
                pstmt.setString(8, txtMembershipTier.getText());
                pstmt.setInt(9, Integer.parseInt(txtDiscount.getText()));
                pstmt.setString(10, txtId.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadCustomerData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating customer: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa.");
        }
    }

    /**
     * Xóa khách hàng
     */
    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection con = ConnectMySQL.getConnection();
                 PreparedStatement pstmt = con.prepareStatement("DELETE FROM Customer WHERE id = ?")) {
                pstmt.setString(1, tableModel.getValueAt(selectedRow, 0).toString());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadCustomerData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting customer: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa.");
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý khách hàng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.add(new GUICustomer());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
