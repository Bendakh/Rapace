package com.example.sbendakhlia.rapace;

public class User {
    private String name;
    private String email;
    private String password;
    private boolean admin;
    private String id;

    public User() {
    }

    public User(String name, String email, String password, boolean admin, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
