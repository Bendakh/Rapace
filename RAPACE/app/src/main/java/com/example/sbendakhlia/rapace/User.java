package com.example.sbendakhlia.rapace;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

public class User {
    private String name;
    private String email;
    private String password;
    private boolean admin;
    private String id;
    private String lastChangedPasswordDate;
    private int nDays;

    public User() {
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = formater.format(todayDate);
        this.lastChangedPasswordDate = thisDate;

        this.nDays = 30;
    }

    public User(String name, String email, String password, boolean admin, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.id = id;

        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = formater.format(todayDate);
        this.lastChangedPasswordDate = thisDate;

        this.nDays = 30;
    }

    public int getnDays() {
        return nDays;
    }

    public void setnDays(int nDays) {
        this.nDays = nDays;
    }

    public String getLastChangedPasswordDate() {
        return lastChangedPasswordDate;
    }

    public void setLastChangedPasswordDate(String lastChangedPasswordDate) {
        this.lastChangedPasswordDate = lastChangedPasswordDate;
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


    public static long GetNumberOfDaysSinceLastPasswordChange(String lastDate) throws ParseException {
        Date tempDate = new SimpleDateFormat("dd/MM/yyyy").parse(lastDate);
        Date todayDate = new Date();
        long diff = (todayDate.getTime() - tempDate.getTime()) / 86400000;
        return Math.abs(diff);
    }
}
