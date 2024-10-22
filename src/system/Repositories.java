package system;

import repository.AttendanceRepository;
import repository.DishRepository;
import repository.EmployeeRepository;
import repository.FeedbackRepository;
import repository.OrderRepository;
import repository.StatisticsRepository;
import repository.UserRepository;

class Repositories {
    private UserRepository userRepository;
    private EmployeeRepository employeeRepository;
    private DishRepository dishRepository;
    private AttendanceRepository attendanceRepository;
    private FeedbackRepository feedbackRepository;
    private OrderRepository orderRepository;
    // private final StatisticsRepository statisticsRepository;

    Repositories() {
        userRepository = new UserRepository();
        employeeRepository = new EmployeeRepository();
        dishRepository = new DishRepository();
        attendanceRepository = new AttendanceRepository();
        feedbackRepository = new FeedbackRepository();
        orderRepository = new OrderRepository();
        // statisticsRepository = new StatisticsRepository();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public DishRepository getDishRepository() {
        return dishRepository;
    }

    public AttendanceRepository getAttendanceRepository() {
        return attendanceRepository;
    }

    public FeedbackRepository getFeedbackRepository() {
        return feedbackRepository;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    // public StatisticsRepository getStatisticsRepository() {
    //     return statisticsRepository;
    // }
}
