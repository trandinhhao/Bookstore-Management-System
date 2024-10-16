package bms.product;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Book extends Product {

    private String author;
    private String publisher;
    private int publicationYear;
    private String genre;
    private String language;
    private String publishDate; // Định dạng: dd/MM/yyyy
    private String imagePath;

    public Book(String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin,
                String author, String publisher, int publicationYear, String genre, String language, String publishDate, String imagePath) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.language = language;
        this.publishDate = publishDate;
        this.imagePath = imagePath;
    }

    // Getters và Setters cho các thuộc tính riêng
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getPublishDate() { return publishDate; }
    public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    // Định nghĩa định dạng lưu trữ trong tệp tin
    @Override
    public String toString() {
        // Sử dụng dấu phân cách ";" để tránh vấn đề với dấu phẩy trong dữ liệu
        return id + ";" + name + ";" + author + ";" + publisher + ";" + genre + ";" + salePrice + ";" +
               quantity + ";" + unit + ";" + origin + ";" + publishDate + ";" + imagePath;
    }

    // Phương thức để tạo đối tượng Book từ một dòng dữ liệu trong tệp tin
    public static Book fromString(String line) {
        String[] data = line.split(";");
        if (data.length < 11) {
            return null; // Dữ liệu không hợp lệ
        }
        try {
            String id = data[0];
            String name = data[1];
            String author = data[2];
            String publisher = data[3];
            String genre = data[4];
            double salePrice = Double.parseDouble(data[5]);
            int quantity = Integer.parseInt(data[6]);
            String unit = data[7];
            String origin = data[8];
            String publishDate = data[9];
            String imagePath = data[10];
            // Giá bán và các giá trị khác có thể cần được điều chỉnh tùy thuộc vào yêu cầu
            return new Book(id, name, 0.0, salePrice, quantity, unit, origin,
                            author, publisher, 0, genre, "Vietnamese", publishDate, imagePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Phương thức tải tất cả sách từ tệp tin
    public static List<Book> loadBooksFromFile(String filename) {
        List<Book> books = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            // Nếu tệp tin không tồn tại, tạo mới tệp tin
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return books;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Book book = Book.fromString(line);
                if (book != null) {
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Phương thức lưu tất cả sách vào tệp tin
    public static void saveBooksToFile(List<Book> books, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                writer.write(book.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức thêm một sách mới
    public static void addBook(Book newBook, List<Book> books, String filename) {
        books.add(newBook);
        saveBooksToFile(books, filename);
    }

    // Phương thức xóa một sách theo ID
    public static void deleteBook(String id, List<Book> books, String filename) {
        books.removeIf(book -> book.getCode().equals(id));
        saveBooksToFile(books, filename);
    }

    // Phương thức cập nhật một sách
    public static void updateBook(Book updatedBook, List<Book> books, String filename) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getCode().equals(updatedBook.getCode())) {
                books.set(i, updatedBook);
                break;
            }
        }
        saveBooksToFile(books, filename);
    }
}