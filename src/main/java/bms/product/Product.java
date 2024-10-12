package bms.product;

public class Product {

    protected String id;
    protected String name;
    protected double costPrice;
    protected double salePrice;
    protected int quantity;
    protected String unit;
    protected String origin;

    public Product(String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        this.id = id;
        this.name = name;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.unit = unit;
        this.origin = origin;
    }

    public String getId() {
        return id;
    }

    public double getSalePrice() {
        return salePrice;
    }

}
