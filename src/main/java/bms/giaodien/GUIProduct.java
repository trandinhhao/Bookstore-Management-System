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
    private JTextField unitField;
    private JTextField originField;
    private JTextField authorField;
    private JTextField genreField;
    private JTextField languageField;
    private JTextField dateField;
    private JPanel datePanel;
    private JComboBox<String> categoryComboBox;

    public GUIProduct() throws ClassNotFoundException, SQLException {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel mainContent = new JPanel();
        mainContent.setBackground(Color.WHITE);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel inputForm = createEnhancedInputForm();
        inputForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(inputForm);
        mainContent.add(Box.createVerticalStrut(10));

        JPanel tablePanel = createEnhancedTablePanel("textbook");
        JPanel searchPanel = createSearchPanel();
        searchPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchPanel.setPreferredSize(new Dimension(500, 30));
        tablePanel.add(searchPanel, BorderLayout.NORTH);

        mainContent.add(tablePanel);
        panel.add(mainContent, BorderLayout.CENTER);

        this.setLayout(new BorderLayout()); // Đặt layout chính
        this.add(panel, BorderLayout.CENTER); // Thêm panel chính vào GUIProduct
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

        // Khởi tạo các trường nhập liệu
        productIdField = new JTextField(10);  // Mã sản phẩm
        productNameField = new JTextField(10); // Tên sản phẩm
        costPriceField = new JTextField(10);  // Giá nhập
        unitPriceField = new JTextField(10);  // Đơn giá
        quantityField = new JTextField(10);  // Số lượng
        unitField = new JTextField(10);      // Đơn vị tính
        originField = new JTextField(10);    // Xuất xứ
        supplierField = new JTextField(10);  // Nhà cung cấp
        authorField = new JTextField(10);    // Tác giả
        genreField = new JTextField(10);     // Thể loại
        languageField = new JTextField(10);  // Ngôn ngữ
        dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 8); // Ngày nhập

        // ComboBox cho thể loại sản phẩm
        categoryComboBox = new JComboBox<>(new String[]{"Sách", "Quà lưu niệm", "Vở", "Dụng cụ học tập", "Sách giáo khoa"});

        // Panel để chứa các trường nhập liệu
        addFormField(panel, gbc, 0, 0, "Mã sản phẩm:", productIdField);
        addFormField(panel, gbc, 0, 1, "Tên sản phẩm:", productNameField);
        addFormField(panel, gbc, 0, 2, "Giá nhập:", costPriceField);
        addFormField(panel, gbc, 0, 3, "Giá bán:", unitPriceField);
        addFormField(panel, gbc, 0, 4, "Số lượng:", quantityField);
        addFormField(panel, gbc, 0, 5, "Đơn vị tính:", unitField);
        addFormField(panel, gbc, 0, 6, "Xuất xứ:", originField);
        addFormField(panel, gbc, 2, 0, "Nhà cung cấp:", supplierField);
        addFormField(panel, gbc, 2, 1, "Tác giả:", authorField);
        addFormField(panel, gbc, 2, 2, "Thể loại:", genreField);
        addFormField(panel, gbc, 2, 3, "Ngôn ngữ:", languageField);
        addFormField(panel, gbc, 2, 4, "Ngày nhập:", dateField);
        addFormField(panel, gbc, 2, 6, "Loại sản phẩm:", categoryComboBox);

        // Các nút chức năng
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JButton cancelButton = new JButton("Hủy");
        JButton editButton = new JButton("Sửa");
        JButton refreshButton = new JButton("Làm mới");
        JButton saveButton = new JButton("Lưu");

        // Lắng nghe sự kiện nút Lưu
        saveButton.addActionListener(e -> {
            System.out.println("Mã sản phẩm: " + productIdField.getText());
            System.out.println("Tên sản phẩm: " + productNameField.getText());
            System.out.println("Giá nhập: " + costPriceField.getText());
            System.out.println("Đơn giá: " + unitPriceField.getText());
            System.out.println("Số lượng: " + quantityField.getText());
            System.out.println("Đơn vị tính: " + unitField.getText());
            System.out.println("Xuất xứ: " + originField.getText());
            System.out.println("Nhà cung cấp: " + supplierField.getText());
            System.out.println("Tác giả: " + authorField.getText());
            System.out.println("Thể loại: " + genreField.getText());
            System.out.println("Ngôn ngữ: " + languageField.getText());
            System.out.println("Ngày nhập: " + dateField.getText());
            System.out.println("Loại sản phẩm: " + categoryComboBox.getSelectedItem());
        });

        // Lắng nghe sự kiện nút Làm mới
        refreshButton.addActionListener(e -> {
            productIdField.setText("");
            productNameField.setText("");
            costPriceField.setText("");
            unitPriceField.setText("");
            quantityField.setText("");
            unitField.setText("");
            originField.setText("");
            supplierField.setText("");
            authorField.setText("");
            genreField.setText("");
            languageField.setText("");
            dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        });

        // Thêm các nút vào panel
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
                panel.removeAll();
                GridBagConstraints gbcc = new GridBagConstraints();
                gbcc.insets = new Insets(5, 5, 5, 5);
                gbcc.anchor = GridBagConstraints.WEST;
                switch (selectedCategory) {
                    case "Sách":
                        addFormField(panel, gbc, 0, 0, "Mã sản phẩm:", productIdField = new JTextField(10));
                        addFormField(panel, gbc, 0, 1, "Tên sản phẩm:", productNameField = new JTextField(10));
                        addFormField(panel, gbc, 0, 2, "Giá nhập:", costPriceField = new JTextField(10));
                        addFormField(panel, gbc, 0, 3, "Giá bán:", unitPriceField = new JTextField(10));
                        addFormField(panel, gbc, 0, 4, "Số lượng:", quantityField = new JTextField(10));
                        addFormField(panel, gbc, 0, 5, "Đơn vị tính:", unitField = new JTextField(10));
                        addFormField(panel, gbc, 0, 6, "Xuất xứ:", originField = new JTextField(10));
                        addFormField(panel, gbc, 2, 0, "Nhà cung cấp:", supplierField = new JTextField(10));
                        addFormField(panel, gbc, 2, 1, "Tác giả:", authorField = new JTextField(10));
                        addFormField(panel, gbc, 2, 2, "Thể loại:", genreField = new JTextField(10));
                        addFormField(panel, gbc, 2, 3, "Ngôn ngữ:", languageField = new JTextField(10));
                        addFormField(panel, gbc, 2, 4, "Ngày nhập:", dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 8));
                        loadProductData("book");
                        break;
                    case "Sách giáo khoa":
                        // Thêm các trường đặc biệt cho "Sách giáo khoa"
                        addFormField(panel, gbc, 0, 0, "Mã sản phẩm:", productIdField = new JTextField(10));
                        addFormField(panel, gbc, 0, 1, "Tên sản phẩm:", productNameField = new JTextField(10));
                        addFormField(panel, gbc, 0, 2, "Giá nhập:", costPriceField = new JTextField(10));
                        addFormField(panel, gbc, 0, 3, "Giá bán:", unitPriceField = new JTextField(10));
                        addFormField(panel, gbc, 0, 4, "Số lượng:", quantityField = new JTextField(10));
                        addFormField(panel, gbc, 0, 5, "Đơn vị tính:", unitField = new JTextField(10));
                        addFormField(panel, gbc, 0, 6, "Xuất xứ:", originField = new JTextField(10));
                        addFormField(panel, gbc, 2, 0, "Nhà cung cấp:", supplierField = new JTextField(10));
                        addFormField(panel, gbc, 2, 1, "Tác giả:", authorField = new JTextField(10));
                        addFormField(panel, gbc, 2, 2, "Thể loại:", genreField = new JTextField(10));
                        addFormField(panel, gbc, 2, 3, "Ngôn ngữ:", languageField = new JTextField(10));
                        addFormField(panel, gbc, 2, 4, "Ngày nhập:", dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 8));
                        addFormField(panel, gbc, 2, 5, "Môn học:", new JTextField(10));
                        addFormField(panel, gbc, 2, 6, "Lớp:", new JTextField(10));
                        addFormField(panel, gbc, 2, 7, "Cấp độ học:", new JTextField(10));
                        break;
                    case "Quà lưu niệm":
                        // Thêm các trường đặc biệt cho "Quà lưu niệm"
                        addFormField(panel, gbc, 0, 0, "Mã sản phẩm:", productIdField = new JTextField(10));
                        addFormField(panel, gbc, 0, 1, "Tên sản phẩm:", productNameField = new JTextField(10));
                        addFormField(panel, gbc, 0, 2, "Giá nhập:", costPriceField = new JTextField(10));
                        addFormField(panel, gbc, 0, 3, "Giá bán:", unitPriceField = new JTextField(10));
                        addFormField(panel, gbc, 0, 4, "Số lượng:", quantityField = new JTextField(10));
                        addFormField(panel, gbc, 0, 5, "Đơn vị tính:", unitField = new JTextField(10));
                        addFormField(panel, gbc, 0, 6, "Xuất xứ:", originField = new JTextField(10));
                        addFormField(panel, gbc, 2, 0, "Loại:", new JTextField(10));
                        addFormField(panel, gbc, 2, 1, "Chất liệu:", new JTextField(10));
                        loadProductData("gift");
                        break;
                    default:
                        // Xử lý trường hợp mặc định nếu cần
                        break;
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
            columnNames[0] = "STT";
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
                return new String[]{"STT"}; // Trường hợp bảng không xác định
        }
    }

    private void addTableRowSelectionListener() {
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow(); // Lấy chỉ số hàng được chọn
                if (selectedRow != -1) { // Kiểm tra nếu có hàng được chọn
                    // Lấy dữ liệu từ hàng
                    String[] rowData = new String[table.getColumnCount()];
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        Object value = table.getValueAt(selectedRow, i); // Truy cập dữ liệu an toàn
                        rowData[i] = value != null ? value.toString() : "";
                    }

                    // Cập nhật dữ liệu vào các trường giao diện
                    productIdField.setText(rowData[1]);
                    productNameField.setText(rowData[2]);
                    costPriceField.setText(rowData[3]);
                    unitPriceField.setText(rowData[4]);
                    quantityField.setText(rowData[5]);
                    unitField.setText(rowData[6]);
                    originField.setText(rowData[7]);
                    supplierField.setText(rowData[9]);
                    authorField.setText(rowData[8]);
                    genreField.setText(rowData[11]);
                    languageField.setText(rowData[12]);
                    dateField.setText(rowData[10]);

                    // Cập nhật combobox thể loại sản phẩm
                    String category = rowData[13];
                    if (category != null && !category.isEmpty()) {
                        categoryComboBox.setSelectedItem(category);
                    }
                }
            }
        });
    }

// Hàm tiện ích lấy giá trị từ bảng (kèm xử lý giá trị null)
    private String getTableValue(int row, int column) {
        Object value = table.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // JCheckBox để lựa chọn tiêu chí tìm kiếm
        JCheckBox searchByIdCheckBox = new JCheckBox("ID");
        JCheckBox searchByNameCheckBox = new JCheckBox("Tên sản phẩm");
        JCheckBox searchByAuthorCheckBox = new JCheckBox("Tác giả");
        JCheckBox searchByPublisherCheckBox = new JCheckBox("Nhà xuất bản");

        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Tìm kiếm");
        JButton resetButton = new JButton("Đặt lại");

        // Hành động tìm kiếm
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase(); // Lấy từ khóa tìm kiếm
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Duyệt qua từng dòng và chỉ hiển thị các dòng phù hợp với ít nhất 1 checkbox được chọn
            for (int i = model.getRowCount() - 1; i >= 0; i--) {
                boolean matches = false;

                // Kiểm tra các checkbox được chọn
                if (searchByIdCheckBox.isSelected()) {
                    String id = model.getValueAt(i, 1).toString().toLowerCase();
                    if (id.contains(keyword)) {
                        matches = true;
                    }
                }

                if (searchByNameCheckBox.isSelected()) {
                    String name = model.getValueAt(i, 2).toString().toLowerCase();
                    if (name.contains(keyword)) {
                        matches = true;
                    }
                }

                if (searchByAuthorCheckBox.isSelected()) {
                    String author = model.getValueAt(i, 9).toString().toLowerCase();
                    if (author.contains(keyword)) {
                        matches = true;
                    }
                }

                if (searchByPublisherCheckBox.isSelected()) {
                    String publisher = model.getValueAt(i, 8).toString().toLowerCase();
                    if (publisher.contains(keyword)) {
                        matches = true;
                    }
                }

                // Nếu không khớp với bất kỳ tiêu chí nào, loại bỏ hàng
                if (!matches) {
                    model.removeRow(i);
                }
            }
        });

        // Hành động đặt lại bảng
        resetButton.addActionListener(e -> {
            try {
                loadProductData("book"); // Tải lại toàn bộ dữ liệu từ cơ sở dữ liệu
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Không thể tải lại dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Thêm các thành phần vào searchPanel
        searchPanel.add(new JLabel("Tìm kiếm theo:"));
        searchPanel.add(searchByIdCheckBox);
        searchPanel.add(searchByNameCheckBox);
        searchPanel.add(searchByAuthorCheckBox);
        searchPanel.add(searchByPublisherCheckBox);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

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
