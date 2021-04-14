package com.vishal.expensetracker;

public class Users {

    String uid;
    String name;
    String age;
    String email;
    String password;
    int savings;

    public Users(){

    }

    public Users(String uid, String name, String age, String email, String password,int savings) {
        this.uid = uid;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.savings=savings;
    }

    public int getSavings(){
        return savings;
    }

    public void setSaving(int savings){
        this.savings=savings;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
