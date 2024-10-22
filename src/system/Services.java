package system;

import services.AuthService;
import services.ManagerService;
import services.SystemService;
import services.UserService;

class Services {
    private AuthService authService;
    private ManagerService managerService;
    private SystemService systemService;
    private UserService userService;
   
    Services(Repositories repositories) {
        authService = new AuthService(repositories.getUserRepository());
        managerService = new ManagerService(repositories.getUserRepository(), repositories.getDishRepository());
        systemService = new SystemService(repositories.getAttendanceRepository(), repositories.getOrderRepository());
        userService = new UserService(repositories.getUserRepository());
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
}
