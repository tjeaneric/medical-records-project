package com.medical.demo.models;

import com.medical.demo.utils.PasswordManager;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


public abstract class User {


    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private Role role;
    private Gender gender;

    private PasswordManager pm = new PasswordManager();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = this.pm.getPasswordHash(password);
    }


    public void validate() throws Exception{
        if (this.getFirstName() == null || this.getFirstName().isBlank()){
            throw new Exception("Please enter first name");
        }

        if (this.getLastName() == null || this.getLastName().isBlank()){
            throw new Exception("Please enter last name");
        }
        if (this.getEmail() == null || this.getEmail().isBlank()){
            throw new Exception("Please enter email");
        }
        if (this.getAge() < 1){
            throw new Exception("age cannot be less than 0");
        }
        if (this.getGender() == null){
            throw new Exception("Please enter gender");
        }

    }
    public boolean login(User user, String password){
        return this.pm.isPasswordCorrect(password, user.getPassword());
    }

    public abstract User signup() throws Exception;


    @Override
    public String toString() {
        return this.getEmail() + "," + this.getFirstName() + " " + this.getLastName() + "," + this.getRole().name();
    }
}

