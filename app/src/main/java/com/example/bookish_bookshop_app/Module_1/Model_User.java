package com.example.bookish_bookshop_app.Module_1;

public class Model_User {

    // Variables
    public int id;
    public String name;
    public String lastname;
    public String phone;
    public String email;
    public String genre;
    public String occupation;
    public int id_credential;

    // Constructor Default
    public Model_User() {
        this.id = 0;
        this.name = "";
        this.lastname = "";
        this.phone = "";
        this.email = "";
        this.genre = "";
        this.occupation = "";
        this.id_credential = 0;
    }

    // Constructor Parameterized
    public Model_User(int id, String name, String lastname, String phone, String email, String genre, String occupation, int id_credential) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.genre = genre;
        this.occupation = occupation;
        this.id_credential = id_credential;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getGenre() {
        return genre;
    }

    public String getOccupation() {
        return occupation;
    }

    public int getId_credential() {
        return id_credential;
    }


    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setId_credential(int id_credential) {
        this.id_credential = id_credential;
    }

}
