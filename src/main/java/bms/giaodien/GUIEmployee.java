package bms.giaodien;

import bms.connectDB.ConnectMySQL;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.border.EmptyBorder;

public class GUIEmployee extends JPanel {

    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtBirth, txtAddress, txtPhone, txtEmail, txtPosition, txtPartFullTime, txtSearch;
    private JCheckBox chkId, chkName, chkAddress, chkPhone, chkEmail, chkPosition, chkPartFullTime;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    public GUIEmployee() {
        setLayout(new BorderLayout());

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý nhân viên");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Tạo khoảng cách giữa các ô
        gbc.fill = GridBagConstraints.HORIZONTAL; // Đặt các ô nhập liệu giãn theo chiều ngang

// Hàng 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã NV:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(txtId = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên NV:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        inputPanel.add(txtName = new JTextField(15), gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Ngày sinh:"), gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        inputPanel.add(txtBirth = new JTextField(15), gbc);

        gbc.gridx = 6;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Địa chỉ:"), gbc);

        gbc.gridx = 7;
        gbc.gridy = 0;
        inputPanel.add(txtAddress = new JTextField(15), gbc);

// Hàng 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Số ĐT:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(txtPhone = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        inputPanel.add(txtEmail = new JTextField(15), gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Vị trí:"), gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        inputPanel.add(txtPosition = new JTextField(15), gbc);

        gbc.gridx = 6;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Part/Full time:"), gbc);

        gbc.gridx = 7;
        gbc.gridy = 1;
        inputPanel.add(txtPartFullTime = new JTextField(15), gbc);

        topPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout(5, 5));

        JPanel searchFieldPanel = new JPanel(new FlowLayout());
        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");
        searchFieldPanel.add(new JLabel("Tìm kiếm:"));
        searchFieldPanel.add(txtSearch);
        searchFieldPanel.add(btnSearch);

        JPanel searchCheckboxPanel = new JPanel(new FlowLayout());
        chkId = new JCheckBox("Mã NV");
        chkName = new JCheckBox("Tên NV");
        chkAddress = new JCheckBox("Địa chỉ");
        chkPhone = new JCheckBox("Số ĐT");
        chkEmail = new JCheckBox("Email");
        chkPosition = new JCheckBox("Vị trí");
        chkPartFullTime = new JCheckBox("Part/Full time");

        searchCheckboxPanel.add(chkId);
        searchCheckboxPanel.add(chkName);
        searchCheckboxPanel.add(chkAddress);
        searchCheckboxPanel.add(chkPhone);
        searchCheckboxPanel.add(chkEmail);
        searchCheckboxPanel.add(chkPosition);
        searchCheckboxPanel.add(chkPartFullTime);

        searchPanel.add(searchFieldPanel, BorderLayout.NORTH);
        searchPanel.add(searchCheckboxPanel, BorderLayout.SOUTH);

        topPanel.add(searchPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Làm mới");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã NV", "Tên NV", "Ngày sinh", "Địa chỉ", "Số ĐT", "Email", "Vị trí", "Part/Full time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        employeeTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    txtId.setText(employeeTable.getValueAt(selectedRow, 0).toString());
                    txtName.setText(employeeTable.getValueAt(selectedRow, 1).toString());
                    txtBirth.setText(employeeTable.getValueAt(selectedRow, 2).toString());
                    txtAddress.setText(employeeTable.getValueAt(selectedRow, 3).toString());
                    txtPhone.setText(employeeTable.getValueAt(selectedRow, 4).toString());
                    txtEmail.setText(employeeTable.getValueAt(selectedRow, 5).toString());
                    txtPosition.setText(employeeTable.getValueAt(selectedRow, 6).toString());
                    txtPartFullTime.setText(employeeTable.getValueAt(selectedRow, 7).toString());
                }
            }
        });

        btnAdd.addActionListener(e -> addEmployee());
        btnEdit.addActionListener(e -> editEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnRefresh.addActionListener(e -> loadEmployeeData());
        btnSearch.addActionListener(e -> searchEmployee());

        loadEmployeeData();
    }

    private void loadEmployeeData() {
        tableModel.setRowCount(0);
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Employee"); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getString("birth"),
                    rs.getString("address"), rs.getString("phoneNumber"),
                    rs.getString("email"), rs.getString("position"), rs.getString("employmentType")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addEmployee() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
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

    private void editEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
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

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement("DELETE FROM Employee WHERE id = ?")) {
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

    private void searchEmployee() {
        String keyword = txtSearch.getText().trim();
        tableModel.setRowCount(0);

        StringBuilder query = new StringBuilder("SELECT * FROM Employee WHERE 1=1");
        if (!keyword.isEmpty()) {
            if (chkId.isSelected()) {
                query.append(" AND id LIKE ?");
            }
            if (chkName.isSelected()) {
                query.append(" AND name LIKE ?");
            }
            if (chkAddress.isSelected()) {
                query.append(" AND address LIKE ?");
            }
            if (chkPhone.isSelected()) {
                query.append(" AND phoneNumber LIKE ?");
            }
            if (chkEmail.isSelected()) {
                query.append(" AND email LIKE ?");
            }
            if (chkPosition.isSelected()) {
                query.append(" AND position LIKE ?");
            }
            if (chkPartFullTime.isSelected()) {
                query.append(" AND employmentType LIKE ?");
            }
        }

        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(query.toString())) {

            int paramIndex = 1;
            if (!keyword.isEmpty()) {
                if (chkId.isSelected()) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (chkName.isSelected()) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (chkAddress.isSelected()) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (chkPhone.isSelected()) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (chkEmail.isSelected()) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (chkPosition.isSelected()) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (chkPartFullTime.isSelected()) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getString("birth"),
                    rs.getString("address"), rs.getString("phoneNumber"),
                    rs.getString("email"), rs.getString("position"), rs.getString("employmentType")
                });
            }

            employeeTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer(keyword));
            employeeTable.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching employee: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class CustomTableCellRenderer extends DefaultTableCellRenderer {

        private final String keyword;

        public CustomTableCellRenderer(String keyword) {
            this.keyword = keyword.toLowerCase();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null && keyword != null && !keyword.isEmpty()) {
                String cellValue = value.toString().toLowerCase();
                if (cellValue.contains(keyword)) {
                    c.setBackground(Color.YELLOW);
                } else {
                    c.setBackground(Color.WHITE);
                }
            } else {
                c.setBackground(Color.WHITE);
            }
            return c;
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
