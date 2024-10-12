//package bms.system_management;
//
//import bms.product.Product;
//import bms.connectDB.ConnectMySQL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.LinkedHashMap;
//
//public class Order {
//
//    private String id; // ID cua don hang
//    private LinkedHashMap<Product, Integer> products; // San pham va so luong
//    private double totalAmount; // Tong gia tri don hang
//    private String date; // Ngay
//    private String time; // Thoi gian
//
//    public Order(String id, LinkedHashMap<Product, Integer> products) {
//        this.id = id;
//        this.products = products;
//        this.totalAmount = calculateTotalAmount(); // Tinh tong gia tri don hang
//
//        // Lay thoi gian hien tai
//        LocalDateTime now = LocalDateTime.now();
//
//        // Dinh dang thoi gian va ngay thanh chuoi phu hop
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//
//        this.date = now.format(dateFormatter);  // Ngay dang chuoi "yyyy-MM-dd"
//        this.time = now.format(timeFormatter);  // Thoi gian dang chuoi "HH:mm:ss"
//    }
//
//    // Phuong thuc tinh tong gia tri don hang
//    private double calculateTotalAmount() {
//        double total = 0.0;
//        for (Product product : products.keySet()) {
//            total += product.getSalePrice * products.get(product); // Tinh tong dua tren gia ban va so luong
//        }
//        return total;
//    }
//
//    // Phuong thuc de them don hang
//    public boolean addOrder() {
//        try {
//            Connection con = ConnectMySQL.getConnection();
//            con.setAutoCommit(false); // Bat dau transaction
//
//            // Kiem tra so luong ton kho cua tung san pham
//            for (Product product : products.keySet()) {
//                int orderQuantity = products.get(product);
//
//                // Kiem tra so luong ton kho
//                PreparedStatement checkStmt = con.prepareStatement("SELECT quantity FROM Product WHERE id = ?");
//                checkStmt.setString(1, product.getId());
//                ResultSet rs = checkStmt.executeQuery();
//                if (rs.next()) {
//                    int currentQuantity = rs.getInt("quantity");
//                    if (currentQuantity < orderQuantity) {
//                        con.rollback(); // Rollback neu khong du hang
//                        return false; // Khong the tao don hang do so luong khong du
//                    }
//                }
//            }
//
//            // Giam so luong san pham trong kho va cap nhat CSDL
//            for (Product product : products.keySet()) {
//                int orderQuantity = products.get(product);
//
//                // Giam so luong trong kho
//                PreparedStatement updateStmt = con.prepareStatement("UPDATE Product SET quantity = quantity - ? WHERE id = ?");
//                updateStmt.setInt(1, orderQuantity);
//                updateStmt.setString(2, product.getId());
//                updateStmt.executeUpdate();
//            }
//
//            // Them don hang vao CSDL
//            PreparedStatement orderStmt = con.prepareStatement("INSERT INTO Orders (id, totalAmount, date, time) VALUES (?, ?, ?, ?)");
//            orderStmt.setString(1, id);
//            orderStmt.setDouble(2, totalAmount);
//            orderStmt.setString(3, date);
//            orderStmt.setString(4, time);
//            orderStmt.executeUpdate();
//
//            // Commit transaction
//            con.commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false; // Tra ve false neu co loi xay ra
//        }
//    }
//}
