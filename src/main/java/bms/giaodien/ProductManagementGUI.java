package bms.giaodien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
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

    public ProductManagementGUI() {
        setTitle("CỬA HÀNG THỜI TRANG SMILE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);

        JPanel leftPanel = createLeftMenu();
        JPanel contentPanel = createContentPanel();

        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(contentPanel);

        add(splitPane);

        setSize(1200, 700);
        setLocationRelativeTo(null);
        splitPane.setDividerLocation(200);
    }

    private JLabel createCircularAvatar() {
        try {
            // Tạo ảnh avatar mặc định nếu không tìm thấy file
            BufferedImage defaultImage = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = defaultImage.createGraphics();
            g2d.setColor(new Color(100, 100, 100));
            g2d.fillOval(0, 0, 80, 80);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            g2d.drawString("A", 30, 50);
            g2d.dispose();

            // Tạo avatar hình tròn
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

        // Tạo icon mặc định nếu không tìm thấy file
        ImageIcon defaultIcon = createDefaultIcon(text.substring(0, 1));
        button.setIcon(defaultIcon);
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

    private ImageIcon createDefaultIcon(String letter) {
        BufferedImage img = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(textColor);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        int x = (24 - fm.stringWidth(letter)) / 2;
        int y = ((24 - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(letter, x, y);
        g2d.dispose();
        return new ImageIcon(img);
    }

    private JPanel createEnhancedInputForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Thiết lập các trường nhập liệu
        addFormField(panel, gbc, 0, 0, "Mã sản phẩm:", new JTextField("SP012", 10));
        addFormField(panel, gbc, 0, 1, "Tên sản phẩm:", new JTextField(10));
        addFormField(panel, gbc, 0, 2, "Giá nhập:", new JTextField("0", 10));
        addFormField(panel, gbc, 0, 3, "Số lượng:", new JTextField("0", 10));

        String[] sizes = {"XL", "L", "M", "S"};
        addFormField(panel, gbc, 0, 4, "Size:", new JComboBox<>(sizes));

        // Cột 2
        String[] categories = {"Áo Khoác"};
        addFormField(panel, gbc, 2, 0, "Loại sản phẩm:", new JComboBox<>(categories));

        String[] suppliers = {"Phương Đông"};
        addFormField(panel, gbc, 2, 1, "Nhà cung cấp:", new JComboBox<>(suppliers));

        addFormField(panel, gbc, 2, 2, "Đơn giá:", new JTextField(10));

        // Date field with button
        JPanel datePanel = new JPanel(new BorderLayout());
        JTextField dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()), 8);
        JButton dateButton = new JButton("...");
        datePanel.add(dateField, BorderLayout.CENTER);
        datePanel.add(dateButton, BorderLayout.EAST);
        addFormField(panel, gbc, 2, 3, "Ngày nhập:", datePanel);

        // Cột 3 và hình ảnh
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 150));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        JButton chooseImageButton = new JButton("Chọn ảnh");
        imagePanel.add(chooseImageButton, BorderLayout.SOUTH);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        panel.add(imagePanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(new JButton("Hủy"));
        buttonPanel.add(new JButton("Sửa"));
        buttonPanel.add(new JButton("Làm mới"));
        buttonPanel.add(new JButton("Lưu"));

        gbc.gridx = 5;
        gbc.gridy = 0;
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

    private JPanel createEnhancedTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Search options
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] searchOptions = {"Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm",
                "Nhà cung cấp", "Chất liệu", "Màu sắc", "Size", "Theo giá"};
        searchPanel.add(new JLabel("Tìm theo:"));
        for (String option : searchOptions) {
            searchPanel.add(new JCheckBox(option));
        }

        JPanel searchBarPanel = new JPanel();
        searchBarPanel.add(new JTextField(20));
        searchBarPanel.add(new JButton("Tìm kiếm"));
        searchBarPanel.add(new JButton("Làm mới"));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(searchBarPanel, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"STT", "Mã SP", "Tên SP", "Loại SP", "Giá nhập", "Số lượng",
                "Ngày nhập", "Nhà cung cấp", "Chất liệu", "Size", "Màu sắc",
                "Đơn vị tính", "Khuyến mãi", "VAT", "Tình trạng", "Giá bán"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLeftMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Admin Panel
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
        adminPanel.setBackground(primaryColor);
        adminPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Admin Avatar
        JLabel avatarLabel = createCircularAvatar();
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminPanel.add(avatarLabel);

        // Admin Text
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

        // Menu Items
        String[] menuItems = {"Hóa Đơn", "Sản Phẩm", "Khách Hàng", "Nhân Viên",
                "Nhà Cung Cấp", "Khuyến Mãi", "Thống Kê"};

        for (String menuItem : menuItems) {
            JButton btn = createMenuButton(menuItem);
            panel.add(btn);
            panel.add(Box.createVerticalStrut(10));
        }

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
        mainContent.add(inputForm, BorderLayout.NORTH);

        // Table
        JPanel tablePanel = createEnhancedTablePanel();
        mainContent.add(tablePanel, BorderLayout.CENTER);

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
            new ProductManagementGUI().setVisible(true);
        });
    }
}