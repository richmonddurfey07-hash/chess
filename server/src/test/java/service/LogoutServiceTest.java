package service;

import dataaccess.*;
import model.LogoutResult;
import model.RegisterRequest;
import model.RegisterResult;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogoutServiceTest {
    private RegisterService registerService;
    private LogoutService logoutService;
    private AuthDAO authDAO;
    private UserDAO userDAO;
    private GameDAO gameDAO;
    private RegisterResult res;
    private RegisterResult res2;
    private UserData user;
    private UserData user2;
    private LogoutResult logoutResult;

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();
        logoutService = new LogoutService(authDAO);
        registerService = new RegisterService(userDAO,authDAO);
        new ClearService(authDAO, userDAO, gameDAO).clear();

        res = registerService.register(new RegisterRequest("p1", "pass", "email"));
        user = userDAO.getUser(res.username());
        res2 = registerService.register(new RegisterRequest("p2", "pass2", "email2"));
        user2 = userDAO.getUser(res2.username());

    }
    @Test
    public void logoutUnsuccess() throws Exception {
        logoutService.logout(res.authToken());
        assertThrows(DataAccessException.class, () ->
                logoutService.logout(res.authToken()));
    }

    @Test
    public void logoutSuccess() throws Exception{
        LogoutResult result = logoutService.logout(res.authToken());
        assertNotNull(result);
    }
}
