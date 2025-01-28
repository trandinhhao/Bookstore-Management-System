CREATE DATABASE BMS;
USE bms;

CREATE TABLE acc (
	id VARCHAR(30),
	username VARCHAR(30),
    password VARCHAR(30),
    PRIMARY KEY (username)
);

CREATE TABLE Book (
	id VARCHAR(40),
    name VARCHAR(40),
    cost_price DOUBLE,
    sale_price DOUBLE,
    quantity INTEGER,
    unit VARCHAR(40),
    origin VARCHAR(40),
    author VARCHAR(40),
    publisher VARCHAR(40),
    publicationYear YEAR,
    genre VARCHAR(40),
    language VARCHAR(40),
    PRIMARY KEY(id)
);

CREATE TABLE Employee (
	id VARCHAR(40),
    name VARCHAR(40),
    birth DATE,
    address VARCHAR(500),
    phoneNumber VARCHAR(20),
    email VARCHAR(40),
    position VARCHAR(40),
    employmentType VARCHAR(30),
    PRIMARY KEY(id)
);


CREATE TABLE gift (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    cost_price DOUBLE,
    sale_price DOUBLE,
    quantity INT,
    unit VARCHAR(20),
    origin VARCHAR(50),
    type VARCHAR(50),
    material VARCHAR(50)
);

CREATE TABLE notebook (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    cost_price DOUBLE,
    sale_price DOUBLE,
    quantity INT,
    unit VARCHAR(20),
    origin VARCHAR(50),
    page_count INT,
    paper_type VARCHAR(50),
    size VARCHAR(20),
    manufacturer VARCHAR(50)
);

CREATE TABLE stationery (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    cost_price DOUBLE,
    sale_price DOUBLE,
    quantity INT,
    unit VARCHAR(20),
    origin VARCHAR(50),
    type VARCHAR(50),
    manufacturer VARCHAR(50),
    material VARCHAR(50)
);

CREATE TABLE textbook (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    cost_price DOUBLE,
    sale_price DOUBLE,
    quantity INT,
    unit VARCHAR(20),
    origin VARCHAR(50),
    author VARCHAR(100),
    publisher VARCHAR(100),
    publication_year INT,
    genre VARCHAR(50),
    language VARCHAR(50),
    subject VARCHAR(50),
    grade INT,
    edu_level VARCHAR(50)
);

CREATE TABLE customer (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100),
    birth DATE,
    address VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(100),
    loyalty_points INT,
    register_date DATE,
    membership_tier VARCHAR(50),
    discount INT
);

INSERT INTO customer (id, name, birth, address, phone_number, email, loyalty_points, register_date, membership_tier, discount) VALUES
('C01', 'Nguyễn Văn A', '1990-01-15', 'Hà Nội', '0123456789', 'nguyenvana@example.com', 150, '2022-05-10', 'Đồng', 0),
('C02', 'Trần Thị B', '1992-03-22', 'Đà Nẵng', '0987654321', 'tranthib@example.com', 600, '2021-07-15', 'Bạc', 3),
('C03', 'Lê Văn C', '1985-06-30', 'TP Hồ Chí Minh', '0123456780', 'levanc@example.com', 450, '2023-01-20', 'Đồng', 0),
('C04', 'Phạm Thị D', '1995-08-05', 'Cần Thơ', '0987654320', 'phamthid@example.com', 1200, '2020-11-10', 'Vàng', 5),
('C05', 'Đỗ Văn E', '1988-09-18', 'Hải Phòng', '0123456798', 'dovane@example.com', 300, '2022-03-25', 'Đồng', 0),
('C06', 'Nguyễn Thị F', '1994-11-12', 'Nha Trang', '0987654312', 'nguyenthif@example.com', 800, '2022-06-17', 'Bạc', 3),
('C07', 'Vũ Văn G', '1982-05-25', 'Hà Nội', '0123456787', 'vuvang@example.com', 750, '2021-09-05', 'Bạc', 3),
('C08', 'Hoàng Thị H', '1993-12-03', 'TP Hồ Chí Minh', '0987654313', 'hoangthih@example.com', 75, '2020-10-20', 'Đồng', 0),
('C09', 'Trần Văn I', '1987-02-14', 'Đà Nẵng', '0123456788', 'tranvani@example.com', 200, '2022-04-15', 'Đồng', 0),
('C10', 'Lê Thị J', '1991-07-08', 'Cần Thơ', '0987654324', 'lethij@example.com', 1500, '2021-08-30', 'Vàng', 5),
('C11', 'Nguyễn Văn K', '1984-10-17', 'Hải Phòng', '0123456790', 'nguyenvank@example.com', 100, '2023-02-25', 'Đồng', 0),
('C12', 'Đỗ Thị L', '1996-01-02', 'Nha Trang', '0987654300', 'dothil@example.com', 250, '2020-03-12', 'Đồng', 0),
('C13', 'Phạm Văn M', '1983-04-23', 'Hà Nội', '0123456791', 'phamvanm@example.com', 500, '2021-05-20', 'Bạc', 3),
('C14', 'Lê Thị N', '1990-08-30', 'TP Hồ Chí Minh', '0987654314', 'lethinh@example.com', 1100, '2022-07-14', 'Vàng', 5),
('C15', 'Trần Văn O', '1992-06-16', 'Đà Nẵng', '0123456792', 'tranvano@example.com', 150, '2020-12-30', 'Đồng', 0),
('C16', 'Nguyễn Thị P', '1986-03-28', 'Cần Thơ', '0987654325', 'nguyenthip@example.com', 400, '2021-09-11', 'Đồng', 0),
('C17', 'Vũ Văn Q', '1981-07-04', 'Hải Phòng', '0123456793', 'vuvanq@example.com', 50, '2022-08-01', 'Đồng', 0),
('C18', 'Hoàng Thị R', '1995-09-19', 'Nha Trang', '0987654315', 'hoangthir@example.com', 600, '2021-10-15', 'Bạc', 3),
('C19', 'Trần Văn S', '1993-02-01', 'Hà Nội', '0123456794', 'tranvans@example.com', 350, '2022-11-29', 'Đồng', 0),
('C20', 'Lê Thị T', '1990-12-15', 'TP Hồ Chí Minh', '0987654316', 'lethit@example.com', 1200, '2021-06-20', 'Vàng', 5),
('C21', 'Nguyễn Văn U', '1988-05-30', 'Đà Nẵng', '0123456795', 'nguyenvanu@example.com', 180, '2020-10-05', 'Đồng', 0),
('C22', 'Đỗ Thị V', '1994-11-11', 'Cần Thơ', '0987654327', 'dothiv@example.com', 320, '2021-07-17', 'Đồng', 0),
('C23', 'Phạm Văn W', '1987-01-05', 'Hải Phòng', '0123456796', 'phamvanw@example.com', 280, '2023-01-22', 'Đồng', 0),
('C24', 'Lê Thị X', '1996-02-14', 'Nha Trang', '0987654328', 'lethix@example.com', 210, '2020-12-10', 'Đồng', 0),
('C25', 'Trần Văn Y', '1992-09-23', 'Hà Nội', '0123456797', 'tranvany@example.com', 300, '2021-08-18', 'Đồng', 0),
('C26', 'Nguyễn Thị Z', '1983-07-02', 'TP Hồ Chí Minh', '0987654319', 'nguyenthiz@example.com', 60, '2022-05-30', 'Đồng', 0),
('C27', 'Vũ Văn AA', '1990-10-27', 'Đà Nẵng', '0123456798', 'vuvanAA@example.com', 150, '2021-03-25', 'Đồng', 0),
('C28', 'Hoàng Thị BB', '1985-06-17', 'Cần Thơ', '0987654301', 'hoangthibb@example.com', 90, '2022-01-14', 'Đồng', 0),
('C29', 'Trần Văn CC', '1992-03-30', 'Hải Phòng', '0123456786', 'tranvanc@example.com', 140, '2021-11-05', 'Đồng', 0),
('C30', 'Nguyễn Thị DD', '1991-08-25', 'Nha Trang', '0987654320', 'nguyenthidd@example.com', 250, '2020-07-12', 'Đồng', 0);


INSERT INTO Acc VALUES('QL01','admin', '12345');
INSERT INTO Acc VALUES('QL02','adminn', '123456');

INSERT INTO Book VALUES 
('B001', 'Dế Mèn Phiêu Lưu Ký', 45000, 60000, 100, 'quyển', 'Việt Nam', 'Tô Hoài', 'NXB Kim Đồng', 2020, 'Văn học thiếu nhi', 'Tiếng Việt'),
('B002', 'Số Đỏ', 55000, 75000, 80, 'quyển', 'Việt Nam', 'Vũ Trọng Phụng', 'NXB Văn Học', 2019, 'Tiểu thuyết', 'Tiếng Việt'),
('B003', 'Nhà Giả Kim', 65000, 85000, 120, 'quyển', 'Brazil', 'Paulo Coelho', 'NXB Văn Học', 2021, 'Tiểu thuyết', 'Tiếng Việt'),
('B004', 'Tắt Đèn', 40000, 55000, 90, 'quyển', 'Việt Nam', 'Ngô Tất Tố', 'NXB Văn Học', 2018, 'Tiểu thuyết', 'Tiếng Việt'),
('B005', 'Từ Điển Tiếng Việt', 150000, 180000, 50, 'quyển', 'Việt Nam', 'Hoàng Phê', 'NXB Đà Nẵng', 2022, 'Từ điển', 'Tiếng Việt'),
('B006', 'Lập Trình Python Cơ Bản', 120000, 150000, 70, 'quyển', 'Việt Nam', 'Đỗ Minh Quân', 'NXB Bách Khoa Hà Nội', 2023, 'Công nghệ', 'Tiếng Việt'),
('B007', 'Chiến Tranh và Hòa Bình', 200000, 250000, 40, 'bộ', 'Nga', 'Lev Tolstoy', 'NXB Văn Học', 2020, 'Tiểu thuyết', 'Tiếng Việt'),
('B008', 'Nghìn Lẻ Một Đêm', 180000, 220000, 60, 'bộ', 'Ả Rập', 'Nhiều tác giả', 'NXB Văn Học', 2021, 'Truyện cổ tích', 'Tiếng Việt'),
('B009', 'Lịch Sử Việt Nam', 130000, 160000, 80, 'quyển', 'Việt Nam', 'Phan Huy Lê', 'NXB Giáo Dục', 2022, 'Lịch sử', 'Tiếng Việt'),
('B010', 'Đắc Nhân Tâm', 70000, 90000, 150, 'quyển', 'Mỹ', 'Dale Carnegie', 'NXB Tổng Hợp TPHCM', 2023, 'Kỹ năng sống', 'Tiếng Việt'),
('B011', 'Tôi Thấy Hoa Vàng Trên Cỏ Xanh', 80000, 100000, 110, 'quyển', 'Việt Nam', 'Nguyễn Nhật Ánh', 'NXB Trẻ', 2021, 'Tiểu thuyết', 'Tiếng Việt'),
('B012', 'Từ Điển Anh-Việt', 160000, 190000, 55, 'quyển', 'Việt Nam', 'Nguyễn Quốc Hùng', 'NXB Từ Điển Bách Khoa', 2022, 'Từ điển', 'Tiếng Việt'),
('B013', 'Kinh Tế Học', 140000, 170000, 65, 'quyển', 'Việt Nam', 'Nguyễn Văn A', 'NXB Kinh Tế', 2023, 'Kinh tế', 'Tiếng Việt'),
('B014', 'Sherlock Holmes', 190000, 230000, 45, 'bộ', 'Anh', 'Arthur Conan Doyle', 'NXB Văn Học', 2020, 'Trinh thám', 'Tiếng Việt'),
('B015', 'Văn Học Việt Nam', 110000, 140000, 75, 'quyển', 'Việt Nam', 'Nhiều tác giả', 'NXB Giáo Dục', 2021, 'Văn học', 'Tiếng Việt'),
('B016', 'Toán Học Cao Cấp', 160000, 190000, 50, 'quyển', 'Việt Nam', 'Nguyễn Đình Trí', 'NXB Giáo Dục', 2022, 'Toán học', 'Tiếng Việt'),
('B017', 'Cuốn Theo Chiều Gió', 170000, 210000, 40, 'quyển', 'Mỹ', 'Margaret Mitchell', 'NXB Văn Học', 2019, 'Tiểu thuyết', 'Tiếng Việt'),
('B018', 'Lập Trình Web', 130000, 160000, 70, 'quyển', 'Việt Nam', 'Trần Văn B', 'NXB Bách Khoa Hà Nội', 2023, 'Công nghệ', 'Tiếng Việt'),
('B019', 'Từ Điển Y Học', 200000, 240000, 35, 'quyển', 'Việt Nam', 'PGS.TS. Nguyễn C', 'NXB Y Học', 2022, 'Y học', 'Tiếng Việt'),
('B020', 'Hóa Học Đại Cương', 120000, 150000, 60, 'quyển', 'Việt Nam', 'GS. Đặng D', 'NXB Giáo Dục', 2021, 'Hóa học', 'Tiếng Việt');


INSERT INTO Employee VALUES
('NV001', 'Nguyễn Văn An', '1990-05-15', '123 Đội Cấn, Ba Đình, Hà Nội', '0901234567', 'nguyenvanan@email.com', 'Thu ngân', 'Fulltime'),
('NV002', 'Trần Thị Bình', '1992-08-20', '456 Láng Hạ, Đống Đa, Hà Nội', '0912345678', 'tranthibinh@email.com', 'Bán hàng', 'Fulltime'),
('NV003', 'Lê Văn Cường', '1988-03-10', '789 Trần Duy Hưng, Cầu Giấy, Hà Nội', '0923456789', 'levancuong@email.com', 'Kinh doanh', 'Fulltime'),
('NV004', 'Phạm Thị Dung', '1995-11-25', '101 Lê Duẩn, Hoàn Kiếm, Hà Nội', '0934567890', 'phamthidung@email.com', 'Marketing', 'Fulltime'),
('NV005', 'Hoàng Văn Em', '1993-07-05', '202 Thái Hà, Đống Đa, Hà Nội', '0945678901', 'hoangvanem@email.com', 'Kho', 'Fulltime'),
('NV006', 'Ngô Thị Fương', '1991-09-30', '303 Giảng Võ, Ba Đình, Hà Nội', '0956789012', 'ngothifuong@email.com', 'An ninh', 'Fulltime'),
('NV007', 'Đỗ Văn Giang', '1994-02-18', '404 Nguyễn Chí Thanh, Đống Đa, Hà Nội', '0967890123', 'dovangiang@email.com', 'Bán hàng', 'Parttime'),
('NV008', 'Lý Thị Hương', '1997-06-12', '505 Tây Sơn, Đống Đa, Hà Nội', '0978901234', 'lythihuong@email.com', 'Thu ngân', 'Parttime'),
('NV009', 'Trương Văn Inh', '1989-12-08', '606 Hoàng Quốc Việt, Cầu Giấy, Hà Nội', '0989012345', 'truongvaninh@email.com', 'Kinh doanh', 'Fulltime'),
('NV010', 'Mai Thị Kim', '1996-04-22', '707 Phạm Hùng, Nam Từ Liêm, Hà Nội', '0990123456', 'maithikim@email.com', 'Marketing', 'Parttime'),
('NV011', 'Vũ Văn Lâm', '1992-10-17', '808 Trần Phú, Hà Đông, Hà Nội', '0901234567', 'vuvanlam@email.com', 'Kho', 'Fulltime'),
('NV012', 'Đặng Thị Minh', '1993-01-29', '909 Lê Văn Lương, Thanh Xuân, Hà Nội', '0912345678', 'dangthiminh@email.com', 'Bán hàng', 'Fulltime'),
('NV013', 'Bùi Văn Năm', '1990-08-14', '1010 Nguyễn Trãi, Thanh Xuân, Hà Nội', '0923456789', 'buivannam@email.com', 'An ninh', 'Parttime'),
('NV014', 'Hồ Thị Oanh', '1995-03-07', '1111 Đê La Thành, Đống Đa, Hà Nội', '0934567890', 'hothioanh@email.com', 'Thu ngân', 'Fulltime'),
('NV015', 'Đinh Văn Phúc', '1991-12-01', '1212 Trường Chinh, Đống Đa, Hà Nội', '0945678901', 'dinhvanphuc@email.com', 'Bán hàng', 'Fulltime');

INSERT INTO gift (id, name, cost_price, sale_price, quantity, unit, origin, type, material) VALUES
('G01', 'Bộ xếp hình', 30000, 45000, 100, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G02', 'Gấu bông', 25000, 40000, 200, 'con', 'Việt Nam', 'Đồ chơi', 'Vải'),
('G03', 'Bộ đồ chơi xe hơi', 28000, 42000, 150, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G04', 'Búp bê', 30000, 50000, 120, 'con', 'Việt Nam', 'Đồ chơi', 'Vải'),
('G05', 'Bộ đồ chơi Lego', 35000, 55000, 80, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G06', 'Bóng đá', 15000, 25000, 200, 'quả', 'Việt Nam', 'Thể thao', 'Da'),
('G07', 'Bộ đồ dùng học tập', 20000, 30000, 300, 'bộ', 'Việt Nam', 'Văn phòng phẩm', 'Giấy'),
('G08', 'Khung ảnh', 12000, 18000, 400, 'cái', 'Việt Nam', 'Trang trí', 'Gỗ'),
('G09', 'Máy bay điều khiển từ xa', 50000, 80000, 60, 'chiếc', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G10', 'Bóng rổ', 20000, 30000, 150, 'quả', 'Việt Nam', 'Thể thao', 'Da'),
('G11', 'Bảng vẽ', 40000, 60000, 100, 'cái', 'Việt Nam', 'Văn phòng phẩm', 'Gỗ'),
('G12', 'Xe đạp trẻ em', 70000, 100000, 30, 'cái', 'Việt Nam', 'Đồ chơi', 'Kim loại'),
('G13', 'Đồ chơi gỗ', 25000, 35000, 200, 'bộ', 'Việt Nam', 'Đồ chơi', 'Gỗ'),
('G14', 'Bộ xếp hình 3D', 32000, 48000, 90, 'bộ', 'Việt Nam', 'Đồ chơi', 'Giấy'),
('G15', 'Bảng chữ cái', 18000, 26000, 350, 'bộ', 'Việt Nam', 'Văn phòng phẩm', 'Giấy'),
('G16', 'Bộ thí nghiệm hóa học', 45000, 70000, 40, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G17', 'Sách tô màu', 15000, 22000, 500, 'cuốn', 'Việt Nam', 'Văn phòng phẩm', 'Giấy'),
('G18', 'Bộ lắp ráp mô hình', 30000, 45000, 70, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G19', 'Đồ chơi sinh học', 25000, 40000, 120, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G20', 'Tranh ghép hình', 22000, 32000, 200, 'bộ', 'Việt Nam', 'Đồ chơi', 'Giấy'),
('G21', 'Mô hình tàu thủy', 32000, 47000, 75, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G22', 'Đồ chơi giáo dục', 25000, 38000, 150, 'bộ', 'Việt Nam', 'Đồ chơi', 'Gỗ'),
('G23', 'Bộ đồ chơi nấu ăn', 30000, 50000, 100, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G24', 'Bóng tennis', 15000, 20000, 250, 'quả', 'Việt Nam', 'Thể thao', 'Da'),
('G25', 'Mô hình khủng long', 35000, 52000, 60, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G26', 'Xe lửa đồ chơi', 40000, 60000, 80, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G27', 'Bộ xếp hình thú nhồi bông', 32000, 48000, 90, 'bộ', 'Việt Nam', 'Đồ chơi', 'Vải'),
('G28', 'Bộ đồ chơi xây dựng', 25000, 40000, 110, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa'),
('G29', 'Đồ chơi bóng chuyền', 20000, 30000, 150, 'quả', 'Việt Nam', 'Thể thao', 'Da'),
('G30', 'Bộ thí nghiệm sinh học', 40000, 60000, 40, 'bộ', 'Việt Nam', 'Đồ chơi', 'Nhựa');


INSERT INTO notebook (id, name, cost_price, sale_price, quantity, unit, origin, page_count, paper_type, size, manufacturer) VALUES
('N01', 'Vở Thiên Long', 20000, 30000, 1000, 'cuốn', 'Việt Nam', 100, 'Giấy trắng', 'A4', 'Thiên Long'),
('N02', 'Vở Hồng Hà', 22000, 32000, 800, 'cuốn', 'Việt Nam', 120, 'Giấy trắng', 'A5', 'Hồng Hà'),
('N03', 'Vở Song Ngữ', 25000, 35000, 500, 'cuốn', 'Việt Nam', 80, 'Giấy màu', 'A4', 'Hải Hà'),
('N04', 'Vở Kẻ ô', 18000, 26000, 600, 'cuốn', 'Việt Nam', 200, 'Giấy kẻ ô', 'A5', 'Vĩnh Tiến'),
('N05', 'Vở Bìa Cứng', 30000, 45000, 400, 'cuốn', 'Việt Nam', 80, 'Giấy trắng', 'A4', 'Thiên Long'),
('N06', 'Vở Lò Xo', 25000, 35000, 700, 'cuốn', 'Việt Nam', 60, 'Giấy màu', 'A5', 'Hồng Hà'),
('N07', 'Vở Ghi Chép', 27000, 38000, 900, 'cuốn', 'Việt Nam', 120, 'Giấy trắng', 'A4', 'Hải Hà'),
('N08', 'Vở Tập Viết', 22000, 32000, 500, 'cuốn', 'Việt Nam', 100, 'Giấy trắng', 'A5', 'Vĩnh Tiến'),
('N09', 'Vở Thực Hành', 30000, 45000, 300, 'cuốn', 'Việt Nam', 150, 'Giấy trắng', 'A4', 'Thiên Long'),
('N10', 'Vở Sáng Tạo', 28000, 40000, 600, 'cuốn', 'Việt Nam', 120, 'Giấy màu', 'A5', 'Hồng Hà'),
('N11', 'Vở Bìa Mềm', 15000, 22000, 1000, 'cuốn', 'Việt Nam', 50, 'Giấy trắng', 'A4', 'Vĩnh Tiến'),
('N12', 'Vở Viết Chữ', 25000, 35000, 900, 'cuốn', 'Việt Nam', 100, 'Giấy trắng', 'A5', 'Hải Hà'),
('N13', 'Vở Học Sinh', 22000, 32000, 800, 'cuốn', 'Việt Nam', 120, 'Giấy kẻ ô', 'A4', 'Thiên Long'),
('N14', 'Vở Dán Hình', 28000, 40000, 400, 'cuốn', 'Việt Nam', 80, 'Giấy màu', 'A5', 'Hồng Hà'),
('N15', 'Vở Bìa Cứng Bằng Nhựa', 30000, 45000, 350, 'cuốn', 'Việt Nam', 60, 'Giấy trắng', 'A4', 'Hải Hà'),
('N16', 'Vở Bảo Vệ Môi Trường', 20000, 30000, 1000, 'cuốn', 'Việt Nam', 150, 'Giấy tái chế', 'A5', 'Vĩnh Tiến'),
('N17', 'Vở Thơ', 22000, 32000, 600, 'cuốn', 'Việt Nam', 80, 'Giấy trắng', 'A4', 'Thiên Long'),
('N18', 'Vở Kẻ Ô Phân Cách', 25000, 35000, 900, 'cuốn', 'Việt Nam', 100, 'Giấy kẻ ô', 'A5', 'Hồng Hà'),
('N19', 'Vở Tự Nhiên', 18000, 26000, 700, 'cuốn', 'Việt Nam', 200, 'Giấy trắng', 'A4', 'Hải Hà'),
('N20', 'Vở Dạy Viết', 25000, 35000, 800, 'cuốn', 'Việt Nam', 120, 'Giấy trắng', 'A5', 'Vĩnh Tiến'),
('N21', 'Vở Học Tập', 20000, 30000, 1000, 'cuốn', 'Việt Nam', 100, 'Giấy trắng', 'A4', 'Thiên Long'),
('N22', 'Vở Đánh Giá', 22000, 32000, 800, 'cuốn', 'Việt Nam', 120, 'Giấy màu', 'A5', 'Hồng Hà'),
('N23', 'Vở Ghi Nhớ', 25000, 35000, 500, 'cuốn', 'Việt Nam', 80, 'Giấy trắng', 'A4', 'Hải Hà'),
('N24', 'Vở Kẻ Ô Nhỏ', 18000, 26000, 600, 'cuốn', 'Việt Nam', 200, 'Giấy trắng', 'A5', 'Vĩnh Tiến'),
('N25', 'Vở Năng Khiếu', 30000, 45000, 400, 'cuốn', 'Việt Nam', 80, 'Giấy trắng', 'A4', 'Thiên Long'),
('N26', 'Vở Tự Lập', 25000, 35000, 700, 'cuốn', 'Việt Nam', 60, 'Giấy màu', 'A5', 'Hồng Hà'),
('N27', 'Vở Lịch Sử', 27000, 38000, 900, 'cuốn', 'Việt Nam', 120, 'Giấy trắng', 'A4', 'Hải Hà'),
('N28', 'Vở Địa Lý', 22000, 32000, 500, 'cuốn', 'Việt Nam', 100, 'Giấy trắng', 'A5', 'Vĩnh Tiến'),
('N29', 'Vở Văn Học', 30000, 45000, 300, 'cuốn', 'Việt Nam', 150, 'Giấy trắng', 'A4', 'Thiên Long'),
('N30', 'Vở Thí Nghiệm', 28000, 40000, 600, 'cuốn', 'Việt Nam', 120, 'Giấy màu', 'A5', 'Hồng Hà');


INSERT INTO stationery (id, name, cost_price, sale_price, quantity, unit, origin, type, manufacturer, material) VALUES
('S01', 'Bút bi Thiên Long', 30000, 45000, 500, 'cây', 'Việt Nam', 'Bút bi', 'Thiên Long', 'Nhựa'),
('S02', 'Bút chì Hồng Hà', 15000, 25000, 400, 'cây', 'Việt Nam', 'Bút chì', 'Hồng Hà', 'Gỗ'),
('S03', 'Tẩy nhựa Deli', 10000, 20000, 300, 'cái', 'Việt Nam', 'Tẩy', 'Deli', 'Nhựa'),
('S04', 'Sổ tay Thiên Long', 25000, 40000, 350, 'cuốn', 'Việt Nam', 'Sổ tay', 'Thiên Long', 'Giấy'),
('S05', 'Bảng trắng', 50000, 70000, 200, 'cái', 'Việt Nam', 'Bảng trắng', 'Công ty A', 'Nhựa'),
('S06', 'Bút lông Color', 20000, 30000, 250, 'cây', 'Việt Nam', 'Bút lông', 'Công ty B', 'Mực'),
('S07', 'Bút màu Crayola', 40000, 60000, 150, 'bộ', 'Mỹ', 'Bút màu', 'Crayola', 'Mực'),
('S08', 'Giấy A4', 60000, 90000, 400, 'gói', 'Việt Nam', 'Giấy', 'Công ty C', 'Giấy'),
('S09', 'Kéo 18cm', 25000, 40000, 300, 'cái', 'Việt Nam', 'Kéo', 'Công ty D', 'Thép'),
('S10', 'Bìa cứng', 30000, 45000, 200, 'tấm', 'Việt Nam', 'Bìa', 'Công ty E', 'Giấy'),
('S11', 'Bút dạ quang', 35000, 50000, 500, 'cây', 'Việt Nam', 'Bút dạ', 'Công ty F', 'Mực'),
('S12', 'Bìa hồ sơ', 15000, 25000, 450, 'cái', 'Việt Nam', 'Bìa', 'Công ty G', 'Giấy'),
('S13', 'Thước kẻ nhựa', 20000, 30000, 350, 'cái', 'Việt Nam', 'Thước', 'Công ty H', 'Nhựa'),
('S14', 'Bảng kẹp', 30000, 45000, 300, 'cái', 'Việt Nam', 'Bảng kẹp', 'Công ty I', 'Nhựa'),
('S15', 'Bút lông dầu', 20000, 30000, 200, 'cây', 'Việt Nam', 'Bút lông', 'Công ty J', 'Mực'),
('S16', 'Dây thun', 10000, 15000, 500, 'gói', 'Việt Nam', 'Dây thun', 'Công ty K', 'Nhựa'),
('S17', 'Bảng vẽ', 60000, 90000, 100, 'cái', 'Việt Nam', 'Bảng vẽ', 'Công ty L', 'Gỗ'),
('S18', 'Dù bấm', 20000, 30000, 250, 'cái', 'Việt Nam', 'Dù bấm', 'Công ty M', 'Kim loại'),
('S19', 'Bút chì màu', 35000, 50000, 300, 'bộ', 'Việt Nam', 'Bút chì màu', 'Công ty N', 'Gỗ'),
('S20', 'Bút gel', 25000, 40000, 450, 'cây', 'Việt Nam', 'Bút gel', 'Công ty O', 'Nhựa'),
('S21', 'Túi đựng hồ sơ', 50000, 70000, 200, 'cái', 'Việt Nam', 'Túi', 'Công ty P', 'Nhựa'),
('S22', 'Kẹp giấy', 5000, 10000, 1000, 'gói', 'Việt Nam', 'Kẹp', 'Công ty Q', 'Kim loại'),
('S23', 'Bút xóa', 15000, 25000, 600, 'cái', 'Việt Nam', 'Bút xóa', 'Công ty R', 'Nhựa'),
('S24', 'Bảng trắng 2 mặt', 70000, 100000, 150, 'cái', 'Việt Nam', 'Bảng trắng', 'Công ty S', 'Nhựa'),
('S25', 'Sổ ghi chép', 30000, 45000, 400, 'cuốn', 'Việt Nam', 'Sổ', 'Công ty T', 'Giấy'),
('S26', 'Thước kẻ 30cm', 15000, 25000, 350, 'cái', 'Việt Nam', 'Thước', 'Công ty U', 'Nhựa'),
('S27', 'Bảng ghim', 40000, 60000, 100, 'cái', 'Việt Nam', 'Bảng ghim', 'Công ty V', 'Nhựa'),
('S28', 'Mực bút bi', 25000, 35000, 500, 'hộp', 'Việt Nam', 'Mực', 'Công ty W', 'Mực'),
('S29', 'Dấu sao', 20000, 30000, 300, 'cái', 'Việt Nam', 'Dấu', 'Công ty X', 'Nhựa'),
('S30', 'Bảng 3D', 80000, 120000, 200, 'cái', 'Việt Nam', 'Bảng', 'Công ty Y', 'Gỗ');

INSERT INTO textbook  VALUES
('T01', 'Toán lớp 1', 50000, 80000, 200, 'cuốn', 'Việt Nam', 'Nguyễn Văn A', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Toán', 1, 'TH'),
('T02', 'Tiếng Việt lớp 1', 55000, 85000, 150, 'cuốn', 'Việt Nam', 'Nguyễn Văn B', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Tiếng Việt', 1, 'TH'),
('T03', 'Khoa học lớp 2', 60000, 90000, 100, 'cuốn', 'Việt Nam', 'Nguyễn Văn C', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Khoa học', 2, 'TH'),
('T04', 'Toán lớp 3', 65000, 95000, 300, 'cuốn', 'Việt Nam', 'Nguyễn Văn D', 'NXB Giáo dục', 2019, 'Giáo khoa', 'Tiếng Việt', 'Toán', 3, 'TH'),
('T05', 'Tiếng Anh lớp 4', 70000, 100000, 250, 'cuốn', 'Việt Nam', 'Nguyễn Văn E', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Anh', 'Tiếng Anh', 4, 'TH'),
('T06', 'Đạo đức lớp 5', 45000, 75000, 350, 'cuốn', 'Việt Nam', 'Nguyễn Văn F', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Đạo đức', 5, 'TH'),
('T07', 'Lịch sử lớp 6', 55000, 85000, 200, 'cuốn', 'Việt Nam', 'Nguyễn Văn G', 'NXB Giáo dục', 2019, 'Giáo khoa', 'Tiếng Việt', 'Lịch sử', 6, 'THCS'),
('T08', 'Địa lý lớp 6', 50000, 80000, 300, 'cuốn', 'Việt Nam', 'Nguyễn Văn H', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Địa lý', 6, 'THCS'),
('T09', 'Toán lớp 7', 65000, 95000, 100, 'cuốn', 'Việt Nam', 'Nguyễn Văn I', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Toán', 7, 'THCS'),
('T10', 'Ngữ văn lớp 8', 70000, 100000, 150, 'cuốn', 'Việt Nam', 'Nguyễn Văn J', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Ngữ văn', 8, 'THCS'),
('T11', 'Hóa học lớp 9', 60000, 90000, 250, 'cuốn', 'Việt Nam', 'Nguyễn Văn K', 'NXB Giáo dục', 2019, 'Giáo khoa', 'Tiếng Việt', 'Hóa học', 9, 'THCS'),
('T12', 'Sinh học lớp 9', 60000, 90000, 200, 'cuốn', 'Việt Nam', 'Nguyễn Văn L', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Sinh học', 9, 'THCS'),
('T13', 'Vật lý lớp 10', 75000, 110000, 150, 'cuốn', 'Việt Nam', 'Nguyễn Văn M', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Vật lý', 10, 'THPT'),
('T14', 'Địa lý lớp 11', 65000, 95000, 300, 'cuốn', 'Việt Nam', 'Nguyễn Văn N', 'NXB Giáo dục', 2019, 'Giáo khoa', 'Tiếng Việt', 'Địa lý', 11, 'THPT'),
('T15', 'Lịch sử lớp 12', 70000, 100000, 100, 'cuốn', 'Việt Nam', 'Nguyễn Văn O', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Lịch sử', 12, 'THPT'),
('T16', 'Toán cao cấp', 85000, 120000, 150, 'cuốn', 'Việt Nam', 'Nguyễn Văn P', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Toán', 12, 'THPT'),
('T17', 'Hóa học đại cương', 90000, 130000, 200, 'cuốn', 'Việt Nam', 'Nguyễn Văn Q', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Hóa học', 12, 'THPT'),
('T18', 'Sinh học nâng cao', 85000, 120000, 250, 'cuốn', 'Việt Nam', 'Nguyễn Văn R', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Sinh học', 12, 'THPT'),
('T19', 'Giáo dục công dân lớp 10', 60000, 90000, 300, 'cuốn', 'Việt Nam', 'Nguyễn Văn S', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Giáo dục công dân', 10, 'THPT'),
('T20', 'Giáo dục thể chất lớp 11', 65000, 95000, 150, 'cuốn', 'Việt Nam', 'Nguyễn Văn T', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Giáo dục thể chất', 11, 'THPT'),
('T21', 'Ngữ văn lớp 12', 70000, 100000, 200, 'cuốn', 'Việt Nam', 'Nguyễn Văn U', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Ngữ văn', 12, 'THPT'),
('T22', 'Văn học nước ngoài', 60000, 90000, 300, 'cuốn', 'Việt Nam', 'Nguyễn Văn V', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Văn học', 12, 'THPT'),
('T23', 'Giáo trình tiếng Anh', 80000, 110000, 150, 'cuốn', 'Việt Nam', 'Nguyễn Văn W', 'NXB Giáo dục', 2019, 'Giáo khoa', 'Tiếng Anh', 'Tiếng Anh', 12, 'THPT'),
('T24', 'Đề thi môn Toán', 45000, 70000, 200, 'cuốn', 'Việt Nam', 'Nguyễn Văn X', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Toán', 12, 'THPT'),
('T25', 'Tài liệu ôn thi THPT', 95000, 130000, 100, 'cuốn', 'Việt Nam', 'Nguyễn Văn Y', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Ôn thi', 12, 'THPT'),
('T26', 'Giáo trình sinh học', 60000, 90000, 250, 'cuốn', 'Việt Nam', 'Nguyễn Văn Z', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Sinh học', 12, 'THPT'),
('T27', 'Hóa học thực hành', 75000, 110000, 150, 'cuốn', 'Việt Nam', 'Nguyễn Văn AA', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Hóa học', 12, 'THPT'),
('T28', 'Lịch sử thế giới', 70000, 100000, 100, 'cuốn', 'Việt Nam', 'Nguyễn Văn BB', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Lịch sử', 12, 'THPT'),
('T29', 'Địa lý Việt Nam', 65000, 95000, 300, 'cuốn', 'Việt Nam', 'Nguyễn Văn CC', 'NXB Giáo dục', 2021, 'Giáo khoa', 'Tiếng Việt', 'Địa lý', 12, 'THPT'),
('T30', 'Ngữ văn nâng cao', 85000, 120000, 150, 'cuốn', 'Việt Nam', 'Nguyễn Văn DD', 'NXB Giáo dục', 2020, 'Giáo khoa', 'Tiếng Việt', 'Ngữ văn', 12, 'THPT');

-- create 3 new tables
create table orders(
	order_id varchar(45),
    product_id varchar(45),
    order_date varchar(45),
    total_amount int,
    total_price double,
    status varchar(45)
);

create table reports(
	report_id varchar(45),
    report_type varchar(255),
    generated_date datetime,
    content varchar(5000)
);

--
CREATE TABLE Attendance (
    id VARCHAR(40),
    date DATE,
    status VARCHAR(10),
    PRIMARY KEY (id, date)
);

