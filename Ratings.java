package com.example.joeco.ecotaxiphoneapp;

public class Ratings {

    private String rating;
    private String email;

    public Ratings(String rating, String email){

        this.rating = rating;

        this.email = email;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
