package com.medical.demo.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordManager {

    private final Argon2 argon2;

    public PasswordManager() {
        this.argon2 = Argon2Factory.create(
                Argon2Factory.Argon2Types.ARGON2id,
                16,
                32);;
    }

    public String getPasswordHash(String password) {

            return argon2.hash(3, // Number of iterations
                    64 * 1024, // 64mb
                    1, // how many parallel threads to use
                    password);
        }

    public boolean isPasswordCorrect(String passowrd, String hash) {
        return argon2.verify(hash, passowrd);
    }
}
