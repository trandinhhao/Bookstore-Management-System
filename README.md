# Hệ Thống Quản Lý Nhà Sách (Bookstore Management System)

Đây là bài tập lớn môn Lập trình hướng đối tượng (OOP) tại Học viện Công nghệ Bưu chính Viễn thông - PTIT.

---

## 📝 Giới thiệu

Hệ thống quản lý nhà sách được xây dựng nhằm tin học hóa các quy trình nghiệp vụ của nhà sách, giúp việc quản lý trở nên hiệu quả và chuyên nghiệp hơn.

### Các vấn đề của quản lý thủ công
- Khó khăn trong việc tra cứu và tìm kiếm thông tin sản phẩm  
- Dễ xảy ra sai sót trong việc cập nhật thông tin  
- Tốn thời gian xử lý giao dịch  
- Khó khăn trong việc thống kê và báo cáo  

### Giải pháp
- Xây dựng hệ thống quản lý tập trung  
- Số hóa quy trình nghiệp vụ  
- Tự động hóa các công đoạn tính toán và thống kê  

---

## 🛠 Công nghệ sử dụng

- Java Core & Java Swing  
- MySQL Database  
- Apache NetBeans IDE 22  
- Git & GitHub  

---

## 🏗 Kiến trúc hệ thống

### Cấu trúc thư mục

```plaintext
bms/
├── connectDB/     # Kết nối CSDL MySQL
├── giaodien/      # GUI của chương trình
├── home/          # MainClass
├── product/       # Class sản phẩm
├── system_management/  # Class quản lý nâng cao
├── user/          # Class nhân viên và khách hàng
└── work/          # Xử lý đăng nhập
```

---

## ⚙️ Chức năng chính

### 1. Quản lý đăng nhập
<div align="center">
  <img src="images/login.png" alt="Giao diện đăng nhập">
</div>

### 2. Quản lý sản phẩm
- Thêm, sửa, xóa sản phẩm  
- Tìm kiếm sản phẩm  
<div align="center">
  <img src="images/product-management.png" alt="Quản lý sản phẩm">
</div>

### 3. Quản lý nhân viên
- Thêm, sửa, xóa nhân viên  
- Tìm kiếm nhân viên  
<div align="center">
  <img src="images/employee-management.png" alt="Quản lý nhân viên">
</div>

### 4. Quản lý khách hàng
- Thêm, sửa, xóa khách hàng  
- Tìm kiếm khách hàng  
<div align="center">
  <img src="images/customer-management.png" alt="Quản lý khách hàng">
</div>

### 5. Quản lý chấm công
<div align="center">
  <img src="images/attendance.png" alt="Chấm công">
</div>

### 6. Quản lý hóa đơn
<div align="center">
  <img src="images/invoice.png" alt="Quản lý hóa đơn">
</div>

### 7. Quản lý kho
<div align="center">
  <img src="images/inventory.png" alt="Quản lý kho">
</div>

### 8. Thống kê doanh thu
<div align="center">
  <img src="images/statistics.png" alt="Thống kê">
</div>

---

## 📦 Cài đặt và Sử dụng

1. Clone repository

   ```bash
   git clone https://github.com/your-username/bookstore-management.git
   ```

2. Import database
   - Mở MySQL Workbench  
   - Import file [`database.sql`](https://github.com/trandinhhao/Bookstore-Management-System/blob/master/bms.sql)

3. Cấu hình kết nối database trong [`ConnectDB.java`](https://github.com/trandinhhao/Bookstore-Management-System/blob/master/src/main/java/bms/connectDB/ConnectMySQL.java)

4. Build và chạy project trong NetBeans IDE  

---

## 📚 Tài liệu tham khảo

1. [Bài giảng Lập trình Hướng đối tượng - PTIT](https://bit.ly/ptit_oop)  
2. [Java Swing Tutorial - Javatpoint](https://www.javatpoint.com/java-swing)  
3. [All SQL Cheat Sheet - LearnSQL.com](https://bit.ly/all_sql_cheat_sheet)  

---

## 📝 License

[MIT](https://choosealicense.com/licenses/mit/)
