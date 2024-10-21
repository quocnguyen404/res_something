package dao;

public class Feedback {
    private int id;
    private int orderID;
    private String customerName;
    private String feedback;

    public Feedback() {
    }

    public Feedback(int id, int orderID, String customerName, String feedback) {
        this.id = id;
        this.orderID = orderID;
        this.customerName = customerName;
        this.feedback = feedback;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
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
}
