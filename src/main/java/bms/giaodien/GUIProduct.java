package bms.giaodien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIProduct extends JPanel {
    private JPanel mainPanel; // Khu vực để chứa các panel được gọi
    private JComboBox<String> panelSelector; // ComboBox để chọn panel

    public GUIProduct() {
        // Sử dụng BorderLayout để bố trí giao diện
        setLayout(new BorderLayout(10, 10)); // Thêm khoảng cách giữa các thành phần

        // Tạo ComboBox với các lựa chọn panel
        String[] panels = {"bookPanel", "giftPanel", "notebookPanel", "stationeryPanel", "textbookPanel"}; // Tên các panel
        panelSelector = new JComboBox<>(panels);
        panelSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        panelSelector.addActionListener(new PanelSwitcherListener());

        // Khu vực phía trên chứa ComboBox
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa

        controlPanel.add(new JLabel("Chọn Sản Phẩm:"), gbc);

        gbc.gridx = 1;
        controlPanel.add(panelSelector, gbc);

        // Khu vực chính để chứa các panel được gọi
        mainPanel = new JPanel(new CardLayout());

        // Thêm các panel vào khu vực chính
        try {
            mainPanel.add(createBookPanel(), "bookPanel");
        } catch (Exception ex) {
            Logger.getLogger(GUIProduct.class.getName()).log(Level.SEVERE, "Error initializing Book Panel", ex);
        }

        try {
            mainPanel.add(createGiftPanel(), "giftPanel");
        } catch (Exception ex) {
            Logger.getLogger(GUIProduct.class.getName()).log(Level.SEVERE, "Error initializing Gift Panel", ex);
        }

        try {
            mainPanel.add(createNotebookPanel(), "notebookPanel");
        } catch (Exception ex) {
            Logger.getLogger(GUIProduct.class.getName()).log(Level.SEVERE, "Error initializing Notebook Panel", ex);
        }

        try {
            mainPanel.add(createStationeryPanel(), "stationeryPanel");
        } catch (Exception ex) {
            Logger.getLogger(GUIProduct.class.getName()).log(Level.SEVERE, "Error initializing Stationery Panel", ex);
        }

        try {
            mainPanel.add(createTextbookPanel(), "textbookPanel");
        } catch (Exception ex) {
            Logger.getLogger(GUIProduct.class.getName()).log(Level.SEVERE, "Error initializing Textbook Panel", ex);
        }

        // Thêm các thành phần vào JPanel
        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    // ActionListener để chuyển đổi panel
    private class PanelSwitcherListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Lấy lựa chọn từ ComboBox
            String selectedPanel = (String) panelSelector.getSelectedItem();

            // Chuyển đổi panel
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            cardLayout.show(mainPanel, selectedPanel);
        }
    }

    // Tạo panel cho sách
    private JPanel createBookPanel() throws ClassNotFoundException, SQLException {
        GUIProduct_Book bookPanel = new GUIProduct_Book();
        cardPanel.add(supportPanel, "supportPanel");
        panel.add(new JLabel("Đây là Book Panel", JLabel.CENTER), BorderLayout.CENTER);
        return panel;
    }

    // Tạo panel cho quà tặng
    private JPanel createGiftPanel() throws ClassNotFoundException, SQLException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.PINK);
        panel.add(new JLabel("Đây là Gift Panel", JLabel.CENTER), BorderLayout.CENTER);
        return panel;
    }

    // Tạo panel cho vở
    private JPanel createNotebookPanel() throws ClassNotFoundException, SQLException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(new JLabel("Đây là Notebook Panel", JLabel.CENTER), BorderLayout.CENTER);
        return panel;
    }

    // Tạo panel cho dụng cụ học tập
    private JPanel createStationeryPanel() throws ClassNotFoundException, SQLException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.ORANGE);
        panel.add(new JLabel("Đây là Stationery Panel", JLabel.CENTER), BorderLayout.CENTER);
        return panel;
    }

    // Tạo panel cho sách giáo khoa
    private JPanel createTextbookPanel() throws ClassNotFoundException, SQLException {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.GREEN);
        panel.add(new JLabel("Đây là Textbook Panel", JLabel.CENTER), BorderLayout.CENTER);
        return panel;
    }

    // Phương thức main để chạy chương trình
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Switcher");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            // Thêm GUIProduct vào JFrame
            GUIProduct guiProduct = new GUIProduct();
            frame.add(guiProduct);

            // Hiển thị JFrame
            frame.setVisible(true);
        });
    }
}
