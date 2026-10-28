package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.*;

import java.util.UUID;

public class RegisterService {
    private final AuthDAO authDAO;
    private final UserDAO userDAO;

    public RegisterService(UserDAO userDAO, AuthDAO authDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }

    public RegisterResult register(RegisterRequest req) throws DataAccessException{
        if(req.username()==null||req.password()==null||req.email()==null){
            throw new DataAccessException("bad request");
        }
        if(userDAO.getUser(req.username())!=null){
            throw new DataAccessException("already taken");
        }
        UserData user = new UserData(req.username(),req.password(),req.email());
        userDAO.insertUser(user);
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, req.username());
        authDAO.insertAuth(authData);
        return new RegisterResult(req.username(),authToken);
    }
}
