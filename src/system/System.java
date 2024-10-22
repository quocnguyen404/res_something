package system;

import dao.User;

public class System
{
    private User user;
    private GUI gui;
    private Repositories repositories;
    private Services services;
    
    public System()
    {
        gui = new GUI();
        repositories = new Repositories(user.getRole());
        services = new Services(user.getRole());

        repositories.Initialize();
        services.Initialize(repositories);
    }


}
