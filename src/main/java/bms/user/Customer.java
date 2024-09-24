package bms.user;

public class Customer extends Person{
    private int loyaltyPoints;
    private String registerDate;
    private String membershipTier;
    private int discount;

    public Customer(int loyaltyPoints, String registerDate, String membershipTier, int discount, String id, String name, String birth, String address, String phoneNumber, String email) {
        super(id, name, birth, address, phoneNumber, email);
        this.loyaltyPoints = loyaltyPoints;
        this.registerDate = registerDate;
        this.membershipTier = membershipTier;
        this.discount = discount;
    }
    
}
