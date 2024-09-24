package bms.user;

public class Person {
    protected String id;
    protected String name;
    protected String birth;
    protected String address;
    protected String phoneNumber;
    protected String email;

    public Person(String id, String name, String birth, String address, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
}
