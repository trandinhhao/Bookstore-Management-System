package bms.giaodien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;

public class ProductManagementGUI extends JFrame {
    private Color primaryColor = new Color(255, 200, 0, 208); // Cyan
    private Color hoverColor = new Color(232, 206, 10, 223);
    private Color textColor = new Color(50, 50, 50);
    private Font menuFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
    private JTable table;
    public ProductManagementGUI() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Khởi tạo JComboBox với các loại sản phẩm
    JComboBox<String> categoryComboBox = new JComboBox<>(new String[] {"Sách", "Quà lưu niệm"});

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setDividerSize(0);
    splitPane.setBorder(null);

    // Truyền categoryComboBox vào phương thức createLeftMenu
    JPanel leftPanel = createLeftMenu(categoryComboBox);
    JPanel contentPanel = createContentPanel();

    splitPane.setLeftComponent(leftPanel);
    splitPane.setRightComponent(contentPanel);
    add(splitPane);

    setSize(1200, 700);
    setLocationRelativeTo(null);
    splitPane.setDividerLocation(200);
}

    private Connection ConnectMySQL() {
    try {
        String url = "jdbc:mysql://localhost:3306/bms";
        String user = "root";
        String password = "admin2003";
        
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to the database successfully!");
        return conn;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}
     private void addFormField(JPanel panel, GridBagConstraints gbc, int x, int y, String label, JComponent field) {
    gbc.gridx = x;
    gbc.gridy = y;
    panel.add(new JLabel(label), gbc);

    gbc.gridx = x + 1;
    panel.add(field, gbc);
}
private void loadProductData(String tableName) {
    Connection conn = ConnectMySQL();
    if (conn == null) return;

    try {
        String query = "SELECT * FROM bms." + tableName;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            Object[] rowData;
            if ("book".equals(tableName)) {
                rowData = new Object[]{
                    rs.getString("id"), 
                    rs.getString("name"),
                    rs.getDouble("costprice"),
                    rs.getDouble("saleprice"),
                    rs.getInt("quantity"),
                    rs.getString("unit"),
                    rs.getString("origin"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getInt("publicationYear"),
                    rs.getString("genre"),
                    rs.getString("language")
                };
            } else if ("gift".equals(tableName)) {
                rowData = new Object[]{
                    rs.getString("id"), 
                    rs.getString("name"),
                    rs.getDouble("cost_price"),
                    rs.getDouble("sale_price"),
                    rs.getInt("quantity"),
                    rs.getString("unit"),
                    rs.getString("origin"),
                    rs.getString("type"),
                    rs.getString("material")
                };
            } else {
                continue;
            }
            model.addRow(rowData);
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu sản phẩm!");
    }
}

   
private JLabel createCircularAvatar() {
        try {
            BufferedImage defaultImage = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = defaultImage.createGraphics();
            g2d.setColor(new Color(100, 100, 100));
            g2d.fillOval(0, 0, 80, 80);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            g2d.drawString("A", 30, 50);
            g2d.dispose();

            BufferedImage circleBuffer = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new Ellipse2D.Float(0, 0, 80, 80));
            g2.drawImage(defaultImage, 0, 0, 80, 80, null);
            g2.dispose();

            return new JLabel(new ImageIcon(circleBuffer));
        } catch (Exception e) {
            e.printStackTrace();
            return new JLabel("ADMIN");
        }
    }
    
   private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(menuFont);
        button.setForeground(textColor);
        button.setBackground(primaryColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setMaximumSize(new Dimension(180, 40));
        button.setPreferredSize(new Dimension(180, 40));

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setContentAreaFilled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(primaryColor);
                button.setContentAreaFilled(false);
            }
        });

        return button;
    }

    private JPanel createEnhancedTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"Mã SP", "Tên SP", "Giá nhập", "Giá bán",
                "Số lượng", "Đơn vị tính", "Xuất xứ", "Nhà xuất bản", "Tác giả",
                "Năm xuất bản", "Thể loại", "Ngôn ngữ"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
private JPanel createLeftMenu(JComboBox<String> categoryComboBox) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(primaryColor);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel adminPanel = new JPanel();
    adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
    adminPanel.setBackground(primaryColor);
    adminPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    JLabel avatarLabel = createCircularAvatar();
    avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    adminPanel.add(avatarLabel);

    JLabel adminLabel = new JLabel("ADMIN");
    adminLabel.setFont(titleFont);
    adminLabel.setForeground(textColor);
    adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    adminPanel.add(Box.createVerticalStrut(10));
    adminPanel.add(adminLabel);

    JLabel idLabel = new JLabel("ID: ADMIN");
    idLabel.setFont(menuFont);
    idLabel.setForeground(textColor);
    idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    adminPanel.add(Box.createVerticalStrut(5));
    adminPanel.add(idLabel);

    panel.add(adminPanel);
    panel.add(Box.createVerticalStrut(20));

    String[] menuItems = {"Sản Phẩm", "Hóa Đơn", "Khách Hàng", "Nhân Viên", "Nhà Cung Cấp", "Thống Kê"};

    for (String menuItem : menuItems) {
        JButton btn = createMenuButton(menuItem);
        if (menuItem.equals("Sản Phẩm")) {
            btn.addActionListener(e -> {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                if ("Sách".equals(selectedCategory)) {
                    loadProductData("book");
                } else if ("Quà lưu niệm".equals(selectedCategory)) {
                    loadProductData("gift");
                }
            });
        }
        panel.add(btn);
        panel.add(Box.createVerticalStrut(10));
    }

    return panel;
}

private JPanel createEnhancedInputForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Thiết lập các trường nhập liệu
        addFormField(panel, gbc, 0, 0, "Mã sản phẩm:", new JTextField(10));
        addFormField(panel, gbc, 0, 1, "Tên sản phẩm:", new JTextField(10));
        addFormField(panel, gbc, 0, 2, "Giá nhập:", new JTextField(10));
        addFormField(panel, gbc, 0, 3, "Số lượng:", new JTextField(10));

        // Cột 2
        String[] categories = {"Sách", "Quà lưu niệm", "Vở", "Sản phẩm", "Dụng cụ học tập", "Sách giáo khoa"};
        
        String[] suppliers = {"NXB Kim Đồng"};
        addFormField(panel, gbc, 2, 0, "Nhà cung cấp:", new JComboBox<>(suppliers));

        addFormField(panel, gbc, 2, 1, "Đơn giá:", new JTextField(10));

        // Date field with button
        JPanel datePanel = new JPanel(new BorderLayout());
        JTextField dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 8);
        JButton dateButton = new JButton("...");
        datePanel.add(dateField, BorderLayout.CENTER);
        datePanel.add(dateButton, BorderLayout.EAST);
        addFormField(panel, gbc, 2, 3, "Ngày nhập:", datePanel);
        
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(new JButton("Hủy"));
        buttonPanel.add(new JButton("Sửa"));
        buttonPanel.add(new JButton("Làm mới"));
        buttonPanel.add(new JButton("Lưu"));

        gbc.gridx = 5;
        gbc.gridy = 0;
        panel.add(buttonPanel, gbc);
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        addFormField(panel, gbc, 2, 1, "Loại sản phẩm:", categoryComboBox);

// Thêm ActionListener để thay đổi bảng dựa trên lựa chọn loại sản phẩm
        categoryComboBox.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            if ("Sách".equals(selectedCategory)) {
                loadProductData("book");
            } else if ("Quà lưu niệm".equals(selectedCategory)) {
                 loadProductData("gift");
            }
        });

        return panel;
    }

  
  private JPanel createContentPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);

    // Title Panel
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(primaryColor);
    titlePanel.setPreferredSize(new Dimension(0, 50));
    JLabel titleLabel = new JLabel("QUẢN LÝ SẢN PHẨM");
    titleLabel.setFont(titleFont);
    titleLabel.setForeground(textColor);
    titlePanel.add(titleLabel);

    panel.add(titlePanel, BorderLayout.NORTH);

    // Main Content
    JPanel mainContent = new JPanel(new BorderLayout(10, 10));
    mainContent.setBackground(Color.WHITE);
    mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Input Form
    JPanel inputForm = createEnhancedInputForm();
    mainContent.add(inputForm, BorderLayout.NORTH); // Thêm form nhập liệu vào phần trên của mainContent

    // Table
    JPanel tablePanel = createEnhancedTablePanel();
    mainContent.add(tablePanel, BorderLayout.CENTER); // Thêm bảng vào phần trung tâm của mainContent

    panel.add(mainContent, BorderLayout.CENTER);

    return panel;
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                UIManager.put("Button.arc", 10);
                UIManager.put("Component.arc", 10);
                UIManager.put("TextComponent.arc", 10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ProductManagementGUI gui = new ProductManagementGUI();
            gui.setVisible(true);
            gui.loadProductData("book"); // Tải dữ liệu từ cơ sở dữ liệu khi GUI được hiển thị
        });
    }
}
