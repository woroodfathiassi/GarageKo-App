package com.example.garageko;

public class Invoice {
    private int imageId;
    private String date;
    private double price;
    private String detail;

    public static final Invoice[] invoices = {
            new Invoice(R.drawable.tools, "2024-12-01", 29.99, "Oil Change"),
            new Invoice(R.drawable.tools, "2024-12-02", 45.50, "Tire Replacement"),
            new Invoice(R.drawable.tools, "2024-12-03", 12.75, "Battery Check"),
            new Invoice(R.drawable.tools, "2024-12-04", 60.00, "Brake Service"),
            new Invoice(R.drawable.tools, "2024-12-05", 25.30, "Car Wash")
    };

    public Invoice(int imageId, String date, double price, String detail) {
        this.imageId = imageId;
        this.date = date;
        this.price = price;
        this.detail = detail;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
