package repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import common.AppConstant;

public class AttendanceRepository {
    private final String filePath = AppConstant.DATA_PREFIX+"attendances"+AppConstant.DATA_SUFFIX;

    public AttendanceRepository() {
    }

    // Method to save attendance for an employee
    public void saveAttendance(int employeeID, LocalDate date, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(employeeID + "," + date.toString() + "," + status);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to attendance file: " + e.getMessage());
        }
    }

    // Method to load all attendance records
    public List<String> loadAllAttendance() {
        List<String> attendanceRecords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                attendanceRecords.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from attendance file: " + e.getMessage());
        }
        return attendanceRecords;
    }

    // Method to load attendance for a specific employee by ID
    public List<String> loadAttendanceByEmployeeID(int employeeID) {
        List<String> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (Integer.parseInt(parts[0]) == employeeID) {
                    records.add(line); // Add the attendance record for the specified employee
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from attendance file: " + e.getMessage());
        }
        return records; // Return all attendance records for the employee
    }
}
