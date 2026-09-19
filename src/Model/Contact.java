package Model;

public class Contact {

    public Contact(String number, String name, String email)
    {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    private String name;
    private String number;
    private String email;

    public String getContactName() {return name;}

    public String getNumber() {return number;}

    public String getEmail() {return email;}

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
