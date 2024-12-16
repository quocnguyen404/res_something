package listener;

public enum Event {
    //Service Event
    Authenticate,
    Register,
    ChangePassword,
    UpdateUser,
    DeleteUser,
    AddDish,
    DeleteDish,
    GetDishes,
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
    HandleUpdateUser,
    HandleDeletUser,
    HandleAddDish,
    HandleDeleteDish,
    BindManagerEvent,
    BindEmployeeEvent,
    
    //UI Event
    LoginUI,
    RegisterUI,
    ChangePasswordUI,
    ManagerManagementUI,
    UserManagementUI,
    UpdateUserUI,
    FoodManagementUI,
    AddDishUI,
    CheckAttendanceUI,
    FeedbackUI,
    OrderUI,
    StatisticsManagementUI,
    UserUI
}
