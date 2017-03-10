package com.example.anvanthinh.lovediary;

public class Account  {
    private String name;
    private String pass;
    private String phone;
    private String subPhone;

    public Account (){}

    public Account (String name,String pass, String phone, String subPhone){
        this.name = name;
        this.pass = pass;
        this.phone = phone;
        this.subPhone = subPhone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubPhone() {
        return subPhone;
    }

    public void setSubPhone(String subPhone) {
        this.subPhone = subPhone;
    }
}
