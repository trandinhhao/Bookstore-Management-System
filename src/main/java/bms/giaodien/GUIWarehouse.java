package bms.giaodien;

import bms.connectDB.ConnectMySQL;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class GUIWarehouse extends JPanel {

    private Color primaryColor = new Color(195, 199, 243, 255);
    private Color textColor = new Color(50, 50, 50);
    private Font menuFont = new Font("Segoe UI", Font.BOLD, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
    private JTable table;
    private JTextField productIdField, productNameField, costPriceField, salePriceField, quantityField;
    private JComboBox<String> categoryComboBox;

    public GUIWarehouse() throws SQLException, ClassNotFoundException {
        // Khởi tạo các panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Main Content Panel
        JPanel mainContent = new JPanel();
        mainContent.setBackground(Color.WHITE);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 250, 20, 250));

        // Input Form
        JPanel inputForm = createEnhancedInventoryInputForm();
        inputForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(inputForm);
        mainContent.add(Box.createVerticalStrut(10));

        // Table Panel
        JPanel tablePanel = createEnhancedInventoryTablePanel();
        mainContent.add(tablePanel);

        panel.add(mainContent, BorderLayout.CENTER);

        // Gọi phương thức khởi tạo combobox và lắng nghe sự thay đổi
        initializeCategoryComboBox();

        // Load dữ liệu mặc định cho bảng sách
        loadInventoryData("book");

        add(panel);
    }

    private void initializeCategoryComboBox() {
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            String tableName = getTableNameFromCategory(selectedCategory);
            try {
                loadInventoryData(tableName);
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    private String getTableNameFromCategory(String category) {
        switch (category) {
            case "Quà lưu niệm":
                return "gift";
            case "Vở":
                return "notebook";
            case "Dụng cụ học tập":
                return "stationery";
            case "Sách giáo khoa":
                return "textbook";
            case "Sách":
            default:
                return "book"; // Mặc định là bảng book
        }
    }

    private JPanel createEnhancedInventoryInputForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Khởi tạo các trường nhập liệu
        productIdField = new JTextField(10);
        productNameField = new JTextField(10);
        costPriceField = new JTextField(10);
        salePriceField = new JTextField(10);
        quantityField = new JTextField(10);

        String[] categories = {"Sách", "Quà lưu niệm", "Vở", "Dụng cụ học tập", "Sách giáo khoa"};
        categoryComboBox = new JComboBox<>(categories);

        // Tạo mục tìm kiếm
        JLabel searchLabel = new JLabel("Tìm kiếm:");
        JTextField searchField = new JTextField(10); // Ô nhập tìm kiếm
        JButton searchButton = new JButton("Tìm kiếm");

        // Các lựa chọn tìm kiếm
        String[] searchOptions = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng"};
        JComboBox<String> searchChoice = new JComboBox<>(searchOptions);

        // Thêm sự kiện cho nút tìm kiếm
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            String searchCriteria = (String) searchChoice.getSelectedItem();
            try {
                searchInventoryData(searchText, searchCriteria);  // Hàm tìm kiếm
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        // Thêm các trường nhập liệu vào form
        addFormField(panel, gbc, 0, 0, "Mã sản phẩm:", productIdField);
        addFormField(panel, gbc, 0, 1, "Tên sản phẩm:", productNameField);
        addFormField(panel, gbc, 0, 2, "Giá nhập:", costPriceField);
        addFormField(panel, gbc, 0,3, "Giá bán:", salePriceField);
        addFormField(panel, gbc, 2, 1, "Số lượng:", quantityField);
        addFormField(panel, gbc, 2, 2, "Loại sản phẩm:", categoryComboBox);

        // Thêm mục tìm kiếm vào form
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(searchLabel, gbc);

        gbc.gridx = 1;
        panel.add(searchField, gbc);

        gbc.gridx = 2;
        panel.add(searchChoice, gbc);

        gbc.gridx = 3;
        panel.add(searchButton, gbc);

        // Panel nút chức năng
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
            System.out.println("Giá bán: " + salePriceField.getText());
            System.out.println("Số lượng: " + quantityField.getText());
            System.out.println("Loại sản phẩm: " + categoryComboBox.getSelectedItem());
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(refreshButton);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        panel.add(buttonPanel, gbc);

        return panel;
    }


    private void addFormField(JPanel panel, GridBagConstraints gbc, int x, int y, String label, JComponent field) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = x + 1;
        panel.add(field, gbc);
    }

    private JPanel createEnhancedInventoryTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Số thứ tự", "Mã SP", "Tên SP", "Giá nhập", "Giá bán", "Số lượng"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadInventoryData(String tableName) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();
        if (conn == null) {
            return;
        }

        String query = "SELECT id, name, cost_price, sale_price, quantity FROM bms." + tableName + " ORDER BY quantity ASC";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        int rowNumber = 1;

        // Đặt ngưỡng giá trị cho từng bảng
        int threshold = getQuantityThreshold(tableName); // Lấy ngưỡng phù hợp với từng bảng

        while (rs.next()) {
            Object[] rowData = {
                    rowNumber++,
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"),
                    rs.getInt("quantity")
            };
            model.addRow(rowData);
        }

        // Sử dụng TableCellRenderer để bôi đỏ toàn bộ dòng khi quantity < ngưỡng
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Lấy giá trị quantity ở dòng hiện tại
                int quantity = (Integer) table.getValueAt(row, 5);

                // Kiểm tra nếu quantity nhỏ hơn ngưỡng cho bảng hiện tại
                if (quantity < threshold) {
                    c.setBackground(new Color(195, 199, 243, 255)); // Bôi đỏ toàn bộ dòng khi quantity < ngưỡng
                    c.setForeground(Color.BLACK); // Màu chữ trắng để dễ nhìn
                } else {
                    c.setBackground(Color.WHITE); // Màu nền bình thường
                    c.setForeground(Color.BLACK); // Màu chữ bình thường
                }

                return c;
            }
        });

        // Thêm ListSelectionListener để khi chọn một dòng, thông tin của dòng đó sẽ hiển thị lên input form
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy dữ liệu từ dòng đã chọn
                    String productId = (String) table.getValueAt(selectedRow, 1);
                    String productName = (String) table.getValueAt(selectedRow, 2);
                    double costPrice = (Double) table.getValueAt(selectedRow, 3);
                    double salePrice = (Double) table.getValueAt(selectedRow, 4);
                    int quantity = (Integer) table.getValueAt(selectedRow, 5);

                    // Hiển thị thông tin lên form input
                    productIdField.setText(productId);
                    productNameField.setText(productName);
                    costPriceField.setText(String.valueOf(costPrice));
                    salePriceField.setText(String.valueOf(salePrice));
                    quantityField.setText(String.valueOf(quantity));
                    // Set loại sản phẩm cho ComboBox
                    String category = getCategoryFromTableName(tableName); // Lấy loại sản phẩm dựa trên bảng
                    categoryComboBox.setSelectedItem(category);
                }
            }
        });

        rs.close();
        stmt.close();
    }

    private String getCategoryFromTableName(String tableName) {
        switch (tableName) {
            case "book":
                return "Sách";
            case "gift":
                return "Quà lưu niệm";
            case "notebook":
                return "Vở";
            case "stationery":
                return "Dụng cụ học tập";
            case "textbook":
                return "Sách giáo khoa";
            default:
                return "";
        }
    }

    private int getQuantityThreshold(String tableName) {
        switch (tableName) {
            case "book":
                return 50; // Sách < 50
            case "gift":
                return 50; // Quà lưu niệm < 50
            case "notebook":
                return 500; // Vở < 500
            case "stationery":
                return 200; // Dụng cụ học tập < 200
            case "textbook":
                return 200; // Sách giáo khoa < 200
            default:
                return Integer.MAX_VALUE; // Nếu bảng không xác định, không bôi đỏ
        }
    }
    private void searchInventoryData(String searchText, String searchCriteria) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();
        if (conn == null) {
            return;
        }

        String query = "";
        switch (searchCriteria) {
            case "Mã sản phẩm":
                query = "SELECT id, name, cost_price, sale_price, quantity FROM bms." + getTableNameFromCategory((String) categoryComboBox.getSelectedItem()) + " WHERE id LIKE ? ORDER BY quantity ASC";
                break;
            case "Tên sản phẩm":
                query = "SELECT id, name, cost_price, sale_price, quantity FROM bms." + getTableNameFromCategory((String) categoryComboBox.getSelectedItem()) + " WHERE name LIKE ? ORDER BY quantity ASC";
                break;
            case "Số lượng":
                query = "SELECT id, name, cost_price, sale_price, quantity FROM bms." + getTableNameFromCategory((String) categoryComboBox.getSelectedItem()) + " WHERE quantity = ? ORDER BY quantity ASC";
                break;
        }

        PreparedStatement stmt = conn.prepareStatement(query);
        if ("Số lượng".equals(searchCriteria)) {
            stmt.setInt(1, Integer.parseInt(searchText)); // Nếu tìm theo số lượng, chuyển đổi searchText thành số
        } else {
            stmt.setString(1, "%" + searchText + "%"); // Nếu tìm theo mã hoặc tên, tìm theo phần của chuỗi
        }

        ResultSet rs = stmt.executeQuery();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        int rowNumber = 1;

        while (rs.next()) {
            Object[] rowData = {
                    rowNumber++,
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"),
                    rs.getInt("quantity")
            };
            model.addRow(rowData);
        }

        rs.close();
        stmt.close();
    }

}


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                // Set the look and feel to match the system
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//
//                // Create a new JFrame to hold the panel
//                JFrame frame = new JFrame("Test GUIwarehouse");
//
//                // Create an instance of the GUIWarehouse panel
//                GUIWarehouse warehouse = new GUIWarehouse();
//
//                // Add the panel to the frame
//                frame.add(warehouse);
//
//                // Set default close operation
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                // Pack the frame to fit the preferred size of its components
//                frame.pack();
//
//                // Center the frame on the screen
//                frame.setLocationRelativeTo(null);
//
//                // Make the frame visible
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//}
