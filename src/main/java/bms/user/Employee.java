package bms.user;

public class Employee extends Person{
    private String position;

    public Employee(String position, String id, String name, String birth, String address, String phoneNumber, String email) {
        super(id, name, birth, address, phoneNumber, email);
        this.position = position;
    }
    
}
