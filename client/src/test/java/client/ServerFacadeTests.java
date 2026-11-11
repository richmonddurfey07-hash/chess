package client;

import dataaccess.DataAccessException;
import model.LoginRequest;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void clear() throws DataAccessException {
        facade.clear();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    void registerPositive() throws DataAccessException {
        var result = facade.register("player1", "password", "p1@email.com");
        Assertions.assertNotNull(result.authToken());
    }

    @Test
    void registerNegative() throws DataAccessException{
        Assertions.assertThrows(DataAccessException.class, () -> {
            facade.register("player2", null, "p2@email.com");
        });
    }

    @Test
    void loginPositive() throws DataAccessException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        Assertions.assertNotNull(login);
    }

    @Test
    void loginNegative() throws DataAccessException{
        var result = facade.register("player1", "password", "p1@email.com");
        Assertions.assertThrows(DataAccessException.class, () ->
                facade.login(new LoginRequest("player1","pass")));
    }

}
