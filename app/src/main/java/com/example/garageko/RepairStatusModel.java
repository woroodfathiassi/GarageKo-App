package com.example.garageko;

public class RepairStatusModel {
    private String title;
    private boolean isCompleted;
    private boolean isAwaitingParts;
    private boolean isInProgress;

    public RepairStatusModel(String title, boolean isCompleted, boolean isAwaitingParts, boolean isInProgress) {
        this.title = title;
        this.isCompleted = isCompleted;
        this.isAwaitingParts = isAwaitingParts;
        this.isInProgress = isInProgress;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isAwaitingParts() {
        return isAwaitingParts;
    }

    public boolean isInProgress() {
        return isInProgress;
    }
}
