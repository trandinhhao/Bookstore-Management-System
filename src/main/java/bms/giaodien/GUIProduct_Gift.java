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

public class GUIProduct_Gift extends JPanel {

    private JTable giftTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtCostPrice, txtSalePrice, txtQuantity, txtUnit,
            txtOrigin, txtType, txtMaterial;

    public GUIProduct_Gift() {
        setLayout(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý quà tặng");
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
        inputPanel.add(new JLabel("Mã quà tặng:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(txtId = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên quà tặng:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        inputPanel.add(txtName = new JTextField(15), gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Giá nhập:"), gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        inputPanel.add(txtCostPrice = new JTextField(15), gbc);

        gbc.gridx = 6;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Giá bán:"), gbc);

        gbc.gridx = 7;
        gbc.gridy = 0;
        inputPanel.add(txtSalePrice = new JTextField(15), gbc);

        // Hàng 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Số lượng:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(txtQuantity = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Đơn vị:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        inputPanel.add(txtUnit = new JTextField(15), gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Xuất xứ:"), gbc);

        gbc.gridx = 5;
        gbc.gridy = 1;
        inputPanel.add(txtOrigin = new JTextField(15), gbc);

        gbc.gridx = 6;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Loại quà:"), gbc);

        gbc.gridx = 7;
        gbc.gridy = 1;
        inputPanel.add(txtType = new JTextField(15), gbc);

        // Hàng 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Chất liệu:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(txtMaterial = new JTextField(15), gbc);

        topPanel.add(inputPanel, BorderLayout.CENTER);

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

        String[] columnNames = {"Mã quà", "Tên quà", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị",
            "Xuất xứ", "Loại quà", "Chất liệu"};
        tableModel = new DefaultTableModel(columnNames, 0);
        giftTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(giftTable);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addGift());
        btnEdit.addActionListener(e -> editGift());
        btnDelete.addActionListener(e -> deleteGift());
        btnRefresh.addActionListener(e -> loadGiftData());
        giftTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = giftTable.getSelectedRow();
                    if (selectedRow != -1) {
                        txtId.setText(giftTable.getValueAt(selectedRow, 0).toString());
                        txtName.setText(giftTable.getValueAt(selectedRow, 1).toString());
                        txtCostPrice.setText(giftTable.getValueAt(selectedRow, 2).toString());
                        txtSalePrice.setText(giftTable.getValueAt(selectedRow, 3).toString());
                        txtQuantity.setText(giftTable.getValueAt(selectedRow, 4).toString());
                        txtUnit.setText(giftTable.getValueAt(selectedRow, 5).toString());
                        txtOrigin.setText(giftTable.getValueAt(selectedRow, 6).toString());
                        txtType.setText(giftTable.getValueAt(selectedRow, 7).toString());
                        txtMaterial.setText(giftTable.getValueAt(selectedRow, 8).toString());
                    }
                }
            }
        });

        loadGiftData();
    }

    private void loadGiftData() {
        tableModel.setRowCount(0);
        try (Connection con = ConnectMySQL.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM gift")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"), rs.getInt("quantity"), rs.getString("unit"),
                    rs.getString("origin"), rs.getString("type"), rs.getString("material")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading gift data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addGift() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO gift (id, name, cost_price, sale_price, quantity, unit, origin, type, material) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, txtId.getText());
            pstmt.setString(2, txtName.getText());
            pstmt.setDouble(3, Double.parseDouble(txtCostPrice.getText()));
            pstmt.setDouble(4, Double.parseDouble(txtSalePrice.getText()));
            pstmt.setInt(5, Integer.parseInt(txtQuantity.getText()));
            pstmt.setString(6, txtUnit.getText());
            pstmt.setString(7, txtOrigin.getText());
            pstmt.setString(8, txtType.getText());
            pstmt.setString(9, txtMaterial.getText());
            pstmt.executeUpdate();
            loadGiftData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding gift: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editGift() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "UPDATE gift SET name = ?, cost_price = ?, sale_price = ?, quantity = ?, unit = ?, origin = ?, type = ?, material = ? WHERE id = ?")) {
            pstmt.setString(1, txtName.getText());
            pstmt.setDouble(2, Double.parseDouble(txtCostPrice.getText()));
            pstmt.setDouble(3, Double.parseDouble(txtSalePrice.getText()));
            pstmt.setInt(4, Integer.parseInt(txtQuantity.getText()));
            pstmt.setString(5, txtUnit.getText());
            pstmt.setString(6, txtOrigin.getText());
            pstmt.setString(7, txtType.getText());
            pstmt.setString(8, txtMaterial.getText());
            pstmt.setString(9, txtId.getText());
            pstmt.executeUpdate();
            loadGiftData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error editing gift: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteGift() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "DELETE FROM gift WHERE id = ?")) {
            pstmt.setString(1, txtId.getText());
            pstmt.executeUpdate();
            loadGiftData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting gift: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // TEST
    public static void main(String[] args) {
        JFrame frame = new JFrame("TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.add(new GUIProduct_Gift());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
