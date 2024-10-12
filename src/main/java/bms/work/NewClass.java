import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewClass extends JFrame {
    // Khai báo các thành phần GUI
    private JTextField txtMaSach, txtTenSach, txtTacGia, txtNamXB, txtSoLuong, txtDonGia;
    private JComboBox<String> cbMaNXB, cbMaTheLoai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnNhapExcel, btnXuatExcel;
    private JTable tableBooks;
    private DefaultTableModel tableModel;

    public NewClass() {
        setTitle("Quản Lý Cửa Hàng Sách");
        setSize(800, 600);
        setLocationRelativeTo(null); // Căn giữa màn hình
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Tạo panel chính
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel thông tin sách
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(6, 2));

        panelInfo.add(new JLabel("Mã Sách:"));
        txtMaSach = new JTextField();
        panelInfo.add(txtMaSach);

        panelInfo.add(new JLabel("Mã NXB:"));
        cbMaNXB = new JComboBox<>(new String[]{"NXB_Tre", "NXB_HCM", "NXB_QuocGia"});
        panelInfo.add(cbMaNXB);

        panelInfo.add(new JLabel("Mã Thể Loại:"));
        cbMaTheLoai = new JComboBox<>(new String[]{"Văn Học", "Khoa Học", "Lịch Sử"});
        panelInfo.add(cbMaTheLoai);

        panelInfo.add(new JLabel("Tên Sách:"));
        txtTenSach = new JTextField();
        panelInfo.add(txtTenSach);

        panelInfo.add(new JLabel("Tác Giả:"));
        txtTacGia = new JTextField();
        panelInfo.add(txtTacGia);

        panelInfo.add(new JLabel("Năm XB:"));
        txtNamXB = new JTextField();
        panelInfo.add(txtNamXB);

        panelInfo.add(new JLabel("Số Lượng:"));
        txtSoLuong = new JTextField();
        panelInfo.add(txtSoLuong);

        panelInfo.add(new JLabel("Đơn Giá:"));
        txtDonGia = new JTextField();
        panelInfo.add(txtDonGia);

        // Panel nút chức năng
        JPanel panelButtons = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm Mới");
        btnNhapExcel = new JButton("Nhập Excel");
        btnXuatExcel = new JButton("Xuất Excel");

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);
        panelButtons.add(btnNhapExcel);
        panelButtons.add(btnXuatExcel);

        // Bảng hiển thị sách
        String[] columns = {"Mã Sách", "Mã NXB", "Thể Loại", "Tác Giả", "Tên Sách", "Năm XB", "Số Lượng", "Đơn Giá"};
        tableModel = new DefaultTableModel(columns, 0);
        tableBooks = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableBooks);

        // Thêm các panel vào JFrame
        panel.add(panelInfo, BorderLayout.NORTH);
        panel.add(panelButtons, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        add(panel);

        // Thêm sự kiện nút
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });

        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    // Hàm thêm sách
    private void addBook() {
        String maSach = txtMaSach.getText();
        String maNXB = (String) cbMaNXB.getSelectedItem();
        String theLoai = (String) cbMaTheLoai.getSelectedItem();
        String tenSach = txtTenSach.getText();
        String tacGia = txtTacGia.getText();
        String namXB = txtNamXB.getText();
        String soLuong = txtSoLuong.getText();
        String donGia = txtDonGia.getText();

        Object[] row = {maSach, maNXB, theLoai, tacGia, tenSach, namXB, soLuong, donGia};
        tableModel.addRow(row);
    }

    // Hàm sửa thông tin sách
    private void updateBook() {
        int selectedRow = tableBooks.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.setValueAt(txtMaSach.getText(), selectedRow, 0);
            tableModel.setValueAt(cbMaNXB.getSelectedItem(), selectedRow, 1);
            tableModel.setValueAt(cbMaTheLoai.getSelectedItem(), selectedRow, 2);
            tableModel.setValueAt(txtTacGia.getText(), selectedRow, 3);
            tableModel.setValueAt(txtTenSach.getText(), selectedRow, 4);
            tableModel.setValueAt(txtNamXB.getText(), selectedRow, 5);
            tableModel.setValueAt(txtSoLuong.getText(), selectedRow, 6);
            tableModel.setValueAt(txtDonGia.getText(), selectedRow, 7);
        }
    }

    // Hàm xóa sách
    private void deleteBook() {
        int selectedRow = tableBooks.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        }
    }

    // Hàm làm mới các ô nhập
    private void clearFields() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtTacGia.setText("");
        txtNamXB.setText("");
        txtSoLuong.setText("");
        txtDonGia.setText("");
        cbMaNXB.setSelectedIndex(0);
        cbMaTheLoai.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NewClass().setVisible(true);
        });
    }
}
