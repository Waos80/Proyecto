package org.example.contact;

import java.time.LocalDate;

public class Contact {
    public int id;
    public String name;
    public String lastName;
    public String username;
    public String phoneNumber;
    public String email;
    public String address;
    public LocalDate birthDate;

    public Contact(int id) {
        this.id = id;
        this.name = "";
        this.lastName = "";
        this.username = "";
        this.phoneNumber = "";
        this.email = "";
        this.address = "";
        this.birthDate = null;
    }
}
