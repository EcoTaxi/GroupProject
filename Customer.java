package com.example.joeco.ecotaxiphoneapp;

import com.braintreepayments.cardform.view.CardForm;

public class Customer {

    private String email, password,phoneNum, name, address, dob;
    private CardForm cardForm;

    public Customer(String email, String password, String phoneNum, String name, String address, String dob){
        this.name = name;
        this.email = email;
        this.dob = dob;
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

    public CardForm getCardForm() {
        return cardForm;
    }

    public void setCardForm(CardForm cardForm) {
        this.cardForm = cardForm;
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

}