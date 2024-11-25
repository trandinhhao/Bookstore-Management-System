package bms.giaodien;

import bms.connectDB.ConnectMySQL;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class GUIProduct_Notebook extends JPanel {

    private JTable notebookTable;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtName, txtCostPrice, txtSalePrice, txtQuantity, txtUnit,
            txtOrigin, txtPageCount, txtPaperType, txtSize, txtManufacturer, txtSearch;
    private JCheckBox cbId, cbName, cbManufacturer;  // Checkbox tìm kiếm
    private JButton btnSearch;

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

        // Phần tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Từ khóa tìm kiếm:"));
        txtSearch = new JTextField(15);
        btnSearch = new JButton("Tìm kiếm");
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cbId = new JCheckBox("Mã");
        cbName = new JCheckBox("Tên");
        cbManufacturer = new JCheckBox("Nhà sản xuất");
        checkBoxPanel.add(cbId);
        checkBoxPanel.add(cbName);
        checkBoxPanel.add(cbManufacturer);
        searchPanel.add(checkBoxPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.NORTH);

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

        // Tìm kiếm theo các tiêu chí
        btnSearch.addActionListener(e -> searchNoteBook(txtSearch.getText()));

        notebookTable.getSelectionModel().addListSelectionListener(e -> {
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

    private void searchNoteBook(String keyword) {
        tableModel.setRowCount(0);  // Xóa dữ liệu hiện tại trên bảng

        // Bắt đầu câu lệnh SQL
        StringBuilder query = new StringBuilder("SELECT * FROM notebook WHERE 1=1");
        java.util.List<String> selectedCriteria = new ArrayList<>();

        // Kiểm tra các checkbox và thêm vào danh sách điều kiện
        if (cbId.isSelected()) {
            query.append(" AND id LIKE ?");
            selectedCriteria.add("Mã quà");
        }
        if (cbName.isSelected()) {
            query.append(" AND name LIKE ?");
            selectedCriteria.add("Tên quà");
        }
        if (cbManufacturer.isSelected()) {
            query.append(" AND manufacturer LIKE ?");
            selectedCriteria.add("Nhà sản xuất");
        }

        if (selectedCriteria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một tiêu chí tìm kiếm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Thực thi câu truy vấn với các tiêu chí đã chọn
        try (Connection con = ConnectMySQL.getConnection(); PreparedStatement pstmt = con.prepareStatement(query.toString())) {
            // Thiết lập các tham số trong PreparedStatement
            int paramIndex = 1;
            for (int i = 0; i < selectedCriteria.size(); i++) {
                pstmt.setString(paramIndex++, "%" + keyword + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"), rs.getString("name"), rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"), rs.getInt("quantity"), rs.getString("unit"),
                    rs.getString("origin"), rs.getString("page_count"), rs.getString("paper_type"), rs.getString("size"),
                    rs.getString("manufacturer")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching for gifts: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchNoteBook(String keyword, boolean searchById, boolean searchByName, boolean searchByAuthor, boolean searchByPublisher, boolean searchByGenre) {
        tableModel.setRowCount(0);
        StringBuilder query = new StringBuilder("SELECT * FROM gift WHERE 1=1");

        if (!keyword.isEmpty()) {
            if (searchById) {
                query.append(" AND id LIKE ?");
            }
            if (searchByName) {
                query.append(" AND name LIKE ?");
            }
            if (searchByAuthor) {
                query.append(" AND type LIKE ?");
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
                    rs.getInt("publicationYear"), rs.getString("genre"), rs.getString("language")
                });
            }
            notebookTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer(keyword));
            notebookTable.repaint();
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
