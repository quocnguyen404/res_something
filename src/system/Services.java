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
        managerService = new ManagerService(repositories.getUserRepository(), repositories.getDishRepository());
        systemService = new SystemService(repositories.getAttendanceRepository(), repositories.getOrderRepository(), repositories.getDishRepository(), repositories.getUserRepository());
        userService = new UserService(repositories.getUserRepository());
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
