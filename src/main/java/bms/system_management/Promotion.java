package bms.system_management;
import bms.connectDB.ConnectMySQL;
import java.sql.*;
import java.util.ArrayList;

public class Promotion {
    private int id;
    private int idPromotion;
    private String namePromotion;
    private String description;
    private double discount;
    private Date startDate;
    private Date endDate;
    
    public static PreparedStatement getprepareStatement(String sqlString) throws SQLException{
        Connection conn = ConnectMySQL.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sqlString);
        return  pstmt;
    }
    
    public Promotion(int idPromotion, String namePromotion, String description, double discount, Date startDate, Date endDate) {
        this.idPromotion = idPromotion;
        this.namePromotion = namePromotion;
        this.description = description;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public void save () throws SQLException {
        String sqlString = "INSERT INTO promotions (namePromotion, description, discount, startDate, endDate) VALUES (?, ?, ?, ?, ?)";
        try{
            PreparedStatement pstmt = Promotion.getprepareStatement(sqlString);
            pstmt.setString(1, this.namePromotion);
            pstmt.setString(2, this.description);
            pstmt.setDouble(3, this.discount);
            pstmt.setDate(4, new java.sql.Date(this.startDate.getTime()));
            pstmt.setDate(5, new java.sql.Date(this.endDate.getTime()));
            pstmt.executeUpdate();
            
            try (ResultSet generateKeys = pstmt.getGeneratedKeys()){
                if(generateKeys.next()){
                    this.idPromotion = generateKeys.getInt(1);
                }
            }
        } catch (SQLException e){
            System.out.println("Error while save promotion" + e.getMessage());
        }
    }
    public void update() throws SQLException{
        String sqlString = "UPDATE promotions SET name = ?, description = ?, discount_percentage = ?, start_date = ?, end_date = ? WHERE id = ?";
        try{
            PreparedStatement pstmt = Promotion.getprepareStatement(sqlString);
            pstmt.setString(1, this.namePromotion);
            pstmt.setString(2, this.description);
            pstmt.setDouble(3, this.discount);
            pstmt.setDate(4, new java.sql.Date(this.startDate.getTime()));
            pstmt.setDate(5, new java.sql.Date(this.endDate.getTime()));
            pstmt.setInt(6,  this.idPromotion);
            pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error while update promotion" + e.getMessage());
        }
    }
    
    public void delete() throws SQLException{
        String sqlString = "DELETE FROM promotions WHERE id = ?";
        try (PreparedStatement pstmt = Promotion.getprepareStatement(sqlString)){
            pstmt.setInt(1, this.idPromotion);
            pstmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error while delete promotion" + e.getMessage());
        }
    }
    
    public static Promotion getByID(int id) throws SQLException{
        String sqlString = "SELECT * FROM promotions WHERE id = ?";
        try (PreparedStatement pstmt = Promotion.getprepareStatement(sqlString)){
            pstmt.setInt(1, id);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    return new Promotion(
                            rs.getInt("id"), 
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("discount_percentage"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"));
                }
            }
        } catch (SQLException e){
            System.out.println("Error get by ID from promotion" + e.getMessage());
        }
        return null;
    }
    
    public static ArrayList<Promotion> getAll() throws SQLException{
        ArrayList<Promotion> promotions = new ArrayList<>();
        String sqlString = "SELECT * FROM promotions";
        Connection conn = ConnectMySQL.getConnection();
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery(sqlString);
            while(rs.next()){
                promotions.add(new Promotion(
                            rs.getInt("id"), 
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("discount_percentage"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date")));
            }
        }
        return promotions;
    }
}
