package system;

import services.AuthService;
import services.DevService;
import services.ManagerService;
import services.SystemService;
import services.UserService;

class Services {
    private AuthService authService;
    private ManagerService managerService;
    private SystemService systemService;
    private UserService userService;
    private DevService devService;
   
    Services(Repositories repositories) {
        authService = new AuthService(repositories.getUserRepository());
        managerService = new ManagerService(repositories.getUserRepository(), repositories.getDishRepository(), repositories.getAttendanceRepository(), repositories.getFeedbackRepository());
        systemService = new SystemService(repositories.getOrderRepository(), repositories.getDishRepository(), repositories.getFeedbackRepository());
        userService = new UserService(repositories.getUserRepository(), repositories.getAttendanceRepository());
        devService = new DevService(repositories.getUserRepository(), repositories.getDishRepository());
    }

    public AuthService getAuthService() {
        return authService;
    }

    public ManagerService getManagerService() {
        return managerService;
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public UserService getUserService() {
        return userService;
    }

    DevService getDevService() {
        return devService;
    }
}
