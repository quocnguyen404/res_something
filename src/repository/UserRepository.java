package repository;

import dao.User;
import dao.Role;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import common.AppConstant;

public class UserRepository {
    private static final String filePath = AppConstant.DATA_PREFIX+"users"+AppConstant.DATA_SUFFIX;
    private Map<String, User> users;

    public UserRepository() {
        loadUserAllUser();
    }
    
    //user data: 0userName,1encodePW,2firstName,3lastName,4loginCount,5role
    // Save a new user to the file
    public void saveUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String userRecord = String.format("%s, %s, %s, %s, %s, %s",
                                              user.getUserName(), user.getEncodePassword(),
                                              user.getFirstName(), user.getLastName(),
                                              user.getLoginCount(), user.getRole());
            writer.write(userRecord);
            writer.newLine();
        }
    }
    
    // Load users from file
    public void loadUserAllUser() {
        users = new HashMap<String, User>();
        List<User> userFromFile = getAllUserFromFile();
        for (User user : userFromFile) {
            users.put(user.getUserName(), user);
        }
    }

    public List<User> getUserList() {
        return new ArrayList<User>(users.values());
    }

    // Get all users from the file
    private List<User> getAllUserFromFile() {
        List<User> userList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 6) {
                    User user = new User(tokens[0], tokens[1],
                                         tokens[2], tokens[3],
                                         Integer.parseInt(tokens[4]),
                                         Role.valueOf(tokens[5]));
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Find a user by username
    public User findUserByUserName(String userName) throws IOException {
        if(users.containsKey(userName)) {
            return users.get(userName);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens[0].equals(userName)) {

                    return new User(tokens[0], tokens[1],
                                    tokens[2], tokens[3],
                                    Integer.parseInt(tokens[4]),
                                    Role.valueOf(tokens[5]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

    // Update user data in the file
    public void updateUser(User updatedUser) throws IOException {
        File tempFile = new File(filePath + ".tmp");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens[0].equals(updatedUser.getUserName())) {
                    // Write updated user
                    writer.write(updatedUser.getUserName() + "," + updatedUser.getEncodePassword() + "," +
                            updatedUser.getFirstName() + "," + updatedUser.getLastName() + "," + updatedUser.getRole());
                } else {
                    // Write original user data
                    writer.write(line);
                }
                writer.newLine();
            }
        }

        // Delete the original file and rename the temp file
        if (!new File(filePath).delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(new File(filePath))) {
            throw new IOException("Could not rename temp file");
        }
    }

    // Delete user by username
    public void deleteUser(String userName) throws IOException {
        File tempFile = new File(filePath + ".tmp");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (!tokens[0].equals(userName)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }

        // Delete the original file and rename the temp file
        if (!new File(filePath).delete()) {
            throw new IOException("Could not delete original file");
        }
        if (!tempFile.renameTo(new File(filePath))) {
            throw new IOException("Could not rename temp file");
        }
    }
}
