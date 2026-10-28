package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.LoginRequest;
import model.LoginResult;
import model.UserData;

import java.util.Objects;
import java.util.UUID;

public class LoginService {
    private final AuthDAO authDAO;
    private final UserDAO userDAO;

    public LoginService(UserDAO userDAO, AuthDAO authDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public LoginResult login(LoginRequest req) throws DataAccessException {
        if(req.username()==null||req.password()==null){
            throw new DataAccessException("bad request");
        }
        UserData user = userDAO.getUser(req.username());
        if(user==null|| !Objects.equals(user.password(), req.password())){
            throw new DataAccessException("unauthorized");
        }
        userDAO.insertUser(user);
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, req.username());
        authDAO.insertAuth(authData);
        return new LoginResult(req.username(),authToken);
    }
}
