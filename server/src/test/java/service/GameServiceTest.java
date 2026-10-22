package service;

import chess.ChessGame;
import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    private GameService gameService;
    private RegisterService registerService;
    private String validToken;
    private AuthDAO authDAO;
    private UserDAO userDAO;
    private GameDAO gameDAO;
    private String otherAuth;

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();

        gameService = new GameService(authDAO, userDAO, gameDAO);
        registerService = new RegisterService(userDAO,authDAO);
        new ClearService(authDAO, userDAO, gameDAO).clear();

        validToken = registerService.register(new RegisterRequest("p1", "pass", "email")).authToken();
        otherAuth = registerService.register(new RegisterRequest("p2", "pass", "email2")).authToken();
    }
    @Test
    public void joinGameColorTaken() throws Exception {
        int gameID = gameService.create(new CreateRequest("Game",validToken)).gameID();
        gameService.join(new JoinRequest("WHITE", gameID,validToken));

        assertThrows(DataAccessException.class, () ->
                gameService.join(new JoinRequest("WHITE", gameID,otherAuth))
        );
    }

    @Test
    public void listGames() throws Exception{
        int gameID = gameService.create(new CreateRequest("Game",validToken)).gameID();
        ListResult games = gameService.listGames(validToken);
        assertNotNull(games);
    }
}