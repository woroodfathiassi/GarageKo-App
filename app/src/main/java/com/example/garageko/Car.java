package com.example.garageko;

public class Car {
    private String ownerName;
    private String carModel;
    private int carImage;
    private int request_id;

    public Car(String ownerName, String carModel, int carImage) {
        this.ownerName = ownerName;
        this.carModel = carModel;
        this.carImage = carImage;
    }
    public Car(String ownerName, String carModel, int carImage, int request) {
        this.ownerName = ownerName;
        this.carModel = carModel;
        this.carImage = carImage;
        this.request_id = request;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getCarImage() {
        return carImage;
    }

    public int getRequest_id(){
        return request_id;
    }
}

