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
    
    public String getCode() { return id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    

}
