package dataaccess.sql;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.LogoutService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

class SQLLogoutServiceTest {
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
        authDAO = new MySQLAuthDAO();
        userDAO = new MySQLUserDAO();
        gameDAO = new MySQLGameDAO();
        logoutService = new LogoutService(authDAO);
        registerService = new RegisterService(userDAO,authDAO);
        new ClearService(authDAO, userDAO, gameDAO).clear();

        res = registerService.register(new RegisterRequest("p3", "pass", "email"));
        user = userDAO.getUser(res.username());
        res2 = registerService.register(new RegisterRequest("p4", "pass2", "email2"));
        user2 = userDAO.getUser(res2.username());

    }
    @Test
    public void logoutUnsuccessSQL() throws Exception {
        logoutService.logout(res.authToken());
        assertThrows(DataAccessException.class, () ->
                logoutService.logout(res.authToken()));
    }

    @Test
    public void logoutSuccessSQL() throws Exception{

        LogoutResult result = logoutService.logout(res.authToken());
        assertNotNull(result);
    }
}
