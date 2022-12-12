package com.medical.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.medical.demo.models.User;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

public class JwtUtil {

    private Algorithm algorithm;
    private JWTVerifier verifier;

    private static JwtUtil instance = null;

    public static JwtUtil getInstance()
    {
        if (instance == null)
            instance = new JwtUtil();

        return instance;
    }


    public JwtUtil() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.generateKeyPair();

            RSAPublicKey rPubKey = (RSAPublicKey) kp.getPublic();
            RSAPrivateKey rPriKey = (RSAPrivateKey) kp.getPrivate();

            algorithm = Algorithm.RSA256(rPubKey, rPriKey);

            verifier = JWT.require(algorithm)
                    // specify an specific claim validations
                    .withIssuer("auth0")
                    // reusable verifier instance
                    .build();
        } catch (JWTCreationException exception){
            // Invalid Signing configuration / Couldn't convert Claims.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateJwt(User user) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("email", user.getEmail());
        map.put("firstName", user.getFirstName());
        map.put("lastName", user.getLastName());
        map.put("role", user.getRole().name());
        map.put("age", user.getAge());
        map.put("gender", user.getGender().name());
        map.put("id", user.getId());


        String token = JWT.create()
                .withIssuer("auth0")
                .withSubject(String.valueOf(user.getId()))
                .withPayload(map)
                .sign(algorithm);

        return token;
    }

    public User verifyJwt(String token) {
        try {
            token = token.replace("Bearer ", "");
            token = token.replace("bearer ", "");

            UserList userListInstance = UserList.getInstance();
            DecodedJWT decodedJWT = verifier.verify(token);
            User user = userListInstance.getUserById(Integer.parseInt(decodedJWT.getSubject()));
            return user;
        } catch (JWTVerificationException e){
//            e.printStackTrace();
            return null;
        }
    }


}
