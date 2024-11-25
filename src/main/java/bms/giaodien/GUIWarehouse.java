package bms.giaodien;

import bms.connectDB.ConnectMySQL;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GUIWarehouse extends JPanel {

    private JTable table;

    public GUIWarehouse() throws SQLException, ClassNotFoundException {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Tạo bảng hiển thị
        JPanel tablePanel = createUnifiedInventoryTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Load dữ liệu vào bảng
        loadUnifiedInventoryData();
    }

    private JPanel createUnifiedInventoryTablePanel() {
        // Panel chứa toàn bộ giao diện (header không bị đóng khung)
        JPanel outerPanel = new JPanel(new BorderLayout());

        // Panel chứa header (không đóng khung)
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel headerLabel = new JLabel("Tổng hợp kho hàng", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        outerPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel chứa nút Refresh
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            try {
                loadUnifiedInventoryData();  // Tải lại dữ liệu
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(refreshButton);
        outerPanel.add(buttonPanel, BorderLayout.EAST);

        // Tạo panel chính cho bảng và footer, thêm padding xung quanh
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Thêm padding cho các phần bên trong (trên, dưới, trái, phải)

        // Tạo bảng dữ liệu
        String[] columnNames = {"Số thứ tự", "Mã sản phẩm", "Tên sản phẩm", "Giá nhập", "Giá bán", "Số lượng", "Loại sản phẩm"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Đóng khung bảng
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Thiết lập chiều cao bảng để giảm bớt độ cao
        table.setPreferredScrollableViewportSize(new Dimension(750, 300)); // Giảm chiều cao bảng xuống 300px
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel footer chứa thông tin số lượng sản phẩm ít hơn 100
        JPanel footerPanel = new JPanel(new GridLayout(1, 3, 10, 10));  // Sử dụng GridLayout để quản lý các item
        footerPanel.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5)); // Thêm padding cho footer

        // Tạo các phần tử footer
        footerPanel.add(createFooterItem("Số lượng sản phẩm ít hơn 100", new Color(227, 170, 221, 255)));
        footerPanel.add(createFooterItem("Số lượng sản phẩm ít hơn 50", new Color(209, 104, 199, 255)));
        footerPanel.add(createFooterItem("Số lượng sản phẩm ít hơn 10", new Color(204, 28, 183, 255)));

        contentPanel.add(footerPanel, BorderLayout.SOUTH);
        outerPanel.add(contentPanel, BorderLayout.CENTER);

        return outerPanel;
    }

    // Hàm hỗ trợ tạo các phần tử footer với padding
    private JPanel createFooterItem(String labelText, Color color) {
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setPreferredSize(new Dimension(20, 20)); // Ô vuông nhỏ
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel infoLabel = new JLabel("  " + labelText);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14)); // In đậm

        JPanel footerItemPanel = new JPanel(new BorderLayout());
        footerItemPanel.add(colorPanel, BorderLayout.WEST);
        footerItemPanel.add(infoLabel, BorderLayout.CENTER);

        // Thêm padding cho panel footerItemPanel
        footerItemPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Thêm padding (top, left, bottom, right)

        return footerItemPanel;
    }

    private void loadUnifiedInventoryData() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();
        if (conn == null) {
            throw new SQLException("Không thể kết nối cơ sở dữ liệu!");
        }

        // Gộp dữ liệu từ tất cả các bảng
        String query = """
                SELECT id, name, cost_price, sale_price, quantity, 'Sách' AS category FROM bms.book
                UNION ALL
                SELECT id, name, cost_price, sale_price, quantity, 'Quà lưu niệm' AS category FROM bms.gift
                UNION ALL
                SELECT id, name, cost_price, sale_price, quantity, 'Vở' AS category FROM bms.notebook
                UNION ALL
                SELECT id, name, cost_price, sale_price, quantity, 'Dụng cụ học tập' AS category FROM bms.stationery
                UNION ALL
                SELECT id, name, cost_price, sale_price, quantity, 'Sách giáo khoa' AS category FROM bms.textbook
                ORDER BY quantity ASC;
                """;

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        int rowNumber = 1;

        while (rs.next()) {
            Object[] rowData = {
                rowNumber++,
                rs.getString("id"),
                rs.getString("name"),
                rs.getDouble("cost_price"),
                rs.getDouble("sale_price"),
                rs.getInt("quantity"),
                rs.getString("category")
            };
            model.addRow(rowData);
        }

        rs.close();
        stmt.close();

        table.setDefaultRenderer(Object.class, new TableCellRenderer() {
            DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component comp = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Kiểm tra nếu giá trị quantity < 10, < 50, < 100, thay đổi màu nền của dòng
                int quantity = (int) table.getValueAt(row, 5); // Cột quantity ở chỉ số 5
                if (quantity < 10) {
                    comp.setBackground(new Color(204, 28, 183, 255));  // Màu đỏ cho hàng có số lượng < 10
                    comp.setForeground(Color.BLACK); // Đặt màu chữ là trắng
                } else if (quantity < 50) {
                    comp.setBackground(new Color(209, 104, 199, 255));  // Màu tím cho hàng có số lượng < 50
                    comp.setForeground(Color.BLACK); // Đặt màu chữ là trắng
                } else if (quantity < 100) {
                    comp.setBackground(new Color(227, 170, 221, 255));  // Màu xanh lá cho hàng có số lượng < 100
                    comp.setForeground(Color.BLACK); // Đặt màu chữ là trắng
                } else {
                    comp.setBackground(Color.WHITE); // Màu nền trắng cho hàng có số lượng >= 100
                    comp.setForeground(Color.BLACK); // Màu chữ đen
                }

                return comp;
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set the look and feel to match the system
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // Create a new JFrame to hold the panel
                JFrame frame = new JFrame("Tổng hợp kho hàng");

                // Create an instance of the GUIWarehouse panel
                GUIWarehouse warehouse = new GUIWarehouse();

                // Add the panel to the frame
                frame.add(warehouse);

                // Set default close operation
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // Pack the frame to fit the preferred size of its components
                frame.setSize(800, 600);

                // Center the frame on the screen
                frame.setLocationRelativeTo(null);

                // Make the frame visible
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
