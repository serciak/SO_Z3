package com.company;

public class Page {
    private int id;
    private int arrivalTime;
    private int lastUsedTime;
    private int bit;

    public Page(int id) {
        this.id = id;
        arrivalTime = 0;
        lastUsedTime = 0;
        bit = -1;
    }

    public String toString() {
        return id + "\t" + arrivalTime;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getLastUsedTime() {
        return lastUsedTime;
    }

    public int getBit() {
        return bit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setLastUsedTime(int lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }
}
