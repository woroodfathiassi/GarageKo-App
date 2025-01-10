package com.example.garageko;

public class RepairStatusModel {
    private String title;
    private boolean isCompleted;
    private boolean isOk;
    private boolean isInProgress;

    public RepairStatusModel(String title, boolean isCompleted, boolean isOk, boolean isInProgress) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.isOk = isOk;
        this.isInProgress = isInProgress;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isOk() {
        return isOk;
    }

    public boolean isInProgress() {
        return isInProgress;
    }
}
