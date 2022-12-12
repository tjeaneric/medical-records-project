package com.medical.demo.models;

public class LoginRequest {
    String email;
    String password;

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

    public void validate() throws Exception{
        if (this.getEmail() == null || this.getEmail().isBlank()){
            throw new Exception("Please enter your email");
        }

        if (this.getPassword() == null || this.getPassword().isBlank()){
            throw new Exception("Please enter password");
        }


}}
