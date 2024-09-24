package bms.product;

public class Product {
    protected String id;
    protected String name;
    protected double costPrice;
    protected double salePrice;
    protected int quantity;
    protected String category;
    protected String origin;

    public Product(String id, String name, double costPrice, double salePrice, int quantity, String category, String origin) {
        this.id = id;
        this.name = name;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.category = category;
        this.origin = origin;
    }
    
    public void addProduct(Product product) {
        this.id = product.id;
        this.name = product.name;
        this.costPrice = product.costPrice;
        this.salePrice = product.salePrice;
        this.quantity = product.quantity;
        this.category = product.category;
        this.origin = product.origin;
    }
    
}
