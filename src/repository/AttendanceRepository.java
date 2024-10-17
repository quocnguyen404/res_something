package repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.nio.file.Paths;
import java.time.LocalTime;

import common.AppConstant;
import dao.Attendance;

public class AttendanceRepository {
    private static final int ALFD = 2;
    private static final String ATTENDANCE_FOLDER = AppConstant.DATA_PREFIX+"attendances\\";
    private Map<Integer, Attendance> attendances;

    public AttendanceRepository() {
        loadAllAttendanceFromFile();
    }

    public List<Attendance> getAttendanceList() {
        return new ArrayList<Attendance>(attendances.values());
    }

    private void loadAllAttendanceFromFile() {
        String file = ATTENDANCE_FOLDER+LocalDate.now()+AppConstant.DATA_SUFFIX;
        Path path = Paths.get(file);
        attendances = new HashMap<>();
        try {
            if(Files.exists(path)) {
                try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if(tokens.length != ALFD)
                            throw new Exception("Attendance format wrong");
                        Attendance atten = dataToAttendance(tokens);
                        attendances.put(atten.getId(), atten);
                    }
                }
            } else {
                Files.createFile(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAttendance(Attendance attendance) {
        String file = ATTENDANCE_FOLDER+LocalDate.now()+AppConstant.DATA_SUFFIX;
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            attendances.put(attendance.getId(), attendance);
            writer.write(attendaceToData(attendance));
            writer.newLine();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Attendance findAttendanceByEmployeeID(int id) {
        if(attendances.containsKey(id)) {
            return attendances.get(id);
        }
        return null;
    }

    private Attendance dataToAttendance(String[] tokens) {
        return new Attendance(Integer.parseInt(tokens[0]), LocalTime.parse(tokens[1]));
    }

    private String attendaceToData(Attendance atten) {
        return String.format("%s,%s", atten.getId(), atten.getTime());
    }
}
