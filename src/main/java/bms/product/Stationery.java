package bms.product;

public class Stationery extends Product{
    private String type;
    private String manufacturer;
    private String material;

    public Stationery(String type, String manufacturer, String material, String id, String name, double costPrice, double salePrice, int quantity, String category, String origin) {
        super(id, name, costPrice, salePrice, quantity, category, origin);
        this.type = type;
        this.manufacturer = manufacturer;
        this.material = material;
    }
    
}
