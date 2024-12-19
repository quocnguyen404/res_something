package dto.response;

public class FeedbackResponse {
    private int orderID;
    private String customerName;
    private String feedback;

    public FeedbackResponse() {
    }

    public FeedbackResponse(int orderID, String customerName, String feedback) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.feedback = feedback;
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
