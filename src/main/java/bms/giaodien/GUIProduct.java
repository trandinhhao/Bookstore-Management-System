package bms.giaodien;
// DONE

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIProduct extends JPanel {

    private JPanel mainPanel;
    private JComboBox<String> panelSelector;

    public GUIProduct() {
        setLayout(new BorderLayout(10, 10));

        String[] panels = {"Sách", "Quà tặng", "Vở viết", "Dụng cụ học tập", "Sách giáo khoa"};
        panelSelector = new JComboBox<>(panels);
        panelSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        panelSelector.addActionListener(new PanelSwitcherListener());

        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        controlPanel.add(new JLabel("Chọn Sản Phẩm:"), gbc);

        gbc.gridx = 1;
        controlPanel.add(panelSelector, gbc);

        mainPanel = new JPanel(new CardLayout());

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

        add(controlPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private class PanelSwitcherListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedPanel = (String) panelSelector.getSelectedItem();
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            //
            switch (selectedPanel) {
                case "Sách":
                    cardLayout.show(mainPanel, "bookPanel");
                    break;
                case "Quà tặng":
                    cardLayout.show(mainPanel, "giftPanel");
                    break;
                case "Vở viết":
                    cardLayout.show(mainPanel, "notebookPanel");
                    break;
                case "Dụng cụ học tập":
                    cardLayout.show(mainPanel, "stationeryPanel");
                    break;
                case "Sách giáo khoa":
                    cardLayout.show(mainPanel, "textbookPanel");
                    break;
                default:
                    Logger.getLogger(GUIProduct.class.getName()).log(Level.WARNING, "Unknown panel: " + selectedPanel);
            }

        }
    }

    private JPanel createBookPanel() throws ClassNotFoundException, SQLException {
        GUIProduct_Book bookPanel = new GUIProduct_Book();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(bookPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGiftPanel() throws ClassNotFoundException, SQLException {
        GUIProduct_Gift giftPanel = new GUIProduct_Gift();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(giftPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createNotebookPanel() throws ClassNotFoundException, SQLException {
        GUIProduct_Notebook notebookPanel = new GUIProduct_Notebook();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(notebookPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStationeryPanel() throws ClassNotFoundException, SQLException {
        GUIProduct_Stationery stationeryPanel = new GUIProduct_Stationery();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(stationeryPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTextbookPanel() throws ClassNotFoundException, SQLException {
        GUIProduct_Textbook textbookPanel = new GUIProduct_Textbook();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textbookPanel, BorderLayout.CENTER);
        return panel;
    }

    // TEST
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panel Switcher");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            GUIProduct guiProduct = new GUIProduct();
            frame.add(guiProduct);

            frame.setVisible(true);
        });
    }
}
