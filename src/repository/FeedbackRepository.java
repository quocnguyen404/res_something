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

import common.AppConstant;
import dao.Feedback;

public class FeedbackRepository {
    private static final int FLFD = 3;
    private static final String FEED_BACK_FOLDER = AppConstant.DATA_PREFIX+"feedbacks\\";
    private Map<Integer, Feedback> feedbacks;

    public FeedbackRepository() {
        loadAllAttendanceFromFile();
    }

    public List<Feedback> getFeedbacksList() {
        return new ArrayList<Feedback>(feedbacks.values());
    }

    private void loadAllAttendanceFromFile() {
        String file = FEED_BACK_FOLDER+LocalDate.now()+AppConstant.DATA_SUFFIX;
        Path path = Paths.get(file);
        feedbacks = new HashMap<>();
        try {
            if(Files.exists(path)) {
                try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while((line = reader.readLine()) != null) {
                        String[] tokens = line.split(",");
                        if(tokens.length != FLFD)
                            throw new Exception("Attendance format wrong");
                        Feedback feedback = dataToFeedback(tokens);
                        feedbacks.put(feedback.getOrderID(), feedback);
                    }
                }
            } else {
                Files.createFile(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFeedback(Feedback feedback) {
        String fileDir = FEED_BACK_FOLDER+LocalDate.now()+AppConstant.DATA_SUFFIX;
        File file = new File(fileDir);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            feedbacks.put(feedback.getOrderID(), feedback);
            writer.write(feedbackToData(feedback));
            writer.newLine();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Feedback getFeedbackByOrderID(int orderID) {
        if(feedbacks.containsKey(orderID)) {
            return feedbacks.get(orderID);
        }
        return null;
    }

    private Feedback dataToFeedback(String[] tokens) {
        return new Feedback(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
    }

    private String feedbackToData(Feedback feedback) {
        return String.format("%s,%s,%s", feedback.getOrderID(), feedback.getCustomerName(), feedback.getFeedback());
    }
}
