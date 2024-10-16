package bms.product;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Textbook extends Book {
    private String subject;
    private int grade;
    private String eduLevel;

    // Constructor
    public Textbook(String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin,
                   String author, String publisher, int publicationYear, String genre, String language,
                   String publishDate, String imagePath, String subject, int grade, String eduLevel) {
        super(id, name, costPrice, salePrice, quantity, unit, origin, author, publisher, publicationYear, genre, language, publishDate, imagePath);
        this.subject = subject;
        this.grade = grade;
        this.eduLevel = eduLevel;
    }

    // Getters và Setters cho các thuộc tính riêng
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    public String getEduLevel() { return eduLevel; }
    public void setEduLevel(String eduLevel) { this.eduLevel = eduLevel; }

    // Định nghĩa định dạng lưu trữ trong tệp tin
    @Override
    public String toString() {
        // Sử dụng dấu phân cách ";" để tránh vấn đề với dấu phẩy trong dữ liệu
        return super.toString() + ";" + subject + ";" + grade + ";" + eduLevel;
    }

    // Phương thức để tạo đối tượng Textbook từ một dòng dữ liệu trong tệp tin
    public static Textbook fromString(String line) {
        String[] data = line.split(";");
        if (data.length < 14) { // 11 từ Book + 3 từ Textbook
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
            String subject = data[11];
            int grade = Integer.parseInt(data[12]);
            String eduLevel = data[13];

            // Giá bán và các giá trị khác có thể cần được điều chỉnh tùy thuộc vào yêu cầu
            return new Textbook(id, name, 0.0, salePrice, quantity, unit, origin,
                                author, publisher, 0, genre, "Vietnamese", publishDate, imagePath,
                                subject, grade, eduLevel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Phương thức tải tất cả sách giáo khoa từ tệp tin
    public static List<Textbook> loadTextbooksFromFile(String filename) {
        List<Textbook> textbooks = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            // Nếu tệp tin không tồn tại, tạo mới tệp tin
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return textbooks;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Textbook textbook = Textbook.fromString(line);
                if (textbook != null) {
                    textbooks.add(textbook);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textbooks;
    }

    // Phương thức lưu tất cả sách giáo khoa vào tệp tin
    public static void saveTextbooksToFile(List<Textbook> textbooks, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Textbook textbook : textbooks) {
                writer.write(textbook.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức thêm một sách giáo khoa mới
    public static void addTextbook(Textbook newTextbook, List<Textbook> textbooks, String filename) {
        textbooks.add(newTextbook);
        saveTextbooksToFile(textbooks, filename);
    }

    // Phương thức xóa một sách giáo khoa theo ID
    public static void deleteTextbook(String id, List<Textbook> textbooks, String filename) {
        textbooks.removeIf(textbook -> textbook.getCode().equals(id));
        saveTextbooksToFile(textbooks, filename);
    }

    // Phương thức cập nhật một sách giáo khoa
    public static void updateTextbook(Textbook updatedTextbook, List<Textbook> textbooks, String filename) {
        for (int i = 0; i < textbooks.size(); i++) {
            if (textbooks.get(i).getCode().equals(updatedTextbook.getCode())) {
                textbooks.set(i, updatedTextbook);
                break;
            }
        }
        saveTextbooksToFile(textbooks, filename);
    }
}
