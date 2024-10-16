package bms.giaodien;

import bms.product.Book;
import bms.product.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProductManagementGUI extends JFrame {
    private static final String DATA_FILE = "products.txt"; // Tên tệp tin lưu trữ dữ liệu

    private Color primaryColor = new Color(255, 200, 0, 208); // Vàng
    private Color hoverColor = new Color(232, 206, 10, 223);
    private Color textColor = new Color(50, 50, 50);
    private Font menuFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 16);

    private List<Book> productList; // Danh sách sản phẩm
    private DefaultTableModel tableModel; // Model cho JTable
    private JTable productTable;

    public ProductManagementGUI() {
        setTitle("QUẢN LÝ NHÀ SÁCH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Khởi tạo danh sách sản phẩm từ tệp tin
        loadProducts();

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

    private void loadProducts() {
        productList = Book.loadBooksFromFile(DATA_FILE);
    }

    private JLabel createBookIcon() {
    // Đặt đường dẫn tới biểu tượng sách
    JLabel bookIcon = new JLabel(new ImageIcon("C:\\Users\\Asus\\OneDrive\\Pictures\\Java.jpg"));
    bookIcon.setPreferredSize(new Dimension(80, 80));
    return bookIcon;
}


    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(menuFont);
        button.setForeground(textColor);
        button.setBackground(primaryColor);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setMaximumSize(new Dimension(180, 40));
        button.setPreferredSize(new Dimension(180, 40));

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(10);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setContentAreaFilled(true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(primaryColor);
                button.setContentAreaFilled(false);
            }
        });

        return button;
    }

    private JPanel createEnhancedInputForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Các trường nhập liệu
        JTextField codeField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField authorField = new JTextField(10);
        JTextField priceField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JComboBox<String> genreBox = new JComboBox<>(new String[]{"Tiểu thuyết", "Giáo dục", "Khoa học", "Văn học","Truyện tranh", "Kinh dị", "Dân gian", "Ngôn tình", "Hài hước"});
        JComboBox<String> publisherBox = new JComboBox<>(new String[]{"NXB Trẻ", "NXB Kim Đồng", "NXB Giáo Dục", "Khác"});
        JTextField unitField = new JTextField(10);
        JTextField originField = new JTextField(10);
        JTextField dateField = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()), 10);
        dateField.setEditable(false); // Không cho phép chỉnh sửa trực tiếp

        // Thêm các trường vào form
        addFormField(panel, gbc, 0, 0, "Mã sách:", codeField);
        addFormField(panel, gbc, 0, 1, "Tên sách:", nameField);
        addFormField(panel, gbc, 0, 2, "Tác giả:", authorField);
        addFormField(panel, gbc, 0, 3, "Giá bán:", priceField);
        addFormField(panel, gbc, 0, 4, "Số lượng:", quantityField);
        addFormField(panel, gbc, 2, 0, "Thể loại:", genreBox);
        addFormField(panel, gbc, 2, 1, "Nhà xuất bản:", publisherBox);
        addFormField(panel, gbc, 2, 2, "Đơn vị tính:", unitField);
        addFormField(panel, gbc, 2, 3, "Nguồn gốc:", originField);

        // Trường ngày xuất bản với nút chọn ngày
        JPanel datePanel = new JPanel(new BorderLayout());
        JButton dateButton = new JButton("...");
        dateButton.setPreferredSize(new Dimension(30, dateField.getHeight()));
        datePanel.add(dateField, BorderLayout.CENTER);
        datePanel.add(dateButton, BorderLayout.EAST);
        addFormField(panel, gbc, 2, 4, "Ngày xuất bản:", datePanel);

        // Xử lý nút chọn ngày
        dateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sử dụng JSpinner để chọn ngày
                JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
                JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
                dateSpinner.setEditor(editor);
                int option = JOptionPane.showConfirmDialog(panel, dateSpinner, "Chọn ngày", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    java.util.Date selectedDate = (java.util.Date) dateSpinner.getValue();
                    dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(selectedDate));
                }
            }
        });

        // Hình ảnh
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createTitledBorder("Hình ảnh"));
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 150));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        JButton chooseImageButton = new JButton("Chọn ảnh");
        imagePanel.add(chooseImageButton, BorderLayout.SOUTH);

        // Xử lý nút chọn ảnh
        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(panel);
                if (option == JFileChooser.APPROVE_OPTION) {
                    String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                    imageLabel.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
                }
            }
        });

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        panel.add(imagePanel, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JButton saveButton = new JButton("Lưu");
        JButton editButton = new JButton("Chỉnh sửa");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa trắng");

        buttonPanel.add(saveButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        panel.add(buttonPanel, gbc);

        // Hành động cho nút "Lưu"
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String code = codeField.getText().trim();
                    String name = nameField.getText().trim();
                    String author = authorField.getText().trim();
                    double salePrice = Double.parseDouble(priceField.getText().trim());
                    int quantity = Integer.parseInt(quantityField.getText().trim());
                    String genre = (String) genreBox.getSelectedItem();
                    String publisher = (String) publisherBox.getSelectedItem();
                    String unit = unitField.getText().trim();
                    String origin = originField.getText().trim();
                    String dateStr = dateField.getText().trim();
                    int publicationYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateStr)));
                    String language = "Vietnamese"; // Bạn có thể thêm trường nhập liệu cho ngôn ngữ nếu cần
                    String imagePath = imageLabel.getIcon() != null ? imageLabel.getIcon().toString() : "";

                    // Kiểm tra xem có trường nào trống không
                    if (code.isEmpty() || name.isEmpty() || author.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return; // Dừng thực thi
                    }

                    // Kiểm tra xem mã sách đã tồn tại chưa
                    boolean exists = productList.stream().anyMatch(p -> p.getCode().equals(code));
                    if (exists) {
                        JOptionPane.showMessageDialog(panel, "Mã sách đã tồn tại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Tạo đối tượng mới
                    Book newBook = new Book(code, name, 0.0, salePrice, quantity, unit, origin,
                                            author, publisher, publicationYear, genre, language, dateStr, imagePath);

                    // Thêm vào tệp tin và danh sách
                    Book.addBook(newBook, productList, DATA_FILE);

                    // Cập nhật bảng
                    updateTable();

                    JOptionPane.showMessageDialog(panel, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    // Xóa trắng các trường nhập liệu
                    clearFormFields(codeField, nameField, authorField, priceField, quantityField, unitField, originField, dateField, imageLabel);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Giá và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Có lỗi xảy ra: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Hành động cho nút "Chỉnh sửa"
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String bookId = (String) tableModel.getValueAt(selectedRow, 1); // Cột 1 là ID

                    // Tìm đối tượng Book tương ứng
                    Book bookToEdit = null;
                    for (Product p : productList) {
                        if (p.getCode().equals(bookId)) {
                            bookToEdit = (Book) p;
                            break;
                        }
                    }

                    if (bookToEdit != null) {
                        // Hiển thị dialog để nhập thông tin mới
                        JTextField newNameField = new JTextField(bookToEdit.getName());
                        JTextField newAuthorField = new JTextField(bookToEdit.getAuthor());
                        JTextField newPriceField = new JTextField(String.valueOf(bookToEdit.getSalePrice()));
                        JTextField newQuantityField = new JTextField(String.valueOf(bookToEdit.getQuantity()));
                        JComboBox<String> newGenreBox = new JComboBox<>(new String[]{"Tiểu thuyết", "Giáo dục", "Khoa học", "Văn học","Truyện tranh", "Kinh dị", "Dân gian", "Ngôn tình", "Hài hước"});
                        newGenreBox.setSelectedItem(bookToEdit.getGenre());
                        JComboBox<String> newPublisherBox = new JComboBox<>(new String[]{"NXB Trẻ", "NXB Kim Đồng", "NXB Giáo Dục", "Khác"});
                        newPublisherBox.setSelectedItem(bookToEdit.getPublisher());
                        JTextField newUnitField = new JTextField(bookToEdit.getUnit());
                        JTextField newOriginField = new JTextField(bookToEdit.getOrigin());
                        JTextField newDateField = new JTextField(bookToEdit.getPublishDate());

                        // Hình ảnh
                        JLabel newImageLabel = new JLabel();
                        if (bookToEdit.getImagePath() != null && !bookToEdit.getImagePath().isEmpty()) {
                            newImageLabel.setIcon(new ImageIcon(new ImageIcon(bookToEdit.getImagePath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
                        }
                        JButton newChooseImageButton = new JButton("Chọn ảnh");

                        // Xử lý nút chọn ảnh
                        newChooseImageButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFileChooser fileChooser = new JFileChooser();
                                int option = fileChooser.showOpenDialog(panel);
                                if (option == JFileChooser.APPROVE_OPTION) {
                                    String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                                    newImageLabel.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
                                }
                            }
                        });

                        JPanel editImagePanel = new JPanel(new BorderLayout());
                        editImagePanel.add(newImageLabel, BorderLayout.CENTER);
                        editImagePanel.add(newChooseImageButton, BorderLayout.SOUTH);

                        JPanel editPanel = new JPanel(new GridBagLayout());
                        GridBagConstraints editGbc = new GridBagConstraints();
                        editGbc.insets = new Insets(5, 5, 5, 5);
                        editGbc.anchor = GridBagConstraints.WEST;

                        // Thêm các trường vào editPanel
                        addFormField(editPanel, editGbc, 0, 0, "Tên sách:", newNameField);
                        addFormField(editPanel, editGbc, 0, 1, "Tác giả:", newAuthorField);
                        addFormField(editPanel, editGbc, 0, 2, "Giá bán:", newPriceField);
                        addFormField(editPanel, editGbc, 0, 3, "Số lượng:", newQuantityField);
                        addFormField(editPanel, editGbc, 2, 0, "Thể loại:", newGenreBox);
                        addFormField(editPanel, editGbc, 2, 1, "Nhà xuất bản:", newPublisherBox);
                        addFormField(editPanel, editGbc, 2, 2, "Đơn vị tính:", newUnitField);
                        addFormField(editPanel, editGbc, 2, 3, "Nguồn gốc:", newOriginField);
                        addFormField(editPanel, editGbc, 2, 4, "Ngày xuất bản:", newDateField);
                        addFormField(editPanel, editGbc, 4, 0, "Hình ảnh:", editImagePanel);

                        int option = JOptionPane.showConfirmDialog(panel, editPanel, "Chỉnh sửa Sách", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                String newName = newNameField.getText().trim();
                                String newAuthor = newAuthorField.getText().trim();
                                double newSalePrice = Double.parseDouble(newPriceField.getText().trim());
                                int newQuantity = Integer.parseInt(newQuantityField.getText().trim());
                                String newGenre = (String) newGenreBox.getSelectedItem();
                                String newPublisher = (String) newPublisherBox.getSelectedItem();
                                String newUnit = newUnitField.getText().trim();
                                String newOrigin = newOriginField.getText().trim();
                                String newDateStr = newDateField.getText().trim();
                                int newPublicationYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(newDateStr)));
                                String newImagePath = newImageLabel.getIcon() != null ? newImageLabel.getIcon().toString() : "";

                                // Kiểm tra xem có trường nào trống không
                                if (newName.isEmpty() || newAuthor.isEmpty()) {
                                    JOptionPane.showMessageDialog(panel, "Vui lòng điền đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                                    return;
                                }

                                // Cập nhật đối tượng Book
                                Book updatedBook = new Book(bookId, newName, 0.0, newSalePrice, newQuantity, newUnit, newOrigin,
                                                            newAuthor, newPublisher, newPublicationYear, newGenre, "Vietnamese", newDateStr, newImagePath);

                                // Cập nhật vào tệp tin và danh sách
                                Book.updateBook(updatedBook, productList, DATA_FILE);

                                // Cập nhật bảng
                                updateTable();

                                JOptionPane.showMessageDialog(panel, "Chỉnh sửa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(panel, "Giá và số lượng phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(panel, "Có lỗi xảy ra: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Vui lòng chọn một sách để chỉnh sửa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Hành động cho nút "Xóa"
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String bookId = (String) tableModel.getValueAt(selectedRow, 1); // Cột 1 là ID
                    int confirm = JOptionPane.showConfirmDialog(panel, "Bạn có chắc chắn muốn xóa sách này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Xóa từ danh sách và tệp tin
                        Book.deleteBook(bookId, productList, DATA_FILE);
                        // Cập nhật bảng
                        updateTable();
                        JOptionPane.showMessageDialog(panel, "Xóa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Vui lòng chọn một sách để xóa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Hành động cho nút "Xóa trắng"
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFormFields(codeField, nameField, authorField, priceField, quantityField, unitField, originField, dateField, imageLabel);
            }
        });

        return panel;
    }

    private void clearFormFields(JTextField codeField, JTextField nameField, JTextField authorField,
                                 JTextField priceField, JTextField quantityField, JTextField unitField,
                                 JTextField originField, JTextField dateField, JLabel imageLabel) {
        codeField.setText("");
        nameField.setText("");
        authorField.setText("");
        priceField.setText("");
        quantityField.setText("");
        unitField.setText("");
        originField.setText("");
        dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
        imageLabel.setIcon(null);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int gridx, int gridy, String label, JComponent field) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = gridx + 1;
        panel.add(field, gbc);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Tạo bảng sản phẩm
        productTable = new JTable();
        tableModel = new DefaultTableModel(new String[]{
            "STT", "Mã sản phẩm", "Tên sản phẩm", "Tác giả", "Thể loại",
            "Giá bán", "Số lượng", "Nhà xuất bản", "Ngày xuất bản"
        }, 0) {
            // Không cho phép chỉnh sửa trực tiếp trên bảng
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Form nhập liệu
        JPanel inputFormPanel = createEnhancedInputForm();
        contentPanel.add(inputFormPanel, BorderLayout.SOUTH);

        // Cập nhật bảng với dữ liệu sản phẩm
        updateTable();

        return contentPanel;
    }

    private JPanel createLeftMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(20)); // Thêm khoảng cách trên
        panel.add(createBookIcon()); // Thêm biểu tượng sách
        panel.add(Box.createVerticalStrut(20)); // Thêm khoảng cách giữa các nút

        // Các nút menu
        panel.add(createMenuButton("Quản lý sách"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("Quản lý danh mục"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("Quản lý khách hàng"));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createMenuButton("Thống kê"));

        panel.setBackground(primaryColor);
        return panel;
    }

    private void updateTable() {
        try {
            tableModel.setRowCount(0); // Xóa tất cả dữ liệu trong bảng
            int index = 1;
            for (Product product : productList) {
                Book book = (Book) product;
                // Thêm sản phẩm vào bảng
                tableModel.addRow(new Object[]{
                    index++,
                    book.getCode(),
                    book.getName(),
                    book.getAuthor(),
                    book.getGenre(),
                    book.getSalePrice(),
                    book.getQuantity(),
                    book.getPublisher(),
                    book.getPublishDate()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể cập nhật bảng dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductManagementGUI frame = new ProductManagementGUI();
            frame.setVisible(true);
        });
    }
}
