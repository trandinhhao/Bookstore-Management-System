package bms.giaodien;
// DONE

import javax.swing.*;
import java.awt.*;

public class GUISupport extends JPanel {

    public GUISupport() {
        // Layout cho JPanel
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // JLabel cho tiêu đề lớn
        JLabel titleLabel = new JLabel("TRANG HỖ TRỢ QUẢN LÝ");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Khoảng cách dưới tiêu đề

        // JLabel hiển thị thông tin hỗ trợ
        JLabel supportLabel = new JLabel("<html><div style='text-align: center;'>"
                + "Nếu bạn có bất cứ thắc mắc nào về hệ thống, vui lòng liên hệ với chúng tôi qua:<br>"
                + "<br>"
                + "<br>"
                + "<b>Email:</b> nhom3oop@ptit.edu.vn<br>"
                + "<b>Hotline:</b> 0123-456-789<br>"
                + "<br>"
                + "<br>"
                + "<br>"
                + "<br>"
                + "Xin chân thành cảm ơn bạn đã tin tưởng sử dụng BMS<br>"
                + "</div></html>");
        supportLabel.setHorizontalAlignment(SwingConstants.CENTER);
        supportLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        // JLabel cho phần chân trang
        JLabel footerLabel = new JLabel("<html><div style='text-align: center; color: gray;'>"
                + "Copyright © 2024 Team 3 OOP, All rights reserved ®<br>"
                + "Ứng dụng phát triển bởi Nhóm 3 Lập trình Hướng đối tượng"
                + "</div></html>");
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        this.add(titleLabel, BorderLayout.NORTH);    // Tiêu đề ở phía trên
        this.add(supportLabel, BorderLayout.CENTER); // Nội dung hỗ trợ ở giữa
        this.add(footerLabel, BorderLayout.SOUTH);   // Chân trang ở dưới cùng
    }

    // TEST
    public static void main(String[] args) {
        JFrame frame = new JFrame("Thông tin hỗ trợ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        frame.add(new GUISupport());

        frame.setVisible(true);
    }
}
