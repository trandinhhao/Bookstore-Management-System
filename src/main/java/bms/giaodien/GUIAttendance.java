package bms.giaodien;
// DONE

import bms.connectDB.ConnectMySQL;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUIAttendance extends JPanel {

    private JTextField txtEmployeeId;
    private JTable tableAttendance;
    private DefaultTableModel tableModel;
    private JDateChooser dateChooser;

    public GUIAttendance() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 600));
        initComponents();
    }

    private void initComponents() {
        // Panel chính
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel, BorderLayout.CENTER);

        // Panel trái (Chấm công)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Chấm công", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

        // Panel phải (Kiểm tra)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Kiểm tra chấm công", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        // Giao diện bên trái (Chấm công)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblEmployeeId = new JLabel("Mã nhân viên:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(lblEmployeeId, gbc);

        txtEmployeeId = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        leftPanel.add(txtEmployeeId, gbc);

        JButton btnMarkAttendance = new JButton("Chấm công");
        gbc.gridx = 1;
        gbc.gridy = 1;
        leftPanel.add(btnMarkAttendance, gbc);

        btnMarkAttendance.setBackground(new Color(0, 153, 76));
        btnMarkAttendance.setForeground(Color.BLACK);

        // Nhấn nút chấm công
        btnMarkAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    markAttendance();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GUIAttendance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Giao diện bên phải (Kiểm tra)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        JButton btnCheckAttendance = new JButton("Kiểm tra");
        JButton btnCheckToday = new JButton("Kiểm tra hôm nay");

        btnCheckAttendance.setBackground(new Color(0, 102, 204));
        btnCheckAttendance.setForeground(Color.BLACK);

        btnCheckToday.setBackground(new Color(0, 102, 204));
        btnCheckToday.setForeground(Color.BLACK);

        filterPanel.add(new JLabel("Chọn ngày:"));
        filterPanel.add(dateChooser);
        filterPanel.add(btnCheckAttendance);
        filterPanel.add(btnCheckToday);

        rightPanel.add(filterPanel, BorderLayout.NORTH);

        // Bảng hiển thị
        tableModel = new DefaultTableModel(new String[]{"Mã nhân viên", "Tên nhân viên"}, 0);
        tableAttendance = new JTable(tableModel);
        tableAttendance.setRowHeight(25);
        tableAttendance.getTableHeader().setReorderingAllowed(false);
        tableAttendance.getTableHeader().setBackground(new Color(220, 220, 220));
        JScrollPane scrollPane = new JScrollPane(tableAttendance);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Sự kiện nút kiểm tra
        btnCheckAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkAttendance();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GUIAttendance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Sự kiện nút kiểm tra hôm nay
        btnCheckToday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    checkTodayAttendance();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GUIAttendance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Hàm chấm công
    private void markAttendance() throws ClassNotFoundException {
        String employeeId = txtEmployeeId.getText().trim();
        if (employeeId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection con = ConnectMySQL.getConnection()) {
            // Kiểm tra mã nhân viên hợp lệ
            String checkEmployeeSql = "SELECT name FROM Employee WHERE id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkEmployeeSql);
            checkStmt.setString(1, employeeId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String employeeName = rs.getString("name");

            // Chấm công
            String markAttendanceSql = "INSERT INTO Attendance (id, date, status) VALUES (?, CURDATE(), 'IN') ON DUPLICATE KEY UPDATE status = 'IN'";
            PreparedStatement markStmt = con.prepareStatement(markAttendanceSql);
            markStmt.setString(1, employeeId);
            markStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Chấm công thành công cho: " + employeeName, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi chấm công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm kiểm tra chấm công
    private void checkAttendance() throws ClassNotFoundException {
        java.util.Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);

        try (Connection con = ConnectMySQL.getConnection()) {
            // Lấy danh sách nhân viên chấm công vào ngày được chọn
            String query = "SELECT a.id, e.name FROM Attendance a INNER JOIN Employee e ON a.id = e.id WHERE a.date = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, formattedDate);
            ResultSet rs = stmt.executeQuery();

            // Xóa dữ liệu cũ và thêm dữ liệu mới
            tableModel.setRowCount(0);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                tableModel.addRow(new Object[]{id, name});
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Không có nhân viên nào chấm công vào ngày này.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi kiểm tra chấm công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm kiểm tra chấm công hôm nay
    private void checkTodayAttendance() throws ClassNotFoundException {
        dateChooser.setDate(new java.util.Date());
        checkAttendance();
    }

    // TEST
    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý chấm công");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new GUIAttendance());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
