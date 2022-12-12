package com.medical.demo.models;

import java.util.HashMap;

public class Admin extends User{
    public Admin() {
        this.setRole(Role.ADMIN);
    }

    public Admin(User user){
        this.setRole(Role.ADMIN);
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setGender(user.getGender());
        this.setAge(user.getAge());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
    }

    public Admin(HashMap<String, Object> user) {
        this.setRole(Role.ADMIN);
        this.setFirstName(user.get("firstName").toString());
        this.setLastName(user.get("lastName").toString());
        this.setGender(Gender.valueOf(user.get("gender").toString()));
        this.setAge((int)(user.get("age")));
        this.setPassword(user.get("password").toString());
        this.setEmail(user.get("email").toString());
    }


    @Override
    public User signup() throws Exception{
        this.validate();

        return this;
    }


}
