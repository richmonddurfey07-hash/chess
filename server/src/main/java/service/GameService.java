package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.*;

import java.util.Collection;

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
        if(req.gameName()==null){
            throw new DataAccessException("bad request");
        }
        if(authDAO.getAuth(req.authToken())==null){
            throw new DataAccessException("unauthorized");
        }
        int id = gameDAO.createGame(req.gameName());
        return new CreateResult(id);
    }

    public JoinResult join(JoinRequest req) throws DataAccessException{
        if(authDAO.getAuth(req.authToken())==null){
            throw new DataAccessException("unauthorized");
        }
        AuthData authData = authDAO.getAuth(req.authToken());
        if(gameDAO.getGame(req.gameID())==null){
            throw new DataAccessException("bad request");
        }
        GameData game = gameDAO.getGame(req.gameID());
        if(req.color()!=WHITE && req.color()!=BLACK){
            throw new DataAccessException("bad request");
        }
        if((req.color() == WHITE) && (game.whiteUsername() == null)) {
            GameData newGame = new GameData(game.gameID(),authData.username(),game.blackUsername(),game.gameName(),game.game());
            gameDAO.updateGame(newGame);
            return new JoinResult();
        }
        if((req.color() == BLACK) && (game.blackUsername() == null)){
            GameData newGame = new GameData(game.gameID(),game.whiteUsername(),authData.username(),game.gameName(),game.game());
            gameDAO.updateGame(newGame);
            return new JoinResult();
        }
        else{
            throw new DataAccessException("already taken");
        }
    }

    public ListResult ListGames(String authToken) throws DataAccessException{
        if(authDAO.getAuth(authToken)==null){
            throw new DataAccessException("unauthorized");
        }
        Collection<GameData> games = gameDAO.listGames();
        return new ListResult(games);
    }
}
