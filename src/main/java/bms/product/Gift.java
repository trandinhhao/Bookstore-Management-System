package bms.product;

public class Gift extends Product{
    private String type;
    private String material;

    public Gift(String type, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.type = type;
        this.material = material;
    }

    
    
}
