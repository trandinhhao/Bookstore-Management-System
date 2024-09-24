
package bms.product;

public class Book extends Product {
    protected String author;
    protected String publisher;
    protected int publicationYear;
    protected String genre;
    protected String language;

    public Book(String author, String publisher, int publicationYear, String genre, String language, String id, String name, double costPrice, double salePrice, int quantity, String category, String origin) {
        super(id, name, costPrice, salePrice, quantity, category, origin);
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.language = language;
    }
    
}
