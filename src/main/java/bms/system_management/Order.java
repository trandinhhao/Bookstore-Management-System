package bms.system_management;

import bms.product.Product;
import java.util.*;

public class Order {
    private String id;
    private LinkedHashMap<Product, Integer> products;
    private double totalAmount;

    public Order(String id, LinkedHashMap<Product, Integer> products, double totalAmount) {
        this.id = id;
        this.products = products;
        this.totalAmount = totalAmount;
    }
    
}
