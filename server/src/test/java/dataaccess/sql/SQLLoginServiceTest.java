package dataaccess.sql;

import dataaccess.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import service.ClearService;
import service.LoginService;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

class SQLLoginServiceTest {
    private RegisterService registerService;
    private LoginService loginService;
    private AuthDAO authDAO;
    private UserDAO userDAO;
    private GameDAO gameDAO;
    private RegisterResult res;
    private RegisterResult res2;
    private UserData user;
    private UserData user2;

    @BeforeEach
    public void setup() throws DataAccessException {
        authDAO = new MemoryAuthDAO();
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();
        loginService = new LoginService(userDAO, authDAO);
        registerService = new RegisterService(userDAO,authDAO);
        new ClearService(authDAO, userDAO, gameDAO).clear();

        res = registerService.register(new RegisterRequest("p3", BCrypt.hashpw("pass", BCrypt.gensalt()), "email"));
        user = userDAO.getUser(res.username());
        res2 = registerService.register(new RegisterRequest("p4", BCrypt.hashpw("pass2", BCrypt.gensalt()), "email2"));
        user2 = userDAO.getUser(res2.username());

    }
    @Test
    public void loginUnsuccessSQL() throws Exception {
        assertThrows(DataAccessException.class, () ->
                loginService.login(new LoginRequest(res.username(),user2.password())));
    }

    @Test
    public void loginSuccessSQL() throws Exception{
        LoginResult result = loginService.login(new LoginRequest(res.username(),"pass"));
        assertEquals(result.username(),user.username());
    }
}