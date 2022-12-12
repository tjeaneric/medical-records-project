package com.medical.demo.models;

import java.util.HashMap;

public class Pharmacist extends User{
    public Pharmacist() {
        this.setRole(Role.PHARMACIST);
    }


    public Pharmacist(User user){
        this.setRole(Role.PHARMACIST);
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setGender(user.getGender());
        this.setAge(user.getAge());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
    }

    public Pharmacist(HashMap<String, Object> user) {
        this.setRole(Role.PHARMACIST);
        this.setFirstName(user.get("firstName").toString());
        this.setLastName(user.get("lastName").toString());
        this.setGender(Gender.valueOf(user.get("gender").toString()));
        this.setAge((int)(user.get("age")));
        this.setPassword(user.get("password").toString());
        this.setEmail(user.get("email").toString());
    }


    @Override
    public User signup() throws Exception{

//        if (this.getPassword() == null || this.getPassword().isBlank() || this.getPassword().length() != 8){
//            throw new Exception("password must be 8 characters");
//        }
        this.validate();

        return this;
    }

}
