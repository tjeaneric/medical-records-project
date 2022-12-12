package com.medical.demo.models;

import java.util.HashMap;

public class Patient extends User{
    public Patient() {
        this.setRole(Role.PATIENT);
    }


    public Patient(User user){
        this.setRole(Role.PATIENT);
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setGender(user.getGender());
        this.setAge(user.getAge());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
    }

    public Patient(HashMap<String, Object> user) {
        this.setRole(Role.PATIENT);
        this.setFirstName(user.get("firstName").toString());
        this.setLastName(user.get("lastName").toString());
        this.setGender(Gender.valueOf(user.get("gender").toString()));
        this.setAge((int)(user.get("age")));
        this.setPassword(user.get("password").toString());
        this.setEmail(user.get("email").toString());
    }


    @Override
    public User signup() throws Exception{

//        if (this.getPassword() == null || this.getPassword().isBlank() || this.getPassword().length() != 6){
//            throw new Exception("password must be 6 characters");
//        }
        this.validate();

        return this;
    }

}

