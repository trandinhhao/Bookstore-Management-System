package bms.product;

public class Gift extends Product{
    private String type;
    private String material;

    public Gift(String type, String material, String id, String name, double costPrice, double salePrice, int quantity, String category, String origin) {
        super(id, name, costPrice, salePrice, quantity, category, origin);
        this.type = type;
        this.material = material;
    }
    
}
