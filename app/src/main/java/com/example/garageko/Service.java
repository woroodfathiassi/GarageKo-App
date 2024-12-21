package com.example.garageko;

public class Service {
    private int imageId;
    private String service;

    public static final Service[] invoices = {
            new Service(R.drawable.oil, "Oil Change"),
            new Service(R.drawable.breakk, "Tire Replacement"),
            new Service(R.drawable.tire, "Battery Check"),
    };

    public Service(int imageId, String service) {
        this.imageId = imageId;
        this.service = service;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
