package dto.request;

import java.time.LocalDate;

public class FeedbackRequest {
    private int orderID;
    private String customerName;
    private String feedback;
    private LocalDate date;

    public FeedbackRequest() {
    }

    public FeedbackRequest(int orderID, String customerName, String feedback, LocalDate date) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.feedback = feedback;
        this.date = date;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
