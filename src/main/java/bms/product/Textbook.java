package bms.product;

public class Textbook extends Book {
    private String subject;
    private int grade;
    private String eduLevel;

    public Textbook(String subject, int grade, String eduLevel, String author, String publisher, int publicationYear, String genre, String language, String id, String name, double costPrice, double salePrice, int quantity, String category, String origin) {
        super(author, publisher, publicationYear, genre, language, id, name, costPrice, salePrice, quantity, category, origin);
        this.subject = subject;
        this.grade = grade;
        this.eduLevel = eduLevel;
    }
    
}
