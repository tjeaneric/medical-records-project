package com.medical.demo.utils;
import com.medical.demo.models.User;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UserList {

    private static UserList instance = null;
    final List<User> users = new ArrayList<>();

    public static UserList getInstance()
    {
        if (instance == null)
            instance = new UserList();

        return instance;
    }
    public List<User> getUsers(){

        return users;
    }

    public User addUser(User user){
        user.setId(users.size() + 1);
        users.add(user);
        return user;
    }

    public User getUserByEmail(String email) {
        List<User> filtered = users.stream()
                .filter(user -> user.getEmail().equals(email))
                .collect(Collectors.toList());

        if (filtered.size() > 0) {
            return filtered.get(0);
        }
        return null;
    }

    public User getUserById(int id) {
        List<User> filtered = users.stream()
                .filter(user -> user.getId() == id)
                .collect(Collectors.toList());

        if (filtered.size() > 0) {
            return filtered.get(0);
        }
        return null;
    }

    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public String convertToCSV(String[] data) {

        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }


    public void givenDataArray_whenConvertToCSV_thenOutputCreated() throws IOException {
        List<String[]> dataLines = new ArrayList<>();

        for (User user: this.getUsers()) {
            dataLines.add(new String[]{user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().name() });
        }

        File csvOutputFile = new File("users.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }

    }


    public String getCsvString() {
        StringBuilder csvData = new StringBuilder();
        for (User user: this.getUsers()) {
            csvData.append(user.getFirstName()).append(" ").append(user.getLastName()).append(",").append(user.getEmail()).append(",").append(user.getRole().name()).append("\n");
        }
        return csvData.toString();
    }


}
