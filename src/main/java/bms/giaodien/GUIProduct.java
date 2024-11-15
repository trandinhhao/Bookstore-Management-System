package bms.giaodien;

import bms.connectDB.ConnectMySQL;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUIProduct extends JPanel {

    private Color primaryColor = new Color(195, 199, 243, 255);
    private Color textColor = new Color(50, 50, 50);
    private Font menuFont = new Font("Segoe UI", Font.BOLD, 14);
    private Font idFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
    private JTable table;
    private JTextField productIdField;
    private JTextField productNameField;
    private JTextField costPriceField;
    private JTextField quantityField;
    private JTextField supplierField;
    private JTextField unitPriceField;
    private JTextField dateField;
    private JPanel datePanel;
    private JComboBox<String> categoryComboBox;

    public GUIProduct() throws ClassNotFoundException, SQLException {
        System.out.println("OK");
        
        JPanel panel = new JPanel(new BorderLayout());
        // Main Content Panel
        JPanel mainContent = new JPanel();
        mainContent.setBackground(Color.WHITE);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS)); // Căn chỉnh theo trục Y (theo chiều dọc)
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input Form
        JPanel inputForm = createEnhancedInputForm();
        inputForm.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa Input Form
        mainContent.add(inputForm);
        mainContent.add(Box.createVerticalStrut(10)); // Khoảng cách giữa Input Form và Table Panel

        // Table Panel
        JPanel tablePanel = createEnhancedTablePanel("textbook");

        // Thêm Search Panel vào trên Table Panel
        JPanel searchPanel = createSearchPanel();
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa Search Panel
        searchPanel.setPreferredSize(new Dimension(500, 30)); // Cài kích thước tùy chọn cho Search Panel
        tablePanel.add(searchPanel, BorderLayout.NORTH); // Đặt Search Panel ở phía trên Table Panel

        mainContent.add(tablePanel);

        panel.add(mainContent, BorderLayout.CENTER);
        loadProductData("book");
        addTableRowSelectionListener();
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(primaryColor);
        titlePanel.setPreferredSize(new Dimension(0, 40));
        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ BÁN HÀNG");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        titlePanel.add(titleLabel);
        panel.add(titlePanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainContent = new JPanel();
        mainContent.setBackground(Color.WHITE);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS)); // Căn chỉnh theo trục Y (theo chiều dọc)
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input Form
        JPanel inputForm = createEnhancedInputForm();
        inputForm.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa Input Form
        mainContent.add(inputForm);
        mainContent.add(Box.createVerticalStrut(10)); // Khoảng cách giữa Input Form và Table Panel

        // Table Panel
        JPanel tablePanel = createEnhancedTablePanel("textbook");

        // Thêm Search Panel vào trên Table Panel
        JPanel searchPanel = createSearchPanel();
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Căn giữa Search Panel
        searchPanel.setPreferredSize(new Dimension(500, 30)); // Cài kích thước tùy chọn cho Search Panel
        tablePanel.add(searchPanel, BorderLayout.NORTH); // Đặt Search Panel ở phía trên Table Panel

        mainContent.add(tablePanel);

        panel.add(mainContent, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEnhancedInputForm() { // dua sang cai khac
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Khởi tạo biến thành viên thay vì biến cục bộ
        productIdField = new JTextField(10);
        productNameField = new JTextField(10);
        costPriceField = new JTextField(10);
        quantityField = new JTextField(10);
        supplierField = new JTextField(10);
        unitPriceField = new JTextField(10);

        datePanel = new JPanel(new BorderLayout());
        dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 8);
        JButton dateButton = new JButton("...");
        datePanel.add(dateField, BorderLayout.CENTER);
        datePanel.add(dateButton, BorderLayout.EAST);

        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Sách", "Quà lưu niệm", "Vở", "Dụng cụ học tập", "Sách giáo khoa"});

        // Add form fields to the panel
        addFormField(panel, gbc, 0, 0, "Mã sản phẩm:", productIdField);
        addFormField(panel, gbc, 0, 1, "Tên sản phẩm:", productNameField);
        addFormField(panel, gbc, 0, 2, "Giá nhập:", costPriceField);
        addFormField(panel, gbc, 0, 3, "Số lượng:", quantityField);
        addFormField(panel, gbc, 2, 0, "Nhà cung cấp:", supplierField);
        addFormField(panel, gbc, 2, 1, "Đơn giá:", unitPriceField);
        addFormField(panel, gbc, 2, 3, "Ngày nhập:", datePanel);
        addFormField(panel, gbc, 2, 2, "Loại sản phẩm:", categoryComboBox);

        // Button panel with actions
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JButton cancelButton = new JButton("Hủy");
        JButton editButton = new JButton("Sửa");
        JButton refreshButton = new JButton("Làm mới");
        JButton saveButton = new JButton("Lưu");

        // Add ActionListener to print form data on "Lưu" button click
        saveButton.addActionListener(e -> {
            // Print values of each field
            System.out.println("Mã sản phẩm: " + productIdField.getText());
            System.out.println("Tên sản phẩm: " + productNameField.getText());
            System.out.println("Giá nhập: " + costPriceField.getText());
            System.out.println("Số lượng: " + quantityField.getText());
            System.out.println("Nhà cung cấp: " + supplierField.getText());
            System.out.println("Đơn giá: " + unitPriceField.getText());
            System.out.println("Ngày nhập: " + dateField.getText());
            System.out.println("Loại sản phẩm: " + categoryComboBox.getSelectedItem());
        });
        refreshButton.addActionListener(e -> {
            productIdField.setText("");
            productNameField.setText("");
            costPriceField.setText("");
            quantityField.setText("");
            unitPriceField.setText("");
            supplierField.setText("");
            dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        });

        // Add buttons to the panel
        buttonPanel.add(cancelButton);
        buttonPanel.add(editButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(saveButton);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        panel.add(buttonPanel, gbc);

        // Update category selection with data loading if necessary
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            try {
                switch (selectedCategory) {
                    case "Sách" ->
                        loadProductData("book");
                    case "Quà lưu niệm" ->
                        loadProductData("gift");
                    case "Vở" ->
                        loadProductData("notebook");
                    case "Dụng cụ học tập" ->
                        loadProductData("stationery");
                    case "Sách giáo khoa" ->
                        loadProductData("textbook");
                    default ->
                        JOptionPane.showMessageDialog(this, "Loại sản phẩm không tồn tại", "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int x, int y, String label, JComponent field) { // dua sang cai khac
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = x + 1;
        panel.add(field, gbc);
    }

    private void loadProductData(String tableName) throws ClassNotFoundException, SQLException { //dua sang cai khac
        Connection conn = ConnectMySQL.getConnection();
        if (conn == null) {
            return;
        }

        String query = "SELECT * FROM bms." + tableName;

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Lấy metadata từ ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Tạo danh sách tên cột từ metadata và thêm cột "Số thứ tự"
            String[] columnNames = new String[columnCount + 1];
            columnNames[0] = "Số thứ tự";
            for (int i = 0; i < columnCount; i++) {
                columnNames[i + 1] = metaData.getColumnLabel(i + 1);
            }

            // Cập nhật mô hình bảng (DefaultTableModel) với các tên cột động
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setColumnIdentifiers(columnNames); // Cập nhật các tên cột

            // Xóa các dòng cũ trong bảng
            model.setRowCount(0);

            // Thêm các dữ liệu vào bảng với "Số thứ tự"
            int rowNumber = 1;
            while (rs.next()) {
                Object[] rowData = new Object[columnCount + 1];
                rowData[0] = rowNumber++; // Gán số thứ tự cho từng dòng
                for (int i = 0; i < columnCount; i++) {
                    rowData[i + 1] = rs.getObject(i + 1);
                }
                model.addRow(rowData); // Thêm mỗi dòng vào bảng
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu sản phẩm!");
        }
    }

    private JPanel createEnhancedTablePanel(String tableName) {  //dua sang cai khac
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = getColumnNamesForTable(tableName);  // Lấy danh sách cột dựa trên loại bảng
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private String[] getColumnNamesForTable(String tableName) {  //dua sang cai khac
        switch (tableName) {
            case "book":
                return new String[]{"Số thứ tự", "Mã SP", "Tên SP", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị tính", "Xuất xứ", "Nhà xuất bản", "Tác giả", "Năm xuất bản", "Thể loại", "Ngôn ngữ"};
            case "textbook":
                return new String[]{"Số thứ tự", "Mã SP", "Tên SP", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị tính", "Xuất xứ", "Nhà xuất bản", "Tác giả", "Năm xuất bản", "Thể loại", "Ngôn ngữ", "Môn học", "Lớp", "Cấp độ học"};
            case "gift":
                return new String[]{"Số thứ tự", "Mã SP", "Tên SP", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị tính", "Xuất xứ", "Loại", "Chất liệu"};
            case "stationery":
                return new String[]{"Số thứ tự", "Mã SP", "Tên SP", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị tính", "Xuất xứ", "Loại", "Nhà sản xuất", "Chất liệu"};
            case "notebook":
                return new String[]{"Số thứ tự", "Mã SP", "Tên SP", "Giá nhập", "Giá bán", "Số lượng", "Đơn vị tính", "Xuất xứ", "Số trang", "Loại giấy", "Kích thước", "Nhà sản xuất"};
            default:
                return new String[]{"Số thứ tự"}; // Trường hợp bảng không xác định
        }
    }

    private void addTableRowSelectionListener() { // dua sang cai khac
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get data from the selected row and display it in the form fields
                    productIdField.setText(table.getValueAt(selectedRow, 0).toString());
                    productNameField.setText(table.getValueAt(selectedRow, 1).toString());
                    costPriceField.setText(table.getValueAt(selectedRow, 2).toString());
                    unitPriceField.setText(table.getValueAt(selectedRow, 3).toString());
                    quantityField.setText(table.getValueAt(selectedRow, 4).toString());
                    supplierField.setText(table.getValueAt(selectedRow, 8).toString());
                    // Set supplierComboBox and categoryComboBox selections based on the data if possible

                    categoryComboBox.setSelectedItem(table.getValueAt(selectedRow, 10).toString());

                    // Date field (if it's available and needs specific formatting)
                    dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date())); // Set the appropriate date if applicable
                }
            }
        });
    }

    private JPanel createSearchPanel() { // dua sang cai khac
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm kiếm");

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String productName = model.getValueAt(i, 1).toString().toLowerCase();
                if (!productName.contains(keyword)) {
                    model.removeRow(i);
                    i--; // Điều chỉnh chỉ số sau khi xóa hàng
                }
            }
        });

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        return searchPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the look and feel to match the system
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Create a new JFrame to hold the panel
                JFrame frame = new JFrame("Test GUIProduct");

                // Create an instance of the GUIProduct panel
                GUIProduct productPanel = new GUIProduct();

                // Add the panel to the frame
                frame.add(productPanel);

                // Set default close operation
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Pack the frame to fit the preferred size of its components
                frame.pack();

                // Center the frame on the screen
                frame.setLocationRelativeTo(null);

                // Make the frame visible
                frame.setVisible(true);
            } catch (ClassNotFoundException | SQLException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
