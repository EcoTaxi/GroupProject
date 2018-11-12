package com.example.joeco.ecotaxiphoneapp;

public class User {

    private String name, email,dob,password, phonenum, address, visa;

    public User(){
    }
    public User(String name, String email, String dob, String password, String phonenum, String address) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.phonenum = phonenum;
        this.address = address;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
