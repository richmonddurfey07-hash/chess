package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.CreateRequest;
import model.CreateResult;
import model.JoinRequest;
import model.JoinResult;

public class GameService {
    private final AuthDAO authDAO;
    private final UserDAO userDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, UserDAO userDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.userDAO = userDAO;
        this.gameDAO = gameDAO;
    }

    public CreateResult create(CreateRequest req) throws DataAccessException {
        if(authDAO.getAuth(req.authToken())==null){
            throw new DataAccessException("Error: unauthorized");
        }
        int id = gameDAO.createGame(req.gameName());
        return new CreateResult(id);
    }

    public JoinResult join(JoinRequest req) throws DataAccessException{
        if(authDAO.getAuth(req.authToken())==null){
            throw new DataAccessException("Error: unauthorized");
        }
        return new JoinResult();
    }
}
