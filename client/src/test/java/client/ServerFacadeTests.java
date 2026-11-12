package client;


import exception.ResponseException;
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
    public void clear() throws ResponseException {
        facade.clear();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    void registerPositive() throws ResponseException {
        var result = facade.register("player1", "password", "p1@email.com");
        Assertions.assertNotNull(result.authToken());
    }

    @Test
    void registerNegative() throws ResponseException{
        Assertions.assertThrows(ResponseException.class, () -> {
            facade.register("player2", null, "p2@email.com");
        });
    }

    @Test
    void loginPositive() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        Assertions.assertNotNull(login);
    }

    @Test
    void loginNegative() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        Assertions.assertThrows(ResponseException.class, () ->
                facade.login(new LoginRequest("player1","pass")));
    }

    @Test
    void logoutPositive() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        var logout = facade.logout(login.authToken());
        Assertions.assertNotNull(logout);
    }

    @Test
    void logoutNegative() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        Assertions.assertThrows(ResponseException.class, () ->
                facade.logout("authToken"));
    }

    @Test
    void createPositive() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        var create = facade.createGames("game1",login.authToken());
        Assertions.assertNotNull(create);
    }

    @Test
    void createNegative() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        Assertions.assertThrows(ResponseException.class, () ->
                facade.createGames("game1","auth"));
    }

    @Test
    void listPositive() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        var create = facade.createGames("game1",login.authToken());
        var list = facade.listGames(login.authToken());
        Assertions.assertNotNull(list);
    }

    @Test
    void listNegative() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        var create = facade.createGames("game1",login.authToken());
        Assertions.assertThrows(ResponseException.class, () ->
                facade.listGames("auth"));
    }

    @Test
    void joinPositive() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        var create = facade.createGames("game1",login.authToken());
        Assertions.assertDoesNotThrow(()->
                facade.joinGames(create.gameID(), "WHITE", login.authToken()));
    }

    @Test
    void joinNegative() throws ResponseException{
        var result = facade.register("player1", "password", "p1@email.com");
        var login = facade.login(new LoginRequest("player1","password"));
        var create = facade.createGames("game1",login.authToken());
        Assertions.assertThrows(ResponseException.class, () ->
                facade.joinGames(8000000, "White", login.authToken()));
    }


}
