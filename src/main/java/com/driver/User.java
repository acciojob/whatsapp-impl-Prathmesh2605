package com.driver;

public class User {
    private String name;
    private String mobile;

    public User(String name, String mobileNumber) {
        this.name=name;
        this.mobile = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
