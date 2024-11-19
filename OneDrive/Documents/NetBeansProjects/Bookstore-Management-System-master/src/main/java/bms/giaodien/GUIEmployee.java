package bms.giaodien;

import bms.connectDB.ConnectMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GUIEmployee extends JPanel {
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtBirth, txtAddress, txtPhone, txtEmail, txtPosition, txtPartFullTime;

    public GUIEmployee() {
        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel titleLabel = new JLabel("Danh sách nhân viên", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Panel chứa các nút và vùng nhập dữ liệu
        JPanel topPanel = new JPanel(new BorderLayout());

        // Panel vùng nhập dữ liệu
        JPanel inputPanel = new JPanel(new GridLayout(2, 8, 10, 10));
        txtId = new JTextField();
        txtName = new JTextField();
        txtBirth = new JTextField();
        txtAddress = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();
        txtPosition = new JTextField();
        txtPartFullTime = new JTextField();

        inputPanel.add(new JLabel("Mã NV:"));
        inputPanel.add(new JLabel("Tên NV:"));
        inputPanel.add(new JLabel("Ngày sinh:"));
        inputPanel.add(new JLabel("Địa chỉ:"));
        inputPanel.add(new JLabel("Số ĐT:"));
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(new JLabel("Vị trí:"));
        inputPanel.add(new JLabel("Part/Full time:"));

        inputPanel.add(txtId);
        inputPanel.add(txtName);
        inputPanel.add(txtBirth);
        inputPanel.add(txtAddress);
        inputPanel.add(txtPhone);
        inputPanel.add(txtEmail);
        inputPanel.add(txtPosition);
        inputPanel.add(txtPartFullTime);

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
        String[] columnNames = {"Mã NV", "Tên NV", "Ngày sinh", "Địa chỉ", "Số ĐT", "Email", "Vị trí", "Part/Full time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Thêm sự kiện cho các nút
        btnAdd.addActionListener(e -> addEmployee());
        btnEdit.addActionListener(e -> editEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnRefresh.addActionListener(e -> loadEmployeeData());

        // Tải dữ liệu lần đầu
        loadEmployeeData();
    }

    /**
     * Tải dữ liệu nhân viên từ cơ sở dữ liệu và hiển thị trong bảng
     */
    private void loadEmployeeData() {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        try (Connection con = ConnectMySQL.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Employee")) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String birth = rs.getString("birth");
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");
                String position = rs.getString("position");
                String employmentType = rs.getString("employmentType");

                tableModel.addRow(new Object[]{id, name, birth, address, phoneNumber, email, position, employmentType});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Thêm nhân viên mới
     */
    private void addEmployee() {
        try (Connection con = ConnectMySQL.getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                     "INSERT INTO Employee (id, name, birth, address, phoneNumber, email, position, employmentType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, txtId.getText());
            pstmt.setString(2, txtName.getText());
            pstmt.setString(3, txtBirth.getText());
            pstmt.setString(4, txtAddress.getText());
            pstmt.setString(5, txtPhone.getText());
            pstmt.setString(6, txtEmail.getText());
            pstmt.setString(7, txtPosition.getText());
            pstmt.setString(8, txtPartFullTime.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            loadEmployeeData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sửa thông tin nhân viên
     */
    private void editEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection con = ConnectMySQL.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(
                         "UPDATE Employee SET name = ?, birth = ?, address = ?, phoneNumber = ?, email = ?, position = ?, employmentType = ? WHERE id = ?")) {
                pstmt.setString(1, txtName.getText());
                pstmt.setString(2, txtBirth.getText());
                pstmt.setString(3, txtAddress.getText());
                pstmt.setString(4, txtPhone.getText());
                pstmt.setString(5, txtEmail.getText());
                pstmt.setString(6, txtPosition.getText());
                pstmt.setString(7, txtPartFullTime.getText());
                pstmt.setString(8, txtId.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadEmployeeData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating employee: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa.");
        }
    }

    /**
     * Xóa nhân viên
     */
    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection con = ConnectMySQL.getConnection();
                 PreparedStatement pstmt = con.prepareStatement("DELETE FROM Employee WHERE id = ?")) {
                pstmt.setString(1, tableModel.getValueAt(selectedRow, 0).toString());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadEmployeeData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting employee: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa.");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý nhân viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.add(new GUIEmployee());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
