package com.app.delivery.core.domain.models;

public class Restaurant {
    private int id;
    private String name;
    private String address;
    private String phone;
    private boolean active;

    public Restaurant() {
    }

    public Restaurant(int id, String name, String address, String phone, boolean active) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.active = active;
    }

    public Restaurant(String name, String address, String phone){
        this(0,name,address, phone, true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
