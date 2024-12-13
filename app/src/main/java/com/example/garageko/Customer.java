package com.example.garageko;

public class Customer {
    private String name;
    private int rating;
    private String notes;
    private int imageResId;

    public Customer(String name, int rating, String notes, int imageResId) {
        this.name = name;
        this.rating = rating;
        this.notes = notes;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getNotes() {
        return notes;
    }

    public int getImageResId() {
        return imageResId;
    }
}
