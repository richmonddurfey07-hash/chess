package service;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
    public void joinGameColorTakenSQL() throws Exception {
        int gameID = gameService.create(new CreateRequest("Game",validToken)).gameID();
        gameService.join(new JoinRequest("WHITE", gameID,validToken));

        assertThrows(DataAccessException.class, () ->
                gameService.join(new JoinRequest("WHITE", gameID,otherAuth))
        );
    }

    @Test
    public void listGamesSQL() throws Exception{
        int gameID = gameService.create(new CreateRequest("Game",validToken)).gameID();
        ListResult games = gameService.listGames(validToken);
        assertNotNull(games);
    }

    @Test
    public void listGamesUnsuccessfulSQL() throws Exception{
        assertThrows(DataAccessException.class, () ->
                gameService.create(new CreateRequest("Game","1")).gameID()
        );

    }

    @Test
    public void joinGameColorBadSQL() throws Exception {
        int gameID = gameService.create(new CreateRequest("Game",validToken)).gameID();
        assertThrows(DataAccessException.class, () ->
                gameService.join(new JoinRequest("BLUE", gameID,otherAuth))
        );
    }

    @Test
    public void joinBothGameColorSQL() throws Exception {
        int gameID = gameService.create(new CreateRequest("Game",validToken)).gameID();
        gameService.join(new JoinRequest("WHITE", gameID,validToken));
        assertEquals(new JoinResult(),
                gameService.join(new JoinRequest("BLACK", gameID,otherAuth)));
    }
}