package com.medical.demo.controllers;

import com.medical.demo.models.Role;
import com.medical.demo.models.User;
import com.medical.demo.utils.JwtUtil;
import com.medical.demo.utils.UserList;
import com.medical.demo.utils.XlsUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping(path = "api/v1")
public class DataController {
    XlsUtil xlsUtil;
    JwtUtil jwtUtil = JwtUtil.getInstance();

    @GetMapping("/get-data")
    public ResponseEntity<Object> getXlsData(
            @RequestHeader("Authorization") String authHeader
    ){

        User currentUser = jwtUtil.verifyJwt(authHeader);

        if (currentUser == null) {
            HashMap<String, String> map = new HashMap<>();
            map.put("message", "Not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        }

        if (currentUser.getRole() == Role.PATIENT) {
            try {
                xlsUtil = new XlsUtil(Role.PATIENT);
                return ResponseEntity.status(HttpStatus.OK).body(xlsUtil.getData());
            } catch (IOException e) {
                HashMap<String, String> map = new HashMap<>();
                map.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(map);
            }


        } else if (currentUser.getRole() == Role.PHARMACIST) {
            try {
                xlsUtil = new XlsUtil(Role.PHARMACIST);
                return ResponseEntity.status(HttpStatus.OK).body(xlsUtil.getData());
            } catch (IOException e) {
                HashMap<String, String> map = new HashMap<>();
                map.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(map);
            }


        } else if (currentUser.getRole() == Role.PHYSICIAN) {
            try {
                xlsUtil = new XlsUtil(Role.PHYSICIAN);
                return ResponseEntity.status(HttpStatus.OK).body(xlsUtil.getData());
            } catch (IOException e) {
                HashMap<String, String> map = new HashMap<>();
                map.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(map);
            }


        } else {
            try {

                HashMap<String, Object> map = new HashMap<>();

                xlsUtil = new XlsUtil(Role.PATIENT);
                map.put("illnesses", xlsUtil.getData());

                xlsUtil = new XlsUtil(Role.PHYSICIAN);
                map.put("missions", xlsUtil.getData());

                xlsUtil = new XlsUtil(Role.PHARMACIST);
                map.put("drugs", xlsUtil.getData());

                return ResponseEntity.status(HttpStatus.OK).body(map);

            } catch (IOException e) {
                HashMap<String, String> map = new HashMap<>();
                map.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(map);
            }
        }

    }



}
