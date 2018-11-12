package com.example.joeco.ecotaxiphoneapp;

public class Customer {

    private String email, password,visaNum,phoneNum, name, address, dob, cardName, expire, csv;

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

    public String getVisaNum() {
        return visaNum;
    }

    public void setVisaNum(String visaNum) {
        this.visaNum = visaNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

    public Customer(String email, String password, String visaNum, String phoneNum, String name, String address, String dob, String cardName, String expire, String csv){
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.visaNum = visaNum;
        this.cardName = cardName;
        this.expire = expire;
        this.csv = csv;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    public Customer(){
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
    }

}
