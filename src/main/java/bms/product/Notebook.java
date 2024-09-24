package bms.product;

public class Notebook extends Product {
    private int pageCount;
    private String paperType;
    private String size;
    private String manufacturer;

    public Notebook(int pageCount, String paperType, String size, String manufacturer, String id, String name, double costPrice, double salePrice, int quantity, String category, String origin) {
        super(id, name, costPrice, salePrice, quantity, category, origin);
        this.pageCount = pageCount;
        this.paperType = paperType;
        this.size = size;
        this.manufacturer = manufacturer;
    }
    
}
