package bms.giaodien;

import bms.system_management.Order;
import bms.product.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class GUIBill extends JFrame {

    private JTextField productIdField;
    private JTextField quantityField;
    private JTextField productTypeField;
    private JTextArea billArea;
    private JButton createBillButton;
    private JButton searchButton;
    private JLabel totalLabel;

    // Labels for product information
    private JLabel productNameLabel;
    private JLabel productPriceLabel;
    private JLabel productStockLabel;

    public GUIBill() {
        productNameLabel = new JLabel();
        productPriceLabel = new JLabel();
        productStockLabel = new JLabel();
        setTitle("Bill Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with padding
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Top Panel - Contains Search and Product Type Selection
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 hàng, 2 cột, khoảng cách 10px
        topPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Thêm padding trên dưới

        // Product Type Selection Panel
        JPanel productTypePanel = createProductTypePanel();
        productTypePanel.setPreferredSize(new Dimension(380, 80)); // Điều chỉnh kích thước phù hợp

        // Search Panel
        JPanel searchPanel = createSearchPanel();
        searchPanel.setPreferredSize(new Dimension(380, 80)); // Điều chỉnh kích thước phù hợp

        // Add both panels to top panel
        topPanel.add(productTypePanel);
        topPanel.add(searchPanel);

        // Center Panel - Product Info and Input
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Product Information Panel
        JPanel productInfoPanel = createProductInfoPanel();

        // Input Panel for Quantity
        JPanel inputPanel = createInputPanel();

        centerPanel.add(productInfoPanel, BorderLayout.CENTER);
        centerPanel.add(inputPanel, BorderLayout.SOUTH);

        // Right Panel - Bill Display
        JPanel billPanel = createBillPanel();

        // Add all panels to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(billPanel, BorderLayout.EAST);

        // Add main panel to frame
        add(mainPanel);

        // Initialize event handlers
        initializeEventHandlers();
    }

    private JPanel createProductTypePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(createStyledTitledBorder("Choose type production")); // Sử dụng cùng style border với Search Panel

        panel.add(new JLabel("Product Type:"));
        productTypeField = new JTextField(15);
        productTypeField.setEditable(false); // Không cho phép edit trực tiếp
        panel.add(productTypeField);

        // Create combo box for product type selection
        String[] productTypes = {"Book", "Gift", "Notebook", "Stationery", "Textbook"};
        JComboBox<String> productTypeCombo = new JComboBox<>(productTypes);

        // Thêm action listener để cập nhật TextField khi chọn option
        productTypeCombo.addActionListener(e -> {
            String selectedType = (String) productTypeCombo.getSelectedItem();
            productTypeField.setText(selectedType);
        });

        // Style combobox tương tự như search button
        productTypeCombo.setBackground(new Color(70, 130, 180));
        productTypeCombo.setForeground(Color.BLACK);
        panel.add(productTypeCombo);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(createStyledTitledBorder("Search Product"));

        panel.add(new JLabel("Product ID:"));
        productIdField = new JTextField(15);
        panel.add(productIdField);

        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.BLACK);
        panel.add(searchButton);

        return panel;
    }

    // Helper method để tạo border style thống nhất
    private Border createStyledTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180)),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP
        );
    }

    private JPanel createProductInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(createStyledTitledBorder("Product Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize labels with placeholder text
        productNameLabel = new JLabel("Name: ");
        productPriceLabel = new JLabel("Price: ");
        productStockLabel = new JLabel("Available Stock: ");

        // Add components using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(productNameLabel, gbc);

        gbc.gridy = 1;
        panel.add(productPriceLabel, gbc);

        gbc.gridy = 2;
        panel.add(productStockLabel, gbc);

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(createStyledTitledBorder("Create Order"));

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField(10);
        panel.add(quantityField);

        createBillButton = new JButton("Create Bill");
        createBillButton.setBackground(new Color(46, 139, 87));
        createBillButton.setForeground(Color.BLACK);
        panel.add(createBillButton);

        return panel;
    }

    private JPanel createBillPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(createStyledTitledBorder("Bill Details"));
        panel.setPreferredSize(new Dimension(300, 0));

        billArea = new JTextArea();
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(billArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        totalLabel = new JLabel("Total: 0.00 VNĐ");
        totalLabel.setFont(new Font("Dialog", Font.BOLD, 14));
        totalLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(totalLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void initializeEventHandlers() {
        searchButton.addActionListener(e -> searchProduct());
        createBillButton.addActionListener(e -> createBill());
//        TypeProductButton.addActionListener(e -> createTypeProductPanel());

        // Add key listener to productIdField
        productIdField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchProduct();
                }
            }
        });
    }

    private Product getSpesificProduct(String productId, String productType) throws SQLException, ClassNotFoundException {
        Product product = null;

        switch (productType) {
            case "book":
                product = Book.getProductById(productId);
                break;
            case "gift":
                product = Gift.getProductById(productId);
                break;
            case "textbook":
                product = Textbook.getProductById(productId);
                break;
            case "stationery":
                product = Stationery.getProductById(productId);
                break;
            case "notebook":
                product = Notebook.getProductById(productId);
                break;
            default:
                showError("Please choose a type product!");
                clearProductInfo();
                return null;
        }
        return product;
    }

    private void searchProduct() {
        String productId = productIdField.getText().trim();
        String productType = productTypeField.getText().trim().toLowerCase();

        if (productId.isEmpty()) {
            showError("Please enter a Product ID");
            return;
        }

        try {
            Product product = this.getSpesificProduct(productId, productType);

            if (product != null) {
                this.productNameLabel.setText("Name: " + product.getName());
                this.productPriceLabel.setText("Price: " + String.format("%.2f VNĐ", product.getSalePrice()));
                this.productStockLabel.setText("Available Stock: " + product.getQuantity());
            } else {
                showError("Product not found!");
                clearProductInfo();
            }
        } catch (Exception ex) {
            showError("Error searching product: " + ex.getMessage());
            clearProductInfo();
        }
    }

    private void clearProductInfo() {
        productNameLabel.setText("Name: ");
        productPriceLabel.setText("Price: ");
        productStockLabel.setText("Available Stock: ");
    }

    private void createBill() {
        String productId = productIdField.getText().trim();
        String quantityStr = quantityField.getText().trim();
        String productType = productTypeField.getText().trim().toLowerCase();

        if (productId.isEmpty() || quantityStr.isEmpty()) {
            showError("Please fill in all fields");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            String orderId = UUID.randomUUID().toString();

            Product product = this.getSpesificProduct(productId, productType);
            if (product == null) {
                showError("Product not found!");
                return;
            }

            if (product.getQuantity() < quantity) {
                showError("Insufficient stock!");
                return;
            }

            // Calculate total
            double total = Order.calculateInvoiceTotal(productId, quantity, productType);

            // Update stock
            Order.updateProduct(quantity, productId, productType);

            // Save order
            Order order = new Order(productId, orderId, new Date(), quantity, total, "Pending");
            order.addOrder();

            // Update bill display
            updateBillDisplay(order, product, quantity, total);

            // Clear input fields
            quantityField.setText("");

            // Show success message
            JOptionPane.showMessageDialog(this, "Bill created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            showError("Please enter a valid quantity");
        } catch (SQLException | ClassNotFoundException ex) {
            showError("Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void updateBillDisplay(Order order, Product product, int quantity, double total) {
        StringBuilder bill = new StringBuilder();
        bill.append("===== BILL DETAILS =====\n\n");
        bill.append("Order ID: ").append(order.getOrderId()).append("\n");
        bill.append("Date: ").append(new Date()).append("\n\n");
        bill.append("Product Details:\n");
        bill.append("- Name: ").append(product.getName()).append("\n");
        bill.append("- Price: ").append(String.format("%.2f VNĐ", product.getSalePrice())).append("\n");
        bill.append("- Quantity: ").append(quantity).append("\n");
        bill.append("\n======================\n");
        bill.append("Total Amount: ").append(String.format("%.2f VNĐ", total)).append("\n");

        billArea.setText(bill.toString());
        totalLabel.setText("Total: " + String.format("%.2f VNĐ", total));
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new GUIBill().setVisible(true);
        });
    }
}
