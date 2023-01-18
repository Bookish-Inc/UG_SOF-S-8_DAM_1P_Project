package com.example.bookish_bookshop_app.Module_1;

public class Model_Credential {
    // Variables
    public int id;
    public String username;
    public String password;

    // Constructor default
    public Model_Credential() {
        this.id = 0;
        this.username = "";
        this.password = "";
    }

    // Constructor Parameterized
    public Model_Credential(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
