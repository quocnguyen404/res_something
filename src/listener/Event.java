package listener;

public enum Event {
    //Service Event
    Authenticate,
    Register,
    ChangePassword,
    Attendance,
    GetAttendances,
    CreateOrder,
    SubmitOrder,
    Logout,

    //Handle UI Event
    GetSystemUser,
    HandleLogin,
    HandleRegister,
    HandleChangePassword,
    BindManagerEvent,
    BindEmployeeEvent,
    
    //UI Event
    LoginUI,
    RegisterUI,
    ChangePasswordUI,
    CheckAttendanceUI,
    FeedbackUI,
    FoodManagementUI,
    ManagerManagementUI,
    OrderUI,
    StatisticsManagementUI,
    UserUI
}
