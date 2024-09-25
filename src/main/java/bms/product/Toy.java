package bms.product;

public class Toy extends Product {

    private String type;
    private String ageRange;
    private String material;

    public Toy(String type, String ageRange, String material, String id, String name, double costPrice, double salePrice, int quantity, String unit, String origin) {
        super(id, name, costPrice, salePrice, quantity, unit, origin);
        this.type = type;
        this.ageRange = ageRange;
        this.material = material;
    }

}
