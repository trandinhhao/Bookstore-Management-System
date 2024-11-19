package bms.giaodien;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import bms.giaodien.GUICustomer;
import bms.giaodien.GUIEmployee;

public class TEST extends JFrame {

    private Color primaryColor = new Color(195, 199, 243, 255);
    private Color textColor = new Color(50, 50, 50);
    private Font menuFont = new Font("Segoe UI", Font.BOLD, 14);
    private Font idFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
    private String username;
    private String id;
    private JPanel cardPanel = new JPanel(new CardLayout());

    public TEST(String username, String id) {
        this.username = username;
        this.id = id;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Menu");

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

    private JPanel createLeftMenu() { // OK
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

        JLabel adminLabel = new JLabel("Xin chào, " + username);
        adminLabel.setFont(titleFont);
        adminLabel.setForeground(textColor);
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminPanel.add(Box.createVerticalStrut(10));
        adminPanel.add(adminLabel);

        JLabel idLabel = new JLabel("ID: " + id);
        idLabel.setFont(idFont);
        idLabel.setForeground(textColor);
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminPanel.add(Box.createVerticalStrut(5));
        adminPanel.add(idLabel);

        panel.add(adminPanel);
        panel.add(Box.createVerticalStrut(20));

        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Sách", "Quà lưu niệm", "Vở", "Dụng cụ học tập", "Sách giáo khoa"});

        String[] menuItems = {"Sản Phẩm", "Nhân Viên", "Khách Hàng", "Nhà Cung Cấp", "Hóa Đơn", "Quản Lý Kho", "Thống Kê", "Trợ Giúp", "Đăng Xuất"};
        ArrayList<JButton> allButtons = new ArrayList<>();
        panel.add(cardPanel, BorderLayout.CENTER);
        //
        // TẠO CÁC PANEL KHÁC NHAU, MỌI NGƯỜI TỰ THÊM VÀO ĐÂY----------------------
        //
        JPanel defaultPanel = new JPanel();
        defaultPanel.setBackground(Color.lightGray);
        defaultPanel.add(new JLabel("Giao diện lúc mới vào"));
        cardPanel.add(defaultPanel, "default");
        // 2 panel test cho mọi người hiểu
        GUICustomer customerPanel = new GUICustomer();
        cardPanel.add(customerPanel, "customerPanel");

        //
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.LIGHT_GRAY);
        GUIEmployee employeePanel = new GUIEmployee();
        cardPanel.add(employeePanel, "employeePanel");
        // Ví dụ product nè
        try {
            GUIProduct productPanel = new GUIProduct();
            cardPanel.add(productPanel, "productPanel");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(TEST.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Ví dụ support nè
        GUISupport supportPanel = new GUISupport();
        cardPanel.add(supportPanel, "supportPanel");
        for (String menuItem : menuItems) {
            JButton btn = createMenuButton(menuItem);
            allButtons.add(btn);
            btn.addActionListener(e -> {
                // Lấy tên của nút vừa được nhấn
                String buttonText = btn.getText();

                // Kiểm tra tên nút và thực hiện hành động tương ứng
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                if (buttonText.equals("Sản Phẩm")) {
                    cl.show(cardPanel, "productPanel");
                } else if (buttonText.equals("Nhân Viên")) {
                    cl.show(cardPanel, "employeePanel");
                } else if (buttonText.equals("Khách Hàng")) {
                    cl.show(cardPanel, "customerPanel");
                } else if (buttonText.equals("Nhà Cung Cấp")) {
                    cl.show(cardPanel, "Panel 2");
                } else if (buttonText.equals("Hóa Đơn")) {
                    cl.show(cardPanel, "Panel 1");
                } else if (buttonText.equals("Quản Lý Kho")) {
                    cl.show(cardPanel, "Panel 2");
                } else if (buttonText.equals("Thống Kê")) {
                    cl.show(cardPanel, "Panel 1");
                } else if (buttonText.equals("Trợ Giúp")) {
                    cl.show(cardPanel, "supportPanel");
                } else if (buttonText.equals("Đăng Xuất")) {
                    //...
                    UIManager.put("Button.focusPainted", false);
                    int response = JOptionPane.showConfirmDialog(
                            null,
                            "Bạn có chắc chắn muốn đăng xuất không?",
                            "Xác nhận đăng xuất",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (response == JOptionPane.YES_OPTION) {
                        // Đóng form hiện tại
                        SwingUtilities.getWindowAncestor(btn).dispose();
                        new LoginForm().setVisible(true);
                    }
                    // Nếu chọn "Không", hộp thoại sẽ tự động đóng mà không làm gì thêm
                }
            });
            panel.add(btn);
            panel.add(Box.createVerticalStrut(10));
        }
        // hieu ung cac nut
        Color buttonColor = new Color(200, 168, 233); // mau nut
        Color pressedColor = new Color(180, 138, 213); // mau khi an nut
        Color hoverColor = new Color(227, 170, 221, 255);// mau khi hover
        for (JButton button : allButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JButton b1 : allButtons) {
                        b1.setBackground(buttonColor);
                        b1.setContentAreaFilled(true);
                    }
                    button.setBackground(pressedColor);
                    button.setContentAreaFilled(true);
                }
            });
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (button.getBackground() != pressedColor) {
                        button.setBackground(hoverColor);
                        button.setContentAreaFilled(true);
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (button.getBackground() != pressedColor) {
                        button.setBackground(buttonColor);
                        button.setContentAreaFilled(true);
                    }
                }
            });
        }
        return panel;
    }

    private JButton createMenuButton(String text) { // OK
        JButton button = new JButton(text);
        button.setFont(menuFont);
        button.setForeground(textColor);
        Color buttonColor = new Color(200, 168, 233);
        button.setBackground(buttonColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setMaximumSize(new Dimension(180, 40));
        button.setPreferredSize(new Dimension(180, 40));

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);

        return button;
    }

    private JLabel createCircularAvatar() { // OK
        try {
            BufferedImage defaultImage = ImageIO.read(new File("C:\\Users\\PC\\Desktop\\BMS\\BMS\\src\\main\\java\\bms\\giaodien\\j97.jpg"));
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
            return new JLabel("ADMIN");
        }
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

        panel.add(cardPanel, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                TEST gui = new TEST("Admin", "001");
                gui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
