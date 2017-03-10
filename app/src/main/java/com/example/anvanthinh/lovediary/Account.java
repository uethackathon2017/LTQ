package com.example.anvanthinh.lovediary;

public class Account  {
    private String name;
    private String pass;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Account (){}

    public Account (String name,String pass, String phone){
        this.name = name;
        this.pass = pass;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
