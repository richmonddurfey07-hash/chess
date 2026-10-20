package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

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
        if(gameDAO.getGame(req.gameID())==null){
            throw new DataAccessException("Error: bad request");
        }
        GameData game = gameDAO.getGame(req.gameID());
        if(req.color()==WHITE) {
            GameData newGame = new GameData(game.gameID(),game.whiteUsername(),game.blackUsername(),game.gameName(),game.game());
            gameDAO.updateGame(newGame);
        }
        if(req.color()==BLACK){
            GameData newGame = new GameData(game.gameID(),game.whiteUsername(),game.blackUsername(),game.gameName(),game.game());
            gameDAO.updateGame(newGame);
        }
        else{
            throw new DataAccessException("Error: bad request");
        }
        return new JoinResult();
    }
}
