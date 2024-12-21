package com.example.garageko;

public class Car {
    private String ownerName;
    private String carModel;
    private int carImage;

    public Car(String ownerName, String carModel, int carImage) {
        this.ownerName = ownerName;
        this.carModel = carModel;
        this.carImage = carImage;
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
}

