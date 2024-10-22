package bms.system_management;

import bms.connectDB.ConnectMySQL;
import bms.product.Product;
import java.sql.*;
import java.util.*;

public class Supplier {

    private int supplierID;
    private String name;
    private String contactInfo;

    public Supplier(int suppliedID, String name, String contactInfo) {
        this.supplierID = suppliedID;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public PreparedStatement getprepareStatement(String sqlString) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectMySQL.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sqlString);
        return pstmt;
    }

    // method to add a new supplier to database 
    public void addSupplier() throws ClassNotFoundException {
        String sqlString = "INSERT INTO supplies (name, contactInfo) VALUES (? ?)";
        try {
            PreparedStatement pstmt = this.getprepareStatement(sqlString);
            pstmt.setString(1, this.name);
            pstmt.setString(2, this.contactInfo);
            pstmt.executeUpdate();
            System.out.println("Supplier added succesfully");

        } catch (SQLException e) {
            System.out.println("Error adding supplier" + e.getMessage());
        }
    }

    // method to update a supplier to database 
    public void updateSupplier() throws ClassNotFoundException {
        String sqlString = "UPDATE suppliers SET name = ?";
        try {
            PreparedStatement pstmt = this.getprepareStatement(sqlString);
            pstmt.setInt(1, this.supplierID);
            pstmt.setString(2, this.contactInfo);
            pstmt.setString(3, this.contactInfo);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Supplier updated successfully.");
            } else {
                System.out.println("No supplier found with ID: " + this.supplierID);
            }
        } catch (SQLException e) {
            System.out.println("Error updating supplier" + e.getMessage());
        }
    }

    // mdethod to delete a supplier
    public void deleteSupplier() throws ClassNotFoundException {
        String sqlString = "DELETE FROM suppliers supplierID = ?";
        try {
            PreparedStatement pstmt = this.getprepareStatement(sqlString);
            pstmt.setInt(1, this.supplierID);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Supplier deleted sucessfull.");
            } else {
                System.out.println("No supplier found with ID: " + this.supplierID);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting supplier:" + e.getMessage());
        }
    }

    // Method to get all suppliers
    public static ArrayList<Supplier> getAllSupplier() throws ClassNotFoundException {
        ArrayList<Supplier> suppliers = new ArrayList<>();

        String sqlString = "SELECT * FROM suppliers";
        try {
            Connection conn = ConnectMySQL.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlString);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlString);
            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getInt("supplierID"),
                        rs.getString("name"),
                        rs.getString("contactInfo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving suppliers: " + e.getMessage());
        }
        return suppliers;
    }

    // method to get supplied products for a supplier
    public ArrayList<Product> getSuppliedProducts() throws ClassNotFoundException {
        ArrayList<Product> products = new ArrayList<>();
        String sqlString = "SELECT p.* FROM product p" + "JOIN supplier_products sp ON p.id = sp.id" + "WHERE sp.supplierID = ?";
        try {
            PreparedStatement pstmt = this.getprepareStatement(sqlString);
            pstmt.setInt(1, this.supplierID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getDouble("costPrice"),
                            rs.getDouble("salePrice"),
                            rs.getInt("quantity"),
                            rs.getString("unit"),
                            rs.getString("origin")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("");
        }
        return products;
    }

    @Override
    public String toString() {
        return "Supplier{"
                + "supplierId=" + this.supplierID
                + ", name='" + name + '\''
                + ", contactInfo='" + contactInfo + '\''
                + '}';
    }
}
