package bms.giaodien;

import javax.swing.*;
import java.awt.*;

public class GUIWelcome extends JPanel {

    public GUIWelcome() {
        // Đặt layout cho JPanel
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Khoảng cách xung quanh

        // Tiêu đề
        JLabel titleLabel = new JLabel("CHÀO MỪNG BẠN ĐẾN VỚI HỆ THỐNG QUẢN LÝ NHÀ SÁCH");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Nội dung hiển thị
        String[][] instructions = {
                {"1. Để kiểm tra quản lý Sản phẩm", "Nhấn vào nút \"Sản Phẩm\""},
                {"2. Để kiểm tra quản lý Nhân Viên", "Nhấn vào nút \"Nhân Viên\""},
                {"3. Để kiểm tra quản lý Khách Hàng", "Nhấn vào nút \"Khách Hàng\""},
                {"4. Để kiểm tra quản lý Chấm Công", "Nhấn vào nút \"Chấm Công\""},
                {"5. Để tạo Hóa Đơn", "Nhấn vào nút \"Hóa Đơn\""},
                {"6. Để kiểm tra quản lý Kho Hàng", "Nhấn vào nút \"Quản Lý Kho\""},
                {"7. Để kiểm tra Thống Kê", "Nhấn vào nút \"Thống Kê\""},
                {"8. Liên hệ với chúng tôi", "Nhấn vào nút \"Trợ Giúp\""},
                {"9. Để thoát khỏi chương trình", "Nhấn vào nút \"Đăng Xuất\""}
        };

        // Panel chứa nội dung
        JPanel contentPanel = new JPanel(new GridLayout(instructions.length, 2, 10, 10)); // Lưới có 2 cột
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Khoảng cách bên trong
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm từng dòng nội dung vào contentPanel
        for (String[] instruction : instructions) {
            JLabel leftLabel = new JLabel(instruction[0]);
            leftLabel.setFont(new Font("Arial", Font.PLAIN, 16));

            JLabel rightLabel = new JLabel(instruction[1]);
            rightLabel.setFont(new Font("Arial", Font.PLAIN, 16));

            contentPanel.add(leftLabel);
            contentPanel.add(rightLabel);
        }

        // Thêm các thành phần vào JPanel chính
        this.add(titleLabel, BorderLayout.NORTH);         // Tiêu đề ở trên
        this.add(contentPanel, BorderLayout.CENTER);      // Nội dung ở giữa
    }

    // Hàm main để test GUIWelcome
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hệ Thống Quản Lý Nhà Sách");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        // Thêm GUIWelcome vào JFrame
        frame.add(new GUIWelcome());

        // Hiển thị JFrame
        frame.setVisible(true);
    }
}
