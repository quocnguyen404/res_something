package repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository {
    private final String filePath;

    public FeedbackRepository(String filePath) {
        this.filePath = filePath;
    }

    // Method to save feedback for an employee
    public void saveFeedback(int employeeID, LocalDate date, String feedbackMessage) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(employeeID + "," + date.toString() + "," + feedbackMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to feedback file: " + e.getMessage());
        }
    }

    // Method to load all feedback records
    public List<String> loadAllFeedback() {
        List<String> feedbackRecords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                feedbackRecords.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from feedback file: " + e.getMessage());
        }
        return feedbackRecords;
    }

    // Method to load feedback for a specific employee by ID
    public List<String> loadFeedbackByEmployeeID(int employeeID) {
        List<String> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == employeeID) {
                    records.add(line); // Add the feedback record for the specified employee
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from feedback file: " + e.getMessage());
        }
        return records; // Return all feedback records for the employee
    }
}
