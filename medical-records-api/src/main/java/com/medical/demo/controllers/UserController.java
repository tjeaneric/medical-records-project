package com.medical.demo.controllers;
import com.medical.demo.models.*;
import com.medical.demo.utils.JwtUtil;
import com.medical.demo.utils.UserList;
import com.medical.demo.utils.XlsUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    UserList users = UserList.getInstance();
    JwtUtil jwtUtil = JwtUtil.getInstance();

    @GetMapping("/all.csv")
    public ResponseEntity<Object> getAllUsers(
            @RequestHeader("Authorization") String authHeader,
            HttpServletResponse response
    ){

        User currentUser = jwtUtil.verifyJwt(authHeader);

        if (currentUser == null) {
            HashMap<String, String> map = new HashMap<>();
            map.put("message", "Not authenticated");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
        }

        if (currentUser.getRole() != Role.ADMIN) {
            HashMap<String, String> map = new HashMap<>();
            map.put("message", "You don't have permissions to perform this operation");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(map);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("text", "plain", StandardCharsets.UTF_8));

        return new ResponseEntity<>(users.getCsvString(), httpHeaders, HttpStatus.OK);
    }
    @PostMapping("/create_user")
    public ResponseEntity<Object> createUser(@RequestBody HashMap<String, Object> user){
        try {
          User newUser;
          if (user.get("role").toString().equals(Role.ADMIN.name())){
              newUser = new Admin(user);
              if (user.get("password").toString() == null || user.get("password").toString().isBlank() || user.get("password").toString().length() != 10){
                  HashMap<String, String> map = new HashMap<>();

                  map.put("message", "password must be 10 characters");
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
                }
          } else if (user.get("role").toString().equals(Role.PATIENT.name())) {
              newUser = new Patient(user);

              if (user.get("password").toString() == null || user.get("password").toString().isBlank() || user.get("password").toString().length() != 6){
                  HashMap<String, String> map = new HashMap<>();

                  map.put("message", "password must be 6 characters");
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
              }

          } else if (user.get("role").toString().equals(Role.PHARMACIST.name())) {
              newUser = new Pharmacist(user);

              if (user.get("password").toString() == null || user.get("password").toString().isBlank() || user.get("password").toString().length() != 4){
                  HashMap<String, String> map = new HashMap<>();

                  map.put("message", "password must be 4 characters");
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
              }

          }else {
              newUser = new Physician(user);

              if (user.get("password").toString() == null || user.get("password").toString().isBlank() || user.get("password").toString().length() != 8){
                  HashMap<String, String> map = new HashMap<>();

                  map.put("message", "password must be 8 characters");
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
              }
          }

            User existingUser = users.getUserByEmail(newUser.getEmail());

            if (existingUser != null) {
                HashMap<String, String> map = new HashMap<>();

                map.put("message", "User with provided email already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
            }
            users.addUser(newUser.signup());
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){

        if (loginRequest.getEmail() == null || loginRequest.getEmail().isBlank()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("message", "Email is missing");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }

        if (loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("message", "Password is missing");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }

        User user = users.getUserByEmail(loginRequest.getEmail());

        if (user == null) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("message", "User with that email is not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }

        if (user.login(user, loginRequest.getPassword())) {

            String token = jwtUtil.generateJwt(user);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("access_token", token);

            return ResponseEntity.status(HttpStatus.OK).body(map);
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", "Invalid credentials");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        
    }
}
