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

public class GUIProduct_Book extends JPanel {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtCostPrice, txtSalePrice, txtQuantity, txtUnit,
            txtOrigin, txtAuthor, txtPublisher, txtPublicationYear, txtGenre, txtLanguage;

    public GUIProduct_Book() {
        setLayout(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý sách");
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
        inputPanel.add(new JLabel("Mã sách:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(txtId = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên sách:"), gbc);

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
        inputPanel.add(new JLabel("Tác giả:"), gbc);

        gbc.gridx = 7;
        gbc.gridy = 1;
        inputPanel.add(txtAuthor = new JTextField(15), gbc);

        // Hàng 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Nhà xuất bản:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(txtPublisher = new JTextField(15), gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Năm xuất bản:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        inputPanel.add(txtPublicationYear = new JTextField(15), gbc);

        gbc.gridx = 4;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Thể loại:"), gbc);

        gbc.gridx = 5;
        gbc.gridy = 2;
        inputPanel.add(txtGenre = new JTextField(15), gbc);

        gbc.gridx = 6;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Ngôn ngữ:"), gbc);

        gbc.gridx = 7;
        gbc.gridy = 2;
        inputPanel.add(txtLanguage = new JTextField(15), gbc);

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

        String[] columnNames = {"Mã sách", "Tên sách", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị",
            "Xuất xứ", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "Thể loại", "Ngôn ngữ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addBook());
        btnEdit.addActionListener(e -> editBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnRefresh.addActionListener(e -> loadBookData());
        bookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = bookTable.getSelectedRow();
                    if (selectedRow != -1) {
                        txtId.setText(bookTable.getValueAt(selectedRow, 0).toString());
                        txtName.setText(bookTable.getValueAt(selectedRow, 1).toString());
                        txtCostPrice.setText(bookTable.getValueAt(selectedRow, 2).toString());
                        txtSalePrice.setText(bookTable.getValueAt(selectedRow, 3).toString());
                        txtQuantity.setText(bookTable.getValueAt(selectedRow, 4).toString());
                        txtUnit.setText(bookTable.getValueAt(selectedRow, 5).toString());
                        txtOrigin.setText(bookTable.getValueAt(selectedRow, 6).toString());
                        txtAuthor.setText(bookTable.getValueAt(selectedRow, 7).toString());
                        txtPublisher.setText(bookTable.getValueAt(selectedRow, 8).toString());
                        txtPublicationYear.setText(bookTable.getValueAt(selectedRow, 9).toString());
                        txtGenre.setText(bookTable.getValueAt(selectedRow, 10).toString());
                        txtLanguage.setText(bookTable.getValueAt(selectedRow, 11).toString());
                    }
                }
            }
        });

        loadBookData();
    }

    private void loadBookData() {
        tableModel.setRowCount(0);
        try (Connection con = ConnectMySQL.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Book")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"), rs.getInt("quantity"), rs.getString("unit"),
                    rs.getString("origin"), rs.getString("author"), rs.getString("publisher"),
                    rs.getInt("publicationYear"), rs.getString("genre"), rs.getString("language")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading book data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addBook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO Book (id, name, cost_price, sale_price, quantity, unit, origin, author, publisher, publicationYear, genre, language) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, txtId.getText());
            pstmt.setString(2, txtName.getText());
            pstmt.setDouble(3, Double.parseDouble(txtCostPrice.getText()));
            pstmt.setDouble(4, Double.parseDouble(txtSalePrice.getText()));
            pstmt.setInt(5, Integer.parseInt(txtQuantity.getText()));
            pstmt.setString(6, txtUnit.getText());
            pstmt.setString(7, txtOrigin.getText());
            pstmt.setString(8, txtAuthor.getText());
            pstmt.setString(9, txtPublisher.getText());
            pstmt.setInt(10, Integer.parseInt(txtPublicationYear.getText()));
            pstmt.setString(11, txtGenre.getText());
            pstmt.setString(12, txtLanguage.getText());
            pstmt.executeUpdate();
            loadBookData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editBook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "UPDATE Book SET name = ?, cost_price = ?, sale_price = ?, quantity = ?, unit = ?, origin = ?, author = ?, publisher = ?, publicationYear = ?, genre = ?, language = ? WHERE id = ?")) {
            pstmt.setString(1, txtName.getText());
            pstmt.setDouble(2, Double.parseDouble(txtCostPrice.getText()));
            pstmt.setDouble(3, Double.parseDouble(txtSalePrice.getText()));
            pstmt.setInt(4, Integer.parseInt(txtQuantity.getText()));
            pstmt.setString(5, txtUnit.getText());
            pstmt.setString(6, txtOrigin.getText());
            pstmt.setString(7, txtAuthor.getText());
            pstmt.setString(8, txtPublisher.getText());
            pstmt.setInt(9, Integer.parseInt(txtPublicationYear.getText()));
            pstmt.setString(10, txtGenre.getText());
            pstmt.setString(11, txtLanguage.getText());
            pstmt.setString(12, txtId.getText());
            pstmt.executeUpdate();
            loadBookData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error editing book: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "DELETE FROM Book WHERE id = ?")) {
            pstmt.setString(1, txtId.getText());
            pstmt.executeUpdate();
            loadBookData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting book: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // TEST
    public static void main(String[] args) {
        JFrame frame = new JFrame("TEST");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.add(new GUIProduct_Book());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
