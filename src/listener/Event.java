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
    UpdateDish,
    GetDishes,
    CreateOrder,
    SubmitOrder,
    GetFeedbacks,
    CreateFeedback,
    CheckIn,
    CheckOut,
    GetAttendances,

    //Handle UI Event
    GetSystemUser,
    HandleLogin,
    HandleRegister,
    HandleChangePassword,
    HandleUpdateUser,
    HandleDeletUser,
    HandleAddDish,
    HandleDeleteDish,
    HandleUpdateDish,
    HandleViewDishes,
    HandleChooseDishes,
    HandleCreateOrder,
    HandleGetFeedbacks,
    HandleCreateFeedback,
    HandleCheckOut,
    HandleViewAttendance,
    BindManagerEvent,
    BindEmployeeEvent,
    
    //UI Event
    LoginUI,
    RegisterUI,
    ChangePasswordUI,
    UserUI,
    ManagerUI,
    UserManagementUI,
    UpdateUserUI,
    FoodManagementUI,
    AddDishUI,
    UpdateDishUI,
    FeedbackUI,
    OrderUI,
    CreateFeedbackUI,
    ViewAttendanceUI,
    // StatisticsManagementUI,
}
