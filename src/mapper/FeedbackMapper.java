package mapper;

import dao.Feedback;
import dto.request.FeedbackRequest;
import dto.response.FeedbackResponse;

public class FeedbackMapper {
    public FeedbackResponse toResponse(Feedback feedback) {
        return new FeedbackResponse(feedback.getOrderID(),
                                    feedback.getCustomerName(),
                                    feedback.getFeedback());
    }

    public Feedback toFeedback(FeedbackRequest request) {
        return new Feedback(request.getOrderID(), request.getCustomerName(), request.getFeedback());
    }
}
