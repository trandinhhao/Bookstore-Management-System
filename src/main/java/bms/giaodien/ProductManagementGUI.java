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
