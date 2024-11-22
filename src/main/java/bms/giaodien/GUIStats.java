package bms.giaodien;

import bms.system_management.Stats;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import javax.swing.text.DefaultCaret;

public class GUIStats extends JFrame {
    private JPanel mainPanel, controlPanel, reportPanel, datePanel, productPanel;
    private JDateChooser startDateChooser, endDateChooser;
    private JButton generateReportBtn;
    private JTextArea reportTextArea;
    private JTextField productTextField;
    private JLabel startDateLabel, endDateLabel, productType;
    private JPanel productSelectPanel;
    private JComboBox<String> productTypeComboBox;
    
    public GUIStats() {
        initComponents();
        setupLayout();
        setupListeners();
    }
    
    private void initComponents() {
        // Thiết lập frame
        setTitle("Thống Kê Doanh Thu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        // Khởi tạo các panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel = new JPanel(new BorderLayout(10, 10));
        datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        reportPanel = new JPanel(new BorderLayout());
        productPanel = new JPanel(new BorderLayout());
        
        // Khởi tạo các components
        productType = new JLabel("Chọn loại sản phẩm:");
        startDateLabel = new JLabel("Từ ngày:");
        endDateLabel = new JLabel("Đến ngày:");
        startDateChooser = new JDateChooser();
        endDateChooser = new JDateChooser();
        startDateChooser.setPreferredSize(new Dimension(150, 30));
        endDateChooser.setPreferredSize(new Dimension(150, 30));
        
        generateReportBtn = new JButton("Tạo Báo Cáo");
        generateReportBtn.setPreferredSize(new Dimension(150, 35));
        generateReportBtn.setFont(new Font("Arial", Font.BOLD, 14));
        
        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        
        // Auto-scroll to bottom
        DefaultCaret caret = (DefaultCaret)reportTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        productSelectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        productTypeComboBox = new JComboBox<>(new String[]{"Book", "Gift", "Textbook", "Notebook", "Stationery"});
        productTextField = new JTextField(15);
        productTextField.setEditable(false);

        // Thêm listener cho ComboBox
        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) productTypeComboBox.getSelectedItem();
                productTextField.setText(selectedType);
            }
        });
    }
    
    private void setupLayout() {
        // Date Panel
        datePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Chọn khoảng thời gian",
            TitledBorder.LEFT,
            TitledBorder.TOP));
            
        datePanel.add(startDateLabel);
        datePanel.add(startDateChooser);
        datePanel.add(endDateLabel);
        datePanel.add(endDateChooser);
        datePanel.add(generateReportBtn);
        
        // Control Panel
        controlPanel.add(datePanel, BorderLayout.CENTER);
        
        //  Panel
        reportPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Kết quả thống kê",
            TitledBorder.LEFT,
            TitledBorder.TOP));
        reportPanel.add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
        
        // Main Panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(reportPanel, BorderLayout.CENTER);
        
        productSelectPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Chọn loại sản phẩm",
            TitledBorder.LEFT,
            TitledBorder.TOP));
        
        productSelectPanel.add(productType);  // Label đã khai báo trước đó
        productSelectPanel.add(productTypeComboBox);
        productSelectPanel.add(productTextField);

        // Sửa lại control panel để thêm product select panel
        controlPanel.setLayout(new GridLayout(2, 1, 0, 10));  // Đổi layout thành GridLayout
        controlPanel.add(datePanel);
        controlPanel.add(productSelectPanel);
        
        // Add to frame
        add(mainPanel);
    }
    
    private void setupListeners() {
        generateReportBtn.addActionListener((ActionEvent e) -> generateReport());
    }
    
    private void generateReport() {
        Date startDate = startDateChooser.getDate();
        Date endDate = endDateChooser.getDate();
        
        if (startDate == null || endDate == null) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc", 
                "Lỗi Nhập Liệu", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (startDate.after(endDate)) {
            JOptionPane.showMessageDialog(this, 
                "Ngày bắt đầu phải trước ngày kết thúc", 
                "Lỗi Ngày Tháng", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String productType = productTextField.getText().trim().toLowerCase();
            // backend
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            System.out.println(productType);
            Stats report = Stats.generateAdvancedSalesReport(startDate, endDate, productType);
            
            reportTextArea.setText(report.getContent());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi cơ sở dữ liệu: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi kết nối: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GUIStats().setVisible(true);
        });
    }
}