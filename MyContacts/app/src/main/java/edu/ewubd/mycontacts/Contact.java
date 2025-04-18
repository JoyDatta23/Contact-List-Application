package edu.ewubd.mycontacts;
public class Contact {
    private long id;
    private String name;
    private String email;
    private String homePhone;
    private String officePhone;
    private String imageUri;

    public Contact(long id, String name, String email, String homePhone, String officePhone, String imageUri) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.homePhone = homePhone;
        this.officePhone = officePhone;
        this.imageUri = imageUri;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public String getImageUri() {
        return imageUri;
    }

    @Override
    public String toString() {
        return name;
    }
}
