package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;

public class LoginService {
    private final AuthDAO authDAO;
    private final UserDAO userDAO;

    public LoginService(AuthDAO authDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }
}
