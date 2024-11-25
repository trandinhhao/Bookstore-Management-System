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
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class GUIProduct_Textbook extends JPanel {

    private JTable textbookTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtCostPrice, txtSalePrice, txtQuantity, txtUnit,
            txtOrigin, txtAuthor, txtPublisher, txtPublicationYear, txtGenre,
            txtLanguage, txtSubject, txtGrade, txtEduLevel, txtSearch;
    private JCheckBox cbBookId, cbBookName, cbBookAuthor;

    public GUIProduct_Textbook() {
        setLayout(new BorderLayout());
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Quản lý sách giáo khoa");
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
        inputPanel.add(new JLabel("Mã sách:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(txtId = new JTextField(15), gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Tên sách:"), gbc);

        gbc.gridx = 3;
        inputPanel.add(txtName = new JTextField(15), gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Giá nhập:"), gbc);

        gbc.gridx = 5;
        inputPanel.add(txtCostPrice = new JTextField(15), gbc);

        gbc.gridx = 6;
        inputPanel.add(new JLabel("Giá bán:"), gbc);

        gbc.gridx = 7;
        inputPanel.add(txtSalePrice = new JTextField(15), gbc);

        // Hàng 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Số lượng:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(txtQuantity = new JTextField(15), gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Đơn vị:"), gbc);

        gbc.gridx = 3;
        inputPanel.add(txtUnit = new JTextField(15), gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Xuất xứ:"), gbc);

        gbc.gridx = 5;
        inputPanel.add(txtOrigin = new JTextField(15), gbc);

        gbc.gridx = 6;
        inputPanel.add(new JLabel("Tác giả:"), gbc);

        gbc.gridx = 7;
        inputPanel.add(txtAuthor = new JTextField(15), gbc);

        // Hàng 3
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Nhà xuất bản:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(txtPublisher = new JTextField(15), gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Năm xuất bản:"), gbc);

        gbc.gridx = 3;
        inputPanel.add(txtPublicationYear = new JTextField(15), gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Thể loại:"), gbc);

        gbc.gridx = 5;
        inputPanel.add(txtGenre = new JTextField(15), gbc);

        gbc.gridx = 6;
        inputPanel.add(new JLabel("Ngôn ngữ:"), gbc);

        gbc.gridx = 7;
        inputPanel.add(txtLanguage = new JTextField(15), gbc);

        // Hàng 4
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Môn học:"), gbc);

        gbc.gridx = 1;
        inputPanel.add(txtSubject = new JTextField(15), gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Khối lớp:"), gbc);

        gbc.gridx = 3;
        inputPanel.add(txtGrade = new JTextField(15), gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Cấp học:"), gbc);

        gbc.gridx = 5;
        inputPanel.add(txtEduLevel = new JTextField(15), gbc);

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
        // Thêm panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Tìm kiếm:"));
        txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);
        JButton btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(btnSearch);
        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cbBookId = new JCheckBox("Mã sách");
        cbBookName = new JCheckBox("Tên sách");
        cbBookAuthor = new JCheckBox("Tác giả");
        checkBoxPanel.add(cbBookId);
        checkBoxPanel.add(cbBookName);
        checkBoxPanel.add(cbBookAuthor);
        searchPanel.add(checkBoxPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã", "Tên", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị",
            "Xuất xứ", "Tác giả", "Nhà xuất bản", "Năm xuất bản", "Thể loại", "Ngôn ngữ",
            "Môn học", "Khối lớp", "Cấp học"};
        btnSearch.addActionListener(e -> searchBooks(txtSearch.getText()));
        tableModel = new DefaultTableModel(columnNames, 0);
        textbookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(textbookTable);
        add(scrollPane, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addTextbook());
        btnEdit.addActionListener(e -> editTextbook());
        btnDelete.addActionListener(e -> deleteTextbook());
        btnRefresh.addActionListener(e -> loadTextbookData());
        textbookTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = textbookTable.getSelectedRow();
                    if (selectedRow != -1) {
                        txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                        txtName.setText(tableModel.getValueAt(selectedRow, 1).toString());
                        txtCostPrice.setText(tableModel.getValueAt(selectedRow, 2).toString());
                        txtSalePrice.setText(tableModel.getValueAt(selectedRow, 3).toString());
                        txtQuantity.setText(tableModel.getValueAt(selectedRow, 4).toString());
                        txtUnit.setText(tableModel.getValueAt(selectedRow, 5).toString());
                        txtOrigin.setText(tableModel.getValueAt(selectedRow, 6).toString());
                        txtAuthor.setText(tableModel.getValueAt(selectedRow, 7).toString());
                        txtPublisher.setText(tableModel.getValueAt(selectedRow, 8).toString());
                        txtPublicationYear.setText(tableModel.getValueAt(selectedRow, 9).toString());
                        txtGenre.setText(tableModel.getValueAt(selectedRow, 10).toString());
                        txtLanguage.setText(tableModel.getValueAt(selectedRow, 11).toString());
                        txtSubject.setText(tableModel.getValueAt(selectedRow, 12).toString());
                        txtGrade.setText(tableModel.getValueAt(selectedRow, 13).toString());
                        txtEduLevel.setText(tableModel.getValueAt(selectedRow, 14).toString());
                    }
                }
            }
        });

        loadTextbookData();
    }

    private void loadTextbookData() {
        tableModel.setRowCount(0);
        try (Connection con = ConnectMySQL.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM textbook")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"), rs.getInt("quantity"), rs.getString("unit"),
                    rs.getString("origin"), rs.getString("author"), rs.getString("publisher"),
                    rs.getInt("publication_year"), rs.getString("genre"), rs.getString("language"),
                    rs.getString("subject"), rs.getInt("grade"), rs.getString("edu_level")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading textbook data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchBooks(String keyword) {
        tableModel.setRowCount(0);  // Xóa dữ liệu hiện tại trên bảng

        // Bắt đầu câu lệnh SQL
        StringBuilder query = new StringBuilder("SELECT * FROM textbook WHERE 1=1");
        java.util.List<String> selectedCriteria = new ArrayList<>();

        // Kiểm tra các checkbox và thêm vào danh sách điều kiện
        if (cbBookId.isSelected()) {
            query.append(" AND id LIKE ?");
            selectedCriteria.add("Mã sách");
        }
        if (cbBookName.isSelected()) {
            query.append(" AND name LIKE ?");
            selectedCriteria.add("Tên sách");
        }
        if (cbBookAuthor.isSelected()) {
            query.append(" AND author LIKE ?");
            selectedCriteria.add("Tác giả");
        }

        if (selectedCriteria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một tiêu chí tìm kiếm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Thực thi câu truy vấn với các tiêu chí đã chọn
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(query.toString())) {
            // Thiết lập các tham số trong PreparedStatement
            int paramIndex = 1;
            for (String criterion : selectedCriteria) {
                pstmt.setString(paramIndex++, "%" + keyword + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"), rs.getInt("quantity"), rs.getString("unit"),
                    rs.getString("origin"), rs.getString("author"), rs.getString("publisher"),
                    rs.getInt("publication_year"), rs.getString("genre"), rs.getString("language"), rs.getString("subject"), rs.getInt("grade"), rs.getString("edu_level")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching for books: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchBooks(String keyword, boolean searchById, boolean searchByName, boolean searchByAuthor, boolean searchByPublisher, boolean searchByGenre) {
        tableModel.setRowCount(0);
        StringBuilder query = new StringBuilder("SELECT * FROM Book WHERE 1=1");

        if (!keyword.isEmpty()) {
            if (searchById) {
                query.append(" AND id LIKE ?");
            }
            if (searchByName) {
                query.append(" AND name LIKE ?");
            }
            if (searchByAuthor) {
                query.append(" AND author LIKE ?");
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
                if (searchByAuthor) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (searchByPublisher) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
                if (searchByGenre) {
                    pstmt.setString(paramIndex++, "%" + keyword + "%");
                }
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"), rs.getInt("quantity"), rs.getString("unit"),
                    rs.getString("origin"), rs.getString("author"), rs.getString("publisher"),
                    rs.getInt("publicationYear"), rs.getString("genre"), rs.getString("language"),});
            }
            textbookTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer(keyword));
            textbookTable.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching for books: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class CustomTableCellRenderer extends DefaultTableCellRenderer {

        private final String keyword;

        // Constructor nhận từ khóa
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

    private void addTextbook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO textbook (id, name, cost_price, sale_price, quantity, unit, origin, author, publisher, publication_year, genre, language, subject, grade, edu_level) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
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
            pstmt.setString(13, txtSubject.getText());
            pstmt.setInt(14, Integer.parseInt(txtGrade.getText()));
            pstmt.setString(15, txtEduLevel.getText());
            pstmt.executeUpdate();
            loadTextbookData();
            JOptionPane.showMessageDialog(null, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding textbook: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editTextbook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "UPDATE textbook SET name = ?, cost_price = ?, sale_price = ?, quantity = ?, unit = ?, origin = ?, author = ?, publisher = ?, publication_year = ?, genre = ?, language = ?, subject = ?, grade = ?, edu_level = ? WHERE id = ?")) {
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
            pstmt.setString(12, txtSubject.getText());
            pstmt.setInt(13, Integer.parseInt(txtGrade.getText()));
            pstmt.setString(14, txtEduLevel.getText());
            pstmt.setString(15, txtId.getText());
            pstmt.executeUpdate();
            loadTextbookData();
            JOptionPane.showMessageDialog(null, "Sửa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating textbook: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTextbook() {
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(
                "DELETE FROM textbook WHERE id = ?")) {
            pstmt.setString(1, txtId.getText());
            pstmt.executeUpdate();
            loadTextbookData();
            JOptionPane.showMessageDialog(null, "Xóa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting textbook: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
