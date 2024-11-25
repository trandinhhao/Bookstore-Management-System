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

public class GUIProduct_Notebook extends JPanel {

    private JTable notebookTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtCostPrice, txtSalePrice, txtQuantity, txtUnit,
            txtOrigin, txtPageCount, txtPaperType, txtSize, txtManufacturer;

    public GUIProduct_Notebook() {
        setLayout(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý sổ tay");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 16));
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        setBorder(titledBorder);

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã sổ tay:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(txtId = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên sổ tay:"), gbc);

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
        inputPanel.add(new JLabel("Số trang:"), gbc);

        gbc.gridx = 7;
        gbc.gridy = 1;
        inputPanel.add(txtPageCount = new JTextField(15), gbc);

        // Hàng 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Loại giấy:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(txtPaperType = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Kích thước:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        inputPanel.add(txtSize = new JTextField(15), gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Nhà sản xuất:"), gbc);

        gbc.gridx = 5;
        gbc.gridy = 2;
        inputPanel.add(txtManufacturer = new JTextField(15), gbc);

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

        String[] columnNames = {"Mã", "Tên", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị",
            "Xuất xứ", "Số trang", "Loại giấy", "Kích thước", "Nhà sản xuất"};
        tableModel = new DefaultTableModel(columnNames, 0);
        notebookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(notebookTable);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addNotebook());
        btnEdit.addActionListener(e -> editNotebook());
        btnDelete.addActionListener(e -> deleteNotebook());
        btnRefresh.addActionListener(e -> loadNotebookData());
        notebookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = notebookTable.getSelectedRow();
                    if (selectedRow != -1) {
                        txtId.setText(notebookTable.getValueAt(selectedRow, 0).toString());
                        txtName.setText(notebookTable.getValueAt(selectedRow, 1).toString());
                        txtCostPrice.setText(notebookTable.getValueAt(selectedRow, 2).toString());
                        txtSalePrice.setText(notebookTable.getValueAt(selectedRow, 3).toString());
                        txtQuantity.setText(notebookTable.getValueAt(selectedRow, 4).toString());
                        txtUnit.setText(notebookTable.getValueAt(selectedRow, 5).toString());
                        txtOrigin.setText(notebookTable.getValueAt(selectedRow, 6).toString());
                        txtPageCount.setText(notebookTable.getValueAt(selectedRow, 7).toString());
                        txtPaperType.setText(notebookTable.getValueAt(selectedRow, 8).toString());
                        txtSize.setText(notebookTable.getValueAt(selectedRow, 9).toString());
                        txtManufacturer.setText(notebookTable.getValueAt(selectedRow, 10).toString());
                    }
                }
            }
        });

        loadNotebookData();
    }

    private void loadNotebookData() {
        tableModel.setRowCount(0);
        try (Connection con = ConnectMySQL.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM notebook")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"), rs.getInt("quantity"), rs.getString("unit"),
                    rs.getString("origin"), rs.getInt("page_count"), rs.getString("paper_type"),
                    rs.getString("size"), rs.getString("manufacturer")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading notebook data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addNotebook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO notebook (id, name, cost_price, sale_price, quantity, unit, origin, page_count, paper_type, size, manufacturer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, txtId.getText());
            pstmt.setString(2, txtName.getText());
            pstmt.setDouble(3, Double.parseDouble(txtCostPrice.getText()));
            pstmt.setDouble(4, Double.parseDouble(txtSalePrice.getText()));
            pstmt.setInt(5, Integer.parseInt(txtQuantity.getText()));
            pstmt.setString(6, txtUnit.getText());
            pstmt.setString(7, txtOrigin.getText());
            pstmt.setInt(8, Integer.parseInt(txtPageCount.getText()));
            pstmt.setString(9, txtPaperType.getText());
            pstmt.setString(10, txtSize.getText());
            pstmt.setString(11, txtManufacturer.getText());
            pstmt.executeUpdate();
            loadNotebookData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding notebook: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editNotebook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "UPDATE notebook SET name = ?, cost_price = ?, sale_price = ?, quantity = ?, unit = ?, origin = ?, page_count = ?, paper_type = ?, size = ?, manufacturer = ? WHERE id = ?")) {
            pstmt.setString(1, txtName.getText());
            pstmt.setDouble(2, Double.parseDouble(txtCostPrice.getText()));
            pstmt.setDouble(3, Double.parseDouble(txtSalePrice.getText()));
            pstmt.setInt(4, Integer.parseInt(txtQuantity.getText()));
            pstmt.setString(5, txtUnit.getText());
            pstmt.setString(6, txtOrigin.getText());
            pstmt.setInt(7, Integer.parseInt(txtPageCount.getText()));
            pstmt.setString(8, txtPaperType.getText());
            pstmt.setString(9, txtSize.getText());
            pstmt.setString(10, txtManufacturer.getText());
            pstmt.setString(11, txtId.getText());
            pstmt.executeUpdate();
            loadNotebookData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating notebook: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteNotebook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "DELETE FROM notebook WHERE id = ?")) {
            pstmt.setString(1, txtId.getText());
            pstmt.executeUpdate();
            loadNotebookData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting notebook: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // TEST
    public static void main(String[] args) {
        JFrame frame = new JFrame("TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.add(new GUIProduct_Notebook());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
