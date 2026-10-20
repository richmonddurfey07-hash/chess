package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.LoginRequest;
import model.LoginResult;
import model.UserData;

public class LoginService {
    private final AuthDAO authDAO;
    private final UserDAO userDAO;

    public LoginService(UserDAO userDAO, AuthDAO authDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public LoginResult login(LoginRequest req) throws DataAccessException {
        if(userDAO.getUser(req.username())==null){
            throw new DataAccessException("Error: unauthorized");
        }
        UserData user = userDAO.getUser(req.username());
        userDAO.insertUser(user);
        String authToken = authDAO.createAuth(req.username());
        return new LoginResult(req.username(),authToken);
    }
}
