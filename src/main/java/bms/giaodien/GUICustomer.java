package bms.giaodien;

import bms.connectDB.ConnectMySQL;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class GUICustomer extends JPanel {

    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtBirth, txtAddress, txtPhone, txtEmail, txtLoyaltyPoints, txtRegisterDate, txtMembershipTier, txtDiscount;

    public GUICustomer() {
        setLayout(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý khách hàng");
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
        inputPanel.add(new JLabel("Mã KH:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(txtId = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên KH:"), gbc);

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
        inputPanel.add(new JLabel("Điểm tích lũy:"), gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        inputPanel.add(txtLoyaltyPoints = new JTextField(15), gbc);

        gbc.gridx = 6;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Ngày đăng ký:"), gbc);

        gbc.gridx = 7;
        gbc.gridy = 1;
        inputPanel.add(txtRegisterDate = new JTextField(15), gbc);

// Hàng 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Hạng hội viên:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(txtMembershipTier = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Giảm giá:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        inputPanel.add(txtDiscount = new JTextField(15), gbc);

        topPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new BorderLayout());
        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm kiếm");
        searchInputPanel.add(new JLabel("Tìm kiếm:"));
        searchInputPanel.add(txtSearch);
        searchInputPanel.add(btnSearch);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JCheckBox chkId = new JCheckBox("Mã KH");
        JCheckBox chkName = new JCheckBox("Tên KH");
        JCheckBox chkAddress = new JCheckBox("Địa chỉ");
        JCheckBox chkPhone = new JCheckBox("Số ĐT");
        JCheckBox chkEmail = new JCheckBox("Email");
        checkBoxPanel.add(chkId);
        checkBoxPanel.add(chkName);
        checkBoxPanel.add(chkAddress);
        checkBoxPanel.add(chkPhone);
        checkBoxPanel.add(chkEmail);

        searchPanel.add(searchInputPanel, BorderLayout.NORTH);
        searchPanel.add(checkBoxPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Thêm");
        JButton btnEdit = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnRefresh = new JButton("Làm mới");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        JPanel searchAndButtonPanel = new JPanel(new BorderLayout());
        searchAndButtonPanel.add(searchPanel, BorderLayout.NORTH);
        searchAndButtonPanel.add(buttonPanel, BorderLayout.SOUTH);
        topPanel.add(searchAndButtonPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã KH", "Tên KH", "Ngày sinh", "Địa chỉ", "Số ĐT", "Email",
            "Điểm tích lũy", "Ngày đăng ký", "Hạng hội viên", "Giảm giá"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addCustomer());
        btnEdit.addActionListener(e -> editCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        btnRefresh.addActionListener(e -> loadCustomerData());
        btnSearch.addActionListener(e -> searchCustomer(txtSearch.getText(), chkId.isSelected(), chkName.isSelected(), chkAddress.isSelected(), chkPhone.isSelected(), chkEmail.isSelected()));
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Đảm bảo chỉ xử lý sự kiện cuối cùng
                    int selectedRow = customerTable.getSelectedRow();
                    if (selectedRow != -1) { // Kiểm tra xem có dòng nào được chọn không
                        txtId.setText(customerTable.getValueAt(selectedRow, 0).toString());
                        txtName.setText(customerTable.getValueAt(selectedRow, 1).toString());
                        txtBirth.setText(customerTable.getValueAt(selectedRow, 2).toString());
                        txtAddress.setText(customerTable.getValueAt(selectedRow, 3).toString());
                        txtPhone.setText(customerTable.getValueAt(selectedRow, 4).toString());
                        txtEmail.setText(customerTable.getValueAt(selectedRow, 5).toString());
                        txtLoyaltyPoints.setText(customerTable.getValueAt(selectedRow, 6).toString());
                        txtRegisterDate.setText(customerTable.getValueAt(selectedRow, 7).toString());
                        txtMembershipTier.setText(customerTable.getValueAt(selectedRow, 8).toString());
                        txtDiscount.setText(customerTable.getValueAt(selectedRow, 9).toString());
                    }
                }
            }
        });

        loadCustomerData();
    }

    private void loadCustomerData() {
        tableModel.setRowCount(0);
        try (Connection con = ConnectMySQL.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Customer")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getString("birth"),
                    rs.getString("address"), rs.getString("phone_number"), rs.getString("email"),
                    rs.getInt("loyalty_points"), rs.getString("register_date"),
                    rs.getString("membership_tier"), rs.getInt("discount")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading customer data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCustomer() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
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
            JOptionPane.showMessageDialog(this, "Lỗi thêm mới khách hàng: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
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
                JOptionPane.showMessageDialog(this, "Lỗi chỉnh sửa khách hàng: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa.");
        }
    }

    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement("DELETE FROM Customer WHERE id = ?")) {
                pstmt.setString(1, tableModel.getValueAt(selectedRow, 0).toString());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadCustomerData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa khách hàng: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa.");
        }
    }

    private void searchCustomer(String keyword, boolean searchById, boolean searchByName, boolean searchByAddress, boolean searchByPhone, boolean searchByEmail) {
        tableModel.setRowCount(0);
        StringBuilder query = new StringBuilder("SELECT * FROM Customer WHERE 1=1");
        if (!keyword.isEmpty()) {
            if (searchById) {
                query.append(" AND id LIKE ?");
            }
            if (searchByName) {
                query.append(" AND name LIKE ?");
            }
            if (searchByAddress) {
                query.append(" AND address LIKE ?");
            }
            if (searchByPhone) {
                query.append(" AND phone_number LIKE ?");
            }
            if (searchByEmail) {
                query.append(" AND email LIKE ?");
            }
        }
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (!keyword.isEmpty()) {
                if (searchById) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (searchByName) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (searchByAddress) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (searchByPhone) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (searchByEmail) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getString("birth"),
                    rs.getString("address"), rs.getString("phone_number"), rs.getString("email"),
                    rs.getInt("loyalty_points"), rs.getString("register_date"),
                    rs.getString("membership_tier"), rs.getInt("discount")
                });
            }
            customerTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer(keyword));
            customerTable.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching customer: " + e.getMessage(),
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
        JFrame frame = new JFrame("Quản lý khách hàng");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.add(new GUICustomer());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
