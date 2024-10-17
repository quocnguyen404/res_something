package repository;

import dao.User;
import dao.Role;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.AppConstant;

public class UserRepository {
    private static final int ULFD = 7;
    private static final String USER_DATA_PATH = AppConstant.DATA_PREFIX+"users"+AppConstant.DATA_SUFFIX;
    private static final String USER_TMP_DATA_PATH = AppConstant.DATA_PREFIX+"users"+AppConstant.DATA_TEMP_SUFFIX;
    private Map<String, User> users;

    public UserRepository() {
        loadAllUserFromFile();
    }

    public List<User> getUserList() {
        return new ArrayList<User>(users.values());
    }

    // Get all users from the file
    private void loadAllUserFromFile() {
        users = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if(tokens.length != ULFD)
                    throw new Exception("User data format wrong");    
                User user = dataToUser(tokens);
                users.put(user.getUserName(), user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Save a new user to the file
    public void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_PATH, true))) {
            users.put(user.getUserName(), user);
            writer.write(userToData(user));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Find a user by username
    public User findUserByUserName(String userName) {
        if(users.containsKey(userName)) {
            return users.get(userName);
        }
        return null;
    }

    // Update user data in the file
    public void updateUser(User updatedUser) {
        File tempFile = new File(USER_TMP_DATA_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens[0].equals(updatedUser.getUserName())) {
                    // Write updated user
                    writer.write(userToData(updatedUser));
                } else {
                    // Write original user data
                    writer.write(line);
                }
                writer.newLine();
            }

            // Delete the original file and rename the temp file
            if (!new File(USER_DATA_PATH).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(USER_DATA_PATH))) {
                throw new IOException("Could not rename temp file");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Delete user by username
    public void deleteUser(String userName) {
        File tempFile = new File(USER_TMP_DATA_PATH);
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (!tokens[0].equals(userName)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            // Delete the original file and rename the temp file
            if (!new File(USER_DATA_PATH).delete()) {
                throw new IOException("Could not delete original file");
            }
            if (!tempFile.renameTo(new File(USER_DATA_PATH))) {
                throw new IOException("Could not rename temp file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User dataToUser(String[] tokens) {
        User user = new User(tokens[0], tokens[1],
                             tokens[2],
                             tokens[3], tokens[4],
                             Integer.parseInt(tokens[5]),
                             Role.valueOf(tokens[6]));
        return user;
    }

    //user data: 0userName,1encodePW,2password,3firstName,4lastName,5loginCount,6role
    private String userToData(User user) {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                             user.getUserName(), user.getEncodePassword(),
                             user.getPassword(),
                             user.getFirstName(), user.getLastName(),
                             user.getLoginCount(), user.getRole());
    }
}
